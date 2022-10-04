package com.example.crud_app.controller;
import com.example.crud_app.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    private CommentService commentService;

    @PostMapping("/create/{postId}")
    public String createComment(@PathVariable int postId, HttpServletRequest request){
        commentService.createComment(postId, request);
        return "redirect:/posts/read/"+postId;
    }

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable int commentId,HttpServletRequest request){
        int postId = commentService.deleteComment(commentId, request);
        return "redirect:/posts/read/"+postId;
    }

    @GetMapping("/update/{commentId}")
    public String getUpdateCommentPage(@PathVariable int commentId, Model model) {
        model = commentService.getUpdateCommentPage(commentId, model);
        return "comments/updateComment";
    }

    @PostMapping("/update/{commentId}")
    public String saveUpdatedComment(@PathVariable int commentId, HttpServletRequest request){
        int postId = commentService.saveUpdatedComment(commentId, request);
        return "redirect:/posts/read/"+postId;
    }
}
