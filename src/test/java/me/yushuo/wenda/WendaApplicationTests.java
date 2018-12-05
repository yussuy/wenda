package me.yushuo.wenda;

import me.yushuo.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class WendaApplicationTests {

    @Test
    public void initDatabase() {
        Random random = new Random();
        User user = new User();
        for (int i = 0; i < 10; i++) {
            user.setHeadUrl("https://avatars0.githubusercontent.com/u/" + random.nextInt(10000));
            user.setName(String.format("user%d", i));
            user.setPassword("");
            user.setSalt("");

        }
    }

    @Test
    public void contextLoads() {
    }

}
