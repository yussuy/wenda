package me.yushuo.wenda;

import me.yushuo.wenda.dao.QuestionDAO;
import me.yushuo.wenda.dao.UserDAO;
import me.yushuo.wenda.model.Question;
import me.yushuo.wenda.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Test
    public void initDatabase() {
        Random random = new Random();
        Date date = new Date();


        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setHeadUrl("https://avatars0.githubusercontent.com/u/" + random.nextInt(10000));
            user.setName(String.format("user%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            Question question = new Question();
            question.setTitle(String.format("title%d", i));
            question.setContent("balabalabalabala"+i);
            question.setUserId(i + 1);
            question.setCommentCount(i);
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreatedDate(date);
            questionDAO.addQuestion(question);

        }
        Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));
        List<Question> questionList = questionDAO.selectLatestQuestions(0,0,10);
        for (Question question: questionList) {
            System.out.println(question);
        }
    }
}
