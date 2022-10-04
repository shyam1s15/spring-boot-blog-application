package com.example.crud_app.services.comment;

import com.example.crud_app.exceptions.DataNotFoundException;
import com.example.crud_app.exceptions.InsufficientAccessException;
import com.example.crud_app.jpa.CommentRepository;
import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.Comment;
import com.example.crud_app.model.Post;
import com.example.crud_app.model.User;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void createComment(int postId, HttpServletRequest request) {
        try {
            Optional<Post> postOptional = postRepository.findById(postId);
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                Comment comment = new Comment();
                comment.setName(request.getParameter("name"));
                comment.setEmail(request.getParameter("email"));
                comment.setComment(request.getParameter("comment"));
                comment.setCreatedAt(LocalDateTime.now());
                comment.setUpdatedAt(LocalDateTime.now());
                comment.setPostId(post);
                commentRepository.save(comment);
            } else {
                throw new DataNotFoundException();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int deleteComment(int commentId, HttpServletRequest request) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );

        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            User user = optionalUser.get();
            Post post = comment.getPostId();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                commentRepository.deleteById(commentId);
                return Integer.parseInt(request.getParameter("postId"));
            } else if (user.getRole().equals(Constants.ADMIN)) {
                commentRepository.deleteById(commentId);
                return Integer.parseInt(request.getParameter("postId"));
            } else{
                throw new InsufficientAccessException();
            }
        } else{
            throw new DataNotFoundException();
        }
    }

    @Override
    public Model getUpdateCommentPage(int commentId, Model model) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );

        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            model.addAttribute("comment", comment);
            User user = optionalUser.get();
            Post post = comment.getPostId();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                return model;
            } else if (user.getRole().equals(Constants.ADMIN)) {
                return model;
            } else{
                throw new InsufficientAccessException();
            }

        }else {
            throw new DataNotFoundException();
        }
    }

    @Override
    public int saveUpdatedComment(int commentId, HttpServletRequest request) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = null;
        if (optionalComment.isPresent()) {
            comment = optionalComment.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );
            if (optionalUser.isEmpty()) {
                throw new InsufficientAccessException();
            }

            comment.setUpdatedAt(LocalDateTime.now());
            comment.setName(request.getParameter("name"));
            comment.setEmail(request.getParameter("email"));
            comment.setComment(request.getParameter("comment"));

            User user = optionalUser.get();
            Post post = comment.getPostId();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                commentRepository.save(comment);
            } else if (user.getRole().equals(Constants.ADMIN)) {
                commentRepository.save(comment);
            } else{
                throw new InsufficientAccessException();
            }
        }else {
            throw new DataNotFoundException();
        }
        return comment.getPostId().getId();
    }
}
