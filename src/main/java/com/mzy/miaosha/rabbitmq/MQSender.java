package com.mzy.miaosha.rabbitmq;

import com.mzy.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-26 18:34
 **/

@Component
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    /*amp的工具类*/
    @Autowired
    AmqpTemplate amqpTemplate;


    public void sendMessageByDirectExchange(MiaoshaMessgae miaoshaMessgae) {
        log.info("向mq中发送秒杀信息: " + RedisService.beanToString(miaoshaMessgae));
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, RedisService.beanToString(miaoshaMessgae));
    }

    public void sendMessageByTopicExchange(String msg) {
        log.info("向mq的topic路由发送信息: " + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.1", msg);
    }

    public void sendMessageByFanoutExchange(String msg) {
        log.info("向mq的fanout路由发送信息: " + msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
    }

    public void sendMessageByHeaderExchange(String msg) {
        log.info("向mq的Header路由发送信息: " + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEAD_EXCHANGE, "", obj);
    }


}
