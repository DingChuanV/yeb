package com.uin.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.uin.server.pojo.Employee;
import com.uin.server.pojo.MailLog;
import com.uin.server.service.IEmployeeService;
import com.uin.server.service.IMailLogService;
import com.uin.server.pojo.MailConstants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月18日 16:36
 * \* Description: 邮件发送定时任务-->重新发送邮件
 * \
 */
@Component
public class MailTask {
    /**
     * 用来获取所有的数据库中的消息
     */
    @Autowired
    private IMailLogService mailLogService;
    /**
     * 用来引入rabbitmq来操作rabbitmq
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     *
     */
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 邮件发送定时任务 10s一次
     * 定时任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        /**
         * select * from t_mail_log where status=0 and tryTime  < LocalDateTime
         */
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            /**
             * 重试次数小于3次
             */
            if (3 <= mailLog.getCount()) {
                /**
                 * update t_mail_log set status=2 where msgId=#{msgId}
                 * 表示发送失败
                 */
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 2).eq("msgId", mailLog.getMsgId()));
            }
            /**
             * 更新失败重试次数，更新时间，重试时间
             */
            mailLogService.update(new UpdateWrapper<MailLog>().set("count", mailLog.getCount() + 1).set("updateTime", LocalDateTime.now())
                    .set("tryTime", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).eq("msgId", mailLog.getMsgId()));
            /**
             *
             */
            Employee employee = employeeService.getEmployee(mailLog.getEid()).get(0);
            System.out.println("MailTask:employee=" + employee);
            System.out.println("MailTask:msgId=" + mailLog.getMsgId());
            /**
             * 重新发送消息
             * 参数：交换机名、路由键、对象、
             * new CorrelationData(mailLog.getMsgId()) 重新发送
             */
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, employee, new CorrelationData(mailLog.getMsgId()));
        });
    }
}
