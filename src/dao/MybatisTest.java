package dao;

import dao.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class MybatisTest {

    public static void main(String[] args) {
        ApplicationContext ac  = new FileSystemXmlApplicationContext("./config/mybatis.xml");
        UserMapper userMapper = (UserMapper) ac.getBean("userMapper");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        System.out.println(userMapper.selectUser(user));
    }
}