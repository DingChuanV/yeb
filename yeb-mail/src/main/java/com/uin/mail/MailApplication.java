package com.uin.mail;

import com.uin.server.vo.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月06日 18:33
 * \* Description: 添加员工使用Rabbitmq队列发送欢迎邮件
 * \
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //去除数据库的配置
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    /**
     * 绑定队列
     *
     * @return org.springframework.amqp.core.Queue
     * @author wanglufei
     * @date 2022/4/14 10:29 PM
     */
    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
