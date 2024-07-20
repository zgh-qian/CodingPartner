package com.codingpartner.codingpartnerbackend.mq.consumer;

import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import com.codingpartner.codingpartnerbackend.service.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

import static com.codingpartner.codingpartnerbackend.constant.RabbitMqConstant.RABBITMQ_QUEUE_NAME;

@Component
@Slf4j
public class QuestionSubmitConsumer {

    @Resource
    private JudgeService judgeService;

    /**
     * 接收消息并进行判题
     *
     * @param message     接收到的消息
     * @param channel     消息通道
     * @param deliveryTag 消息标签
     */
    @SneakyThrows
    @RabbitListener(queues = {RABBITMQ_QUEUE_NAME}, ackMode = "MANUAL")
    public void listen(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("Received message: " + message);
        long questionSumbitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSumbitId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
