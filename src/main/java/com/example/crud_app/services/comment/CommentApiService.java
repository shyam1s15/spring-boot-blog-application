package com.example.crud_app.services.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public interface CommentApiService {
    public ResponseEntity<Object> createComment(@PathVariable int postId, HttpServletRequest request);
    public ResponseEntity<Object> deleteComment(@PathVariable int commentId,HttpServletRequest request);
    public ResponseEntity<Object> getUpdateCommentPage(@PathVariable int commentId, Model model);
    public ResponseEntity<Object> saveUpdatedComment(@PathVariable int commentId, HttpServletRequest request);
}
