package cn.bestsort.bbslite;

import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.mapper.UserMapper;
import cn.bestsort.bbslite.model.Question;
import cn.bestsort.bbslite.model.UserExample;
import cn.bestsort.bbslite.service.MurmursHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Test
    public void contextLoads(){
        String account = "asbsssd";
        String passwd = "asdasd";
        String account2 = "asasssd";
        UserExample userExample = new UserExample();
        UserExample userExample2 = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(account);
        userExample2.createCriteria().andAccountIdEqualTo(account2);

        assert (MurmursHash.hashUnsigned(passwd + account).equals(userMapper.selectByExample(userExample).get(0).getPassword()));

        assert (MurmursHash.hashUnsigned(passwd + account2).equals(userMapper.selectByExample(userExample2).get(0).getPassword()));
    }

}
