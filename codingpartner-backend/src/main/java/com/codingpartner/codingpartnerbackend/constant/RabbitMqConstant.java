package com.codingpartner.codingpartnerbackend.constant;

public interface RabbitMqConstant {

    /**
     * 队列名称
     */
    String RABBITMQ_QUEUE_NAME = "codingpartner_question_submit_queue";

    /**
     * 交换机名称
     */
    String RABBITMQ_EXCHANGE_NAME = "codingpartner_question_submit_exchange";

    /**
     * 路由键
     */
    String RABBITMQ_ROUTING_KEY = "codingpartner_question_submit_routing_key";
}
