package com.mzy.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq的配置文件
 *
 * @program: miaosha
 * @author: mengzy 18306299232@163.com
 * @create: 2020-07-26 18:24
 **/
@Configuration
public class MQConfig {

    /*默认交换机队列*/
    public static final String MIAOSHA_QUEUE = "miaosha_queue";
    /*广播交换机队列1*/
    public static final String FANOUT_QUEUE_ONE = "fanout_queue_one";
    /*广播交换机队列2*/
    public static final String FANOUT_QUEUE_TWO = "fanout_queue_two";
    /*top交换机队列*/
    public static final String TOPIC_QUEUE = "topic_queue";
    /*head交换机*/
    public static final String HEAD_QUEUE = "head_queue";


    /*广播交换机*/
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    /*top交换机*/
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    /*head交换机*/
    public static final String HEAD_EXCHANGE = "head_exchange";


    //配置队列
    @Bean
    public Queue queue() {
        return new Queue(MIAOSHA_QUEUE, true);
    }

    @Bean
    public Queue fanoutqueue1() {
        return new Queue(FANOUT_QUEUE_ONE, true);
    }

    @Bean
    public Queue fanoutqueue2() {
        return new Queue(FANOUT_QUEUE_TWO, true);
    }

    @Bean
    public Queue topicqueue() {
        return new Queue(TOPIC_QUEUE, true);
    }

    @Bean
    public Queue headqueue() {
        return new Queue(HEAD_QUEUE, true);
    }


    //配置交换机
    @Bean
    public FanoutExchange fanoutExchage() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchage() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public HeadersExchange headersExchage() {
        return new HeadersExchange(HEAD_EXCHANGE);
    }

    //交换机绑定队列
    @Bean
    public Binding FanoutBinding1() {
        return BindingBuilder.bind(fanoutqueue1()).to(fanoutExchage());
    }

    @Bean
    public Binding FanoutBinding2() {
        return BindingBuilder.bind(fanoutqueue2()).to(fanoutExchage());
    }

    @Bean
    public Binding TopicBinding2() {
        return BindingBuilder.bind(topicqueue()).to(topicExchage()).with("topic.#");
    }

    @Bean
    public Binding headerBinding() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headqueue()).to(headersExchage()).whereAll(map).match();
    }


}
