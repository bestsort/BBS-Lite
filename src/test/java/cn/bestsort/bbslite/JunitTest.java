package cn.bestsort.bbslite;

import cn.bestsort.bbslite.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitTest {

    @Autowired
    private MailService mail;
    @Test
    public void sendSimpleMail(){
        mail.sendMail("bestsort@qq.com","this-is-a-token","this-is-account");
    }
    @Test
    public void Gen(){
        //String res = mail.generatorSignUpMail("bestsort@qq.com","this-is-a-token");
        //System.out.println(res);
    }

}
