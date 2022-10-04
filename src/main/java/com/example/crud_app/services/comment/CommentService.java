package com.example.crud_app.services.comment;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


public interface CommentService {
    public void createComment(@PathVariable int postId, HttpServletRequest request);
    public int deleteComment(@PathVariable int commentId,HttpServletRequest request);
    public Model getUpdateCommentPage(@PathVariable int commentId, Model model);
    public int saveUpdatedComment(@PathVariable int commentId, HttpServletRequest request);
}
