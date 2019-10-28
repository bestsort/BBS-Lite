package cn.bestsort.bbslite;

import cn.bestsort.bbslite.manager.MailService;
import cn.bestsort.bbslite.mapper.CommentMapper;
import cn.bestsort.bbslite.pojo.model.CommentKid;
import cn.bestsort.bbslite.pojo.model.CommentParent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitTest {

    @Autowired
    public CommentMapper commentMapper;
    @Test
    public void Gen(){
        List<CommentParent> parents = commentMapper.listComment(1);
        for(CommentParent parent:parents){
            System.out.println(parents);
            for (CommentKid kid:parent.getKids()){
                System.out.println(kid);
            }
        }
    }

}
