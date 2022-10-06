package com.example.crud_app.services.comment;

import com.example.crud_app.exceptions.data_not_found.DataNotFoundApiException;
import com.example.crud_app.exceptions.data_not_found.DataNotFoundException;
import com.example.crud_app.exceptions.insufficient_access.InsufficientAccessApiException;
import com.example.crud_app.exceptions.insufficient_access.InsufficientAccessException;
import com.example.crud_app.exceptions.page_not_found.PageNotFoundException;
import com.example.crud_app.jpa.CommentRepository;
import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.Comment;
import com.example.crud_app.model.Post;
import com.example.crud_app.model.User;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentApiServiceImpl implements CommentApiService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public ResponseEntity<Object> createComment(int postId, HttpServletRequest request) {
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
            return new ResponseEntity<>("Comment Created Successfully with ID: " + comment.getId(), HttpStatus.OK);
        } else {
            throw new DataNotFoundException();
        }
    }

    @Override
    public ResponseEntity<Object> deleteComment(int commentId, HttpServletRequest request) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByUsername(auth.getName());

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            User user = optionalUser.get();
            Post post = comment.getPostId();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                commentRepository.deleteById(commentId);
                return new ResponseEntity<>("Post deleted successfully with id " + commentId, HttpStatus.OK);
            } else if (user.getRole().equals(Constants.ADMIN)) {
                commentRepository.deleteById(commentId);
                return new ResponseEntity<>("Post deleted successfully with id " + commentId, HttpStatus.OK);
            } else {
                throw new InsufficientAccessApiException();
            }
        } else {
            throw new DataNotFoundApiException();
        }
    }

    @Override
    public ResponseEntity<Object> getUpdateCommentPage(int commentId, Model model) {
        throw new PageNotFoundException();
    }

    @Override
    public ResponseEntity<Object> saveUpdatedComment(int commentId, HttpServletRequest request) {
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
                throw new InsufficientAccessApiException();
            }
        }else {
            throw new DataNotFoundApiException();
        }
        return new ResponseEntity<>("Comment Saved Successfully", HttpStatus.OK);
    }
}
