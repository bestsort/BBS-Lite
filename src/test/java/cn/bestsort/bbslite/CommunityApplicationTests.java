package cn.bestsort.bbslite;

import cn.bestsort.bbslite.mapper.QuestionExtMapper;
import cn.bestsort.bbslite.model.Question;
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
    QuestionExtMapper questionExtMapper;

    @Test
    public void contextLoads(){
        String search = "标签1";
        String order = "gmt_create desc";
        List<Question> questions = questionExtMapper.listBySearch(search,order);
        for (Question question : questions) {
            System.out.println(question);
        }
    }

}
