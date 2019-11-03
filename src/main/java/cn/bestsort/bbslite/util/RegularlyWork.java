package cn.bestsort.bbslite.util;

import cn.bestsort.bbslite.manager.MailService;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName DeleteRegularly
 * @Description TODO
 * @Author bestsort
 * @Date 2019/10/24 下午3:17
 * @Version 1.0
 */
@Slf4j
@Component
public class RegularlyWork {
    /**
     * 每天凌晨三点清除未激活用户
     */
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;

    @Scheduled(cron="0 0 3 * * ?")
    public void clearUserDatabase(){
        log.debug("A total of {} cleaned inactive accounts", userService.clearUnActivateUser());
    }

    /**
     * 每隔三分钟检查发送失败的邮件并重新发送
     * 累计三次失败后记录日志
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void resendMail(){
        int lenth = MailService.failMail.size();
        if(lenth != 0) {
            log.debug("Start sending failed messages");
            for (int i = 0; i < lenth; i++) {
                MailVo mail = MailService.failMail.poll();
                Objects.requireNonNull(mail).setTimes(mail.getTimes() - 1);
                if (mail.getTimes() > 0) {
                    log.info("Resend mail  -> {}", mail.sendTo);
                    mailService.sendMail(mail);
                } else {
                    log.error("The mail failed to be sent three times in a row, please check it-> {} ", mail.sendTo);
                }
            }
        }
    }
}
