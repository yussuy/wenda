package me.yushuo.wenda.dao;

import ch.qos.logback.classic.db.names.TableName;
import me.yushuo.wenda.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
    String SELECT_FILEDS = " id " + INSERT_FIELDS;

    @Insert({" insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values " +
                "(#{fromId}, #{toId}, #{content}, #{createdDate}, #{hasRead}, #{conversationId})"})
    int addMessage(Message message);

    @Select({" select ", SELECT_FILEDS, " from ", TABLE_NAME, " where conversation_id = #{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);


}
