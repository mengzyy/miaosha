package com.mzy.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-26 18:34
 **/
@Component
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = {MQConfig.MIAOSHA_QUEUE})
    public void receiveMiaoshaMessage(String msg) {
        log.info("mq接受到秒杀信息: " + msg);
    }

    @RabbitListener(queues = {MQConfig.FANOUT_QUEUE_ONE, MQConfig.FANOUT_QUEUE_TWO})
    public void receiveMFanoutMessage(String msg) {
            log.info("mq的广播队列接受到信息: " + msg);
    }

    @RabbitListener(queues = {MQConfig.HEAD_QUEUE})
    public void receiveHeadMessage(byte[] msg) {
        log.info("mq的head队列接受到信息: " + new String(msg));
    }

    @RabbitListener(queues = {MQConfig.TOPIC_QUEUE})
    public void receiveTopicMessage(String msg) {
        log.info("mq的topic接受到信息: " + msg);
    }
}
