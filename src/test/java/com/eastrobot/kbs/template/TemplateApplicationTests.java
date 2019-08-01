package com.eastrobot.kbs.template;

import com.eastrobot.kbs.template.dao.repository.UserRepository;
import com.eastrobot.kbs.template.model.BeanConverter;
import com.eastrobot.kbs.template.model.entity.Biztpl;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.req.BiztplReq;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.google.common.base.Converter;
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

    @Test
    public void contextLoads() {
    }

    @Test
    public void testConverter() {
        Converter<UserReq, User> converter = new Converter<UserReq, User>() {
            @Override
            protected User doForward(UserReq userVO) {
                User user = User.builder()
                        .username(userVO.getUsername())
                        .userInfoId(userVO.getUserInfoId())
                        .visit(userVO.getVisit())
                        .password(userVO.getPassword())
                        .mainStationId(userVO.getMainStationId())
                        .build();
                user.setId(userVO.getId());
                user.setDelFlag(0);
                return user;
            }

            @Override
            protected UserReq doBackward(User user) {
                UserReq vo = UserReq.builder()
                        .username(user.getUsername())
                        .userInfoId(user.getUserInfoId())
                        .visit(user.getVisit())
                        .password(user.getPassword())
                        .mainStationId(user.getMainStationId())
                        .build();
                vo.setId(user.getId());
                return vo;
            }
        };
    }

    @Test
    public void mapStruct() {
        User user = BeanConverter.INSTANCE.convert(
                UserReq.builder()
                        .mainStationId("111")
                        .username("222")
                        .password("333")
                        .visit("11")
                        .status(1)
                        .build()
        );

        System.out.println(user);


        Biztpl biztpl = BeanConverter.INSTANCE.convert(
                BiztplReq.builder()
                        .cateId("111")
                        .createDate(LocalDateTime.now())
                        .createUser("232323")
                        .build()
        );
        System.out.println(biztpl);
    }

}
