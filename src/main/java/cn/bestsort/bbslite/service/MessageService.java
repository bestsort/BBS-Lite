package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.mapper.*;
import cn.bestsort.bbslite.pojo.model.*;
import cn.bestsort.bbslite.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bestsort
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public static int ALL = 0;
    public static int THUMB_UP = 1;
    public static int FOLLOW_USER = 2;
    public static int FOLLOW_QUESTION = 3;
    public static int COMMENT = 4;
    public List<MessageVo>getListByUser(long userId,int type){
        MessageExample example = new MessageExample();
        example.createCriteria().andSendToEqualTo(userId);
        List<MessageVo> result = new ArrayList<>();
        List<Message> list = messageMapper.selectByExample(example);
        Set<Long> userIdSet = new HashSet<>();
        Set<Long> questionSet = new HashSet<>();
        for (Message item:list){
            userIdSet.add(item.getSendBy());
            questionSet.add(item.getSendTo());
            MessageVo messageVo = MessageVo.builder()
                    .gmtCreate(item.getGmtCreate())
                    .isRead(item.getIsRead().equals((byte)1))
                    .item(FunctionItem.getByCode(item.getType()))
                    .build();
            result.add(messageVo);
        }

        /*获得去重后的用户信息*/
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(new ArrayList<>(userIdSet));
        Map<Long,User> users = userMapper.selectByExample(userExample)
                .stream().collect(Collectors.toMap(User::getId, user->user));

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdIn(new ArrayList<>(questionSet));
        Map<Long,Question>questions = questionMapper.selectByExample(questionExample)
                .stream().collect(Collectors.toMap(Question::getId,question->question));
        for (MessageVo item : result){
            item.builder().title(questions.get(item.getSendToId()).getTitle());
        }
        return null;
    }
    private String buildMessage(){
        return null;
    }
}
