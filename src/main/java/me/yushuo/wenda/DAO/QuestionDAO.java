package me.yushuo.wenda.DAO;

import me.yushuo.wenda.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FILEDS = " title, content, user_id, created_date, comment_count ";
    String SELECT_FILEDS = " id, title, content, user_id, created_date, comment_count ";

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FILEDS, ")",
            "values (", "#{title}, #{content}, #{userId}, #{createdDate}, #{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);
}
