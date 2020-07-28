package com.mzy.miaosha.controller;

import com.mzy.miaosha.domin.MiaoshaOrder;
import com.mzy.miaosha.domin.MiaoshaUser;
import com.mzy.miaosha.rabbitmq.MQSender;
import com.mzy.miaosha.rabbitmq.MiaoshaMessgae;
import com.mzy.miaosha.redis.GoodsKey;
import com.mzy.miaosha.redis.RedisService;
import com.mzy.miaosha.result.CodeMsg;
import com.mzy.miaosha.result.Result;
import com.mzy.miaosha.service.GoodsService;
import com.mzy.miaosha.service.MiaoshaService;
import com.mzy.miaosha.service.MiaoshaUserService;
import com.mzy.miaosha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender mqSender;
    /*内存标记，减少内存的访问*/
    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<Integer> list(Model model, MiaoshaUser user,
                                @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        Boolean goodsIdFlag = localOverMap.get(goodsId);
        /*秒杀是否结束的标记 可以减少对redis的访问*/
        if (goodsIdFlag) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        /*预减库存*/
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        /*判断用户是否已经秒杀过*/
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        /*进入消息队列*/
        mqSender.sendMessageByDirectExchange(new MiaoshaMessgae(user,goodsId));
        /*异步返回结果*/
        return Result.success(0);
    }
}
