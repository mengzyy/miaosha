package com.mzy.miaosha.controller;

import com.mzy.miaosha.domin.MiaoshaUser;
import com.mzy.miaosha.redis.GoodsKey;
import com.mzy.miaosha.redis.RedisService;
import com.mzy.miaosha.result.CodeMsg;
import com.mzy.miaosha.result.Result;
import com.mzy.miaosha.service.GoodsService;
import com.mzy.miaosha.service.MiaoshaUserService;
import com.mzy.miaosha.vo.GoodsDetailVo;
import com.mzy.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        /*从Redis中取缓存*/
        String GoodsListHtml = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(GoodsListHtml)) return GoodsListHtml;

        /*缓存中不存在则从数据库中取，之后加入缓存*/
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        /*手动渲染*/
        model.addAttribute("goodsList", goodsList);
        IWebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        /*加入缓存*/
        redisService.set(GoodsKey.getGoodsList, "", html);
        return html;
    }

    /**
     * 查询正在秒杀的商品
     */
    @RequestMapping(value = "/detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                         @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        /*从Redis中取缓存*/
        String GoodsListHtml = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(GoodsListHtml)) return GoodsListHtml;
        /*从数据库中取*/
        GoodsVo goodsVoByGoodsId = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVoByGoodsId);
        int[] state = getGoodsState(goodsVoByGoodsId.getStartDate().getTime(), goodsVoByGoodsId.getEndDate().getTime(), System.currentTimeMillis());
        model.addAttribute("miaoshaStatus", state[0]);
        model.addAttribute("remainSeconds", state[1]);
        /*手动渲染*/
        IWebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }


        return html;
    }

    /**
     * 用户查询已经秒杀完成的商品
     */
    @RequestMapping("/queryGoodsByMiaoSha/{goodsId}")
    public Result<GoodsDetailVo> detail(Model model, MiaoshaUser user,
                                        @PathVariable("goodsId") long goodsId) {
        if (user == null) return Result.error(CodeMsg.NOT_LOGIN);
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int[] state = getGoodsState(goods.getStartDate().getTime(), goods.getEndDate().getTime(), System.currentTimeMillis());
        /*返回用户秒杀成功的商品*/
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goods);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setMiaoshaStatus(state[0]);
        goodsDetailVo.setRemainSeconds(state[1]);
        return Result.success(goodsDetailVo);
    }

    /**
     * 判断商品秒杀状态
     */
    public int[] getGoodsState(long startAt, long endAt, long now) {
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {
            /*秒杀还没开始，倒计时*/
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            /*秒杀已结束*/
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            /*秒杀进行中*/
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        return new int[]{miaoshaStatus, remainSeconds};

    }

}
