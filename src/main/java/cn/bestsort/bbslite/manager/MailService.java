package cn.bestsort.bbslite.manager;

import cn.bestsort.bbslite.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Mail
 * @Description TODO 失败重传
 * @Author bestsort
 * @Date 2019/10/23 下午8:10
 * @Version 1.0
 */
@Slf4j
@Service
public class MailService {
    @Value("${bbs.url:}")
    private String homeUrl;
    @Value("${spring.mail.username:}")
    private String sendBy;
    @Value("${bbs.mail.title:}")
    private String title;
    @Autowired
    private JavaMailSenderImpl mailSender;
    private TemplateEngine templateEngine;
    public static Queue<MailVo> failMail = new LinkedList<>();

    @Async
    public void sendMail(String token ,String account,String mailAddress){
        MailVo mailVo = new MailVo();
        mailVo.setSendText(generatorSignUpMail(token,account));
        mailVo.setSendSubject(title);
        mailVo.setSendTo(mailAddress);
        sendSignUpMail(mailVo);
    }
    public void sendSignUpMail(MailVo mail){
        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(sendBy);
                messageHelper.setTo(mail.sendTo);
                messageHelper.setSubject(mail.sendSubject);
                messageHelper.setText(mail.sendText,true);
            };
            mailSender.send(messagePreparator);
            log.info("mail send success --> mail:sendTo:{}",mail.sendTo);
        } catch (MailException e) {
            failMail.offer(mail);
            log.error("mail send fail,please chick it --> mail.sendTo:{}",mail.sendTo);
            log.error(e.getMessage());
        }
    }
    @Autowired
    public MailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    public String generatorSignUpMail(String token,String account) {
        Context context = new Context();
        String activeUrl = homeUrl + "/activate?"+
                "token="+token+ "&" +
                "account="+account;

        context.setVariable("homeUrl", homeUrl);
        context.setVariable("activeUrl", activeUrl);
        context.setVariable("userAccount", account);
        return templateEngine.process("mail-template", context);
    }
}
