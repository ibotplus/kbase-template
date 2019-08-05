package com.eastrobot.kbs.template;

import com.eastrobot.kbs.template.dao.repository.UserRepository;
import com.eastrobot.kbs.template.model.BeanConverter;
import com.eastrobot.kbs.template.model.entity.Biztpl;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.req.BiztplReq;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateApplicationTests {

    @Resource
    private UserRepository userRepository;
    @Resource
    private BeanConverter beanConverter;

    @Test
    public void contextLoads() {
    }

    @Test
    public void mapStruct() {
        User user = beanConverter.convert(
                UserReq.builder()
                        .mainStationId("111")
                        .username("222")
                        .password("333")
                        .visit("11")
                        .status(1)
                        .build()
        );

        System.out.println(user);


        Biztpl biztpl = beanConverter.convert(
                BiztplReq.builder()
                        .cateId("111")
                        .createDate(LocalDateTime.now())
                        .createUser("232323")
                        .build()
        );
        System.out.println(biztpl);
    }

}
