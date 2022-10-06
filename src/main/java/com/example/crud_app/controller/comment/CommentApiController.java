package com.example.crud_app.controller.comment;

import com.example.crud_app.services.comment.CommentApiService;
import com.example.crud_app.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/comment")
public class CommentApiController {
    @Autowired
    public void setCommentApiService(CommentApiService commentApiService) {
        this.commentApiService = commentApiService;
    }
    private CommentApiService commentApiService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<Object> createComment(@PathVariable int postId, HttpServletRequest request){
        return commentApiService.createComment(postId, request);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable int commentId,HttpServletRequest request){
        return commentApiService.deleteComment(commentId, request);
    }

    @GetMapping("/update/{commentId}")
    public ResponseEntity<Object> getUpdateCommentPage(@PathVariable int commentId, Model model) {
        return commentApiService.getUpdateCommentPage(commentId, model);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<Object> saveUpdatedComment(@PathVariable int commentId, HttpServletRequest request){
        return commentApiService.saveUpdatedComment(commentId, request);
    }
}
