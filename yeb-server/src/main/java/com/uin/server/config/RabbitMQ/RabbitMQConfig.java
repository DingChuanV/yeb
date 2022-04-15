package com.uin.server.config.RabbitMQ;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.uin.server.pojo.MailConstants;
import com.uin.server.pojo.MailLog;
import com.uin.server.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月18日 16:21
 * \* Description: RabbitMQConfig配置类
 * \
 */

@Configuration
public class RabbitMQConfig {
    /**
     * 可以使用lombok注解的@Slf4j
     */
    public static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMailLogService mailLogService;

    /**
     * 消息失败回调和成功回调
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        /**
         * 缓存连接工厂
         */
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);

        /**
         *  消息确认回调,确认消息是否到达broker（RabbitMQ）
         *  data:消息唯一标识
         *  ack：确认结果
         *  cause：失败原因
         * @author wanglufei
         * @date 2021/8/18 16:32
         * @return org.springframework.amqp.rabbit.core.RabbitTemplate
         * @param
         * @return
         */


        /**
         * 确认回调 接口函数
         */
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            String msgId = data.getId();
            System.out.println("RabbitMQConfig: msgId = " + msgId);
            if (ack) {
                logger.info("{}==========>消息发送成功", msgId);
                /**
                 * 去数据库操作 跟新status为1
                 */
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 1).eq("msgId", msgId));
            } else {
                logger.info("{}==========>消息发送失败", msgId);
            }
        });
        /**
         * 消息失败回调，比如router（路由）不到queue时回调
         * msg:消息主题
         * repCode:响应码
         * repText:响应描述
         * exchange:交换机
         * routingKey:路由键
         */

        /**
         * 失败回调
         */
        rabbitTemplate.setReturnCallback((msg, repCode, repText, exchange, routingKey) -> {
            logger.info("{}=======================>消息发送到queue时失败", msg.getBody());
        });
        return rabbitTemplate;
    }

    /**
     * 队列的名字，开启持久护
     */
    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME, true);
    }

    /**
     * 交换机：直连交换机
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    /**
     * 将队列和交换机进行绑定，用路由key
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
