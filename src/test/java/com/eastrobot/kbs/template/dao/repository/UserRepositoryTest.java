package com.eastrobot.kbs.template.dao.repository;

import com.eastrobot.kbs.template.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Test
    @Rollback
    public void testUserLogicAction() {
        User user = new User();
        user.setId("3428a92f542732d6015490e051ad0016");
        userRepository.delete(user, true);
    }
}