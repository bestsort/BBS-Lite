package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.dao.mapper.ThumbUpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ThumbUpService
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-28 下午6:57
 * @Version 1.0
 */

@Service
public class ThumbUpService {
    @Autowired
    ThumbUpMapper thumbUpMapper;

}
