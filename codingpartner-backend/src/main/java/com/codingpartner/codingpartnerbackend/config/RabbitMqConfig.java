package com.codingpartner.codingpartnerbackend.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.codingpartner.codingpartnerbackend.constant.RabbitMqConstant.*;

@Configuration
public class RabbitMqConfig {
    /**
     * 创建队列
     *
     * @return 队列对象
     */
    @Bean
    public Queue QuestionSubmitQueue() {
        return QueueBuilder.durable(RABBITMQ_QUEUE_NAME).build();
    }

    /**
     * 创建交换机
     *
     * @return 交换机对象
     */
    @Bean
    public DirectExchange QuestionSubmitExchange() {
        return ExchangeBuilder.directExchange(RABBITMQ_EXCHANGE_NAME).build();
    }

    /**
     * 绑定队列和交换机
     *
     * @param QuestionSubmitExchange 交换机对象
     * @param QuestionSubmitQueue    队列对象
     * @return 绑定对象
     */
    @Bean
    public Binding QuestionSubmitBinding(
            @Qualifier("QuestionSubmitExchange") DirectExchange QuestionSubmitExchange,
            @Qualifier("QuestionSubmitQueue") Queue QuestionSubmitQueue
    ) {
        return BindingBuilder
                .bind(QuestionSubmitQueue)
                .to(QuestionSubmitExchange)
                .with(RABBITMQ_ROUTING_KEY);
    }
}
