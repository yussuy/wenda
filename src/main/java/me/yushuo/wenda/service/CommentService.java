package me.yushuo.wenda.service;

import me.yushuo.wenda.dao.CommentDAO;
import me.yushuo.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityid, int entityType) {
        return commentDAO.getCommentCount(entityid, entityType);
    }

    public void update(){

    }
}