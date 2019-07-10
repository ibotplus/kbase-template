package com.eastrobot.kbs.template;

import com.eastrobot.kbs.template.dao.mapper.UserMapper;
import com.eastrobot.kbs.template.dao.repository.UserRepository;
import com.eastrobot.kbs.template.model.BeanConverter;
import com.eastrobot.kbs.template.model.entity.Biztpl;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.BiztplVO;
import com.eastrobot.kbs.template.model.vo.UserVO;
import com.google.common.base.Converter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateApplicationTests {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        Optional.of(userRepository.findById("402883435f950338015fa3bd4bb90009"))
                .map(Optional::toString)
                .ifPresent(System.out::println);

        Optional.ofNullable(userMapper.selectById("402883435f950338015fa3bd4bb90009"))
                .map(User::toString)
                .ifPresent(System.out::println);
    }

    @Test
    public void testConverter() {
        Converter<UserVO, User> converter = new Converter<UserVO, User>() {
            @Override
            protected User doForward(UserVO userVO) {
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
            protected UserVO doBackward(User user) {
                UserVO vo = UserVO.builder()
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
        User user = BeanConverter.INSTANCE.fromVO(
                UserVO.builder()
                        .mainStationId("111")
                        .username("222")
                        .password("333")
                        .visit("11")
                        .status(1)
                        .build()
        );

        System.out.println(user);


        Biztpl biztpl = BeanConverter.INSTANCE.fromVO(
                BiztplVO.builder()
                        .cateId("111")
                        .createDate(LocalDateTime.now())
                        .createUser("232323")
                        .build()
        );
        System.out.println(biztpl);
    }

}
