package me.yushuo.wenda;

import me.yushuo.wenda.model.Question;
import me.yushuo.wenda.service.LikeService;
import me.yushuo.wenda.service.QuestionService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WendaApplication.class)
//@SpringBootTest
public class LikeServiceTest {

    @Autowired
    LikeService likeService;

    @Autowired
    QuestionService questionService;

    @Before
    public void setUp() {
        System.out.println("setUp");
    }

    @After
    public void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    public void testLike() {
        System.out.println("testLike");
        likeService.like(3, 1, 3);
        Assert.assertEquals(1, likeService.getLikeStatus(3, 1, 3));

        likeService.dislike(3, 1, 3);
        Assert.assertEquals(-1, likeService.getLikeStatus(3, 1,3));

    }

    @Test
    public void test() {
        Question q = new Question();
        q.setTitle("ceshi");
        q.setContent("ceshi");
        q.setCreatedDate(new Date());
        questionService.addQuestion(q);
    }

    @Test
    public void testXXX() {
        System.out.println("testXXX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        System.out.println("testException");
        throw new IllegalArgumentException("异常发生了");
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }
//    @Test
//    public void db() {
//          //测试test会不会对数据库和redis产生影响   @Rollback @事务
////        q.setTitle("8888");
////        questionService.addQuestion(q);
//    }
}
