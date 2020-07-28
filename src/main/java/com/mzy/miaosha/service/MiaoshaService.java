package com.mzy.miaosha.service;

import com.mzy.miaosha.domin.MiaoshaUser;
import com.mzy.miaosha.domin.OrderInfo;
import com.mzy.miaosha.redis.MiaoshaKey;
import com.mzy.miaosha.redis.RedisService;
import com.mzy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    private void setGoodsOver(Long id) {
        redisService.set(MiaoshaKey.ISOVER, "" + id, true);

    }

}
