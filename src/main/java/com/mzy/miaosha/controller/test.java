package com.mzy.miaosha.controller;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-25 12:07
 **/

import com.mzy.miaosha.domin.MiaoshaUser;
import com.mzy.miaosha.rabbitmq.MQConfig;
import com.mzy.miaosha.rabbitmq.MQSender;
import com.mzy.miaosha.rabbitmq.MiaoshaMessgae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 这个是用来测试的controller层
 */
@RestController
@RequestMapping("/test")
public class test {

    @Autowired
    MQSender mqSender;

    /*用来测试ControllerAdvice的全局异常效果*/
    @RequestMapping("/exception")
    public String testException() {
        int x = 1 / 0;
        return "haha";

    }

    /*用来测试ControllerAdvice的全局属性效*/
    @RequestMapping("/golbalParam")
    public String testGolbalParam(Model model) {
        return model.asMap().toString();
    }

    /*测试mq*/
    @RequestMapping("/sendmq")
    @ResponseBody
    public String testRabbit() {
        mqSender.sendMessageByDirectExchange(new MiaoshaMessgae(new MiaoshaUser(), 1000L));
        mqSender.sendMessageByFanoutExchange("fanout信息");
        mqSender.sendMessageByHeaderExchange("head信息");
        mqSender.sendMessageByTopicExchange("topic信息");
        return "发送完毕";


    }


}
