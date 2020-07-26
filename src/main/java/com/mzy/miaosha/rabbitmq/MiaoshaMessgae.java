package com.mzy.miaosha.rabbitmq;

import com.mzy.miaosha.domin.MiaoshaUser;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-26 18:37
 **/
public class MiaoshaMessgae {
    private MiaoshaUser miaoshaUser;
    private Long goodsId;

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public MiaoshaMessgae(MiaoshaUser miaoshaUser, Long goodsId) {
        this.miaoshaUser = miaoshaUser;
        this.goodsId = goodsId;
    }

}
