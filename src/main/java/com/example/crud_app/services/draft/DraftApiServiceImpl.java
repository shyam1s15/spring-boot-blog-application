package com.example.crud_app.services.draft;

import com.example.crud_app.exceptions.data_not_found.DataNotFoundApiException;
import com.example.crud_app.exceptions.data_not_found.DataNotFoundException;
import com.example.crud_app.exceptions.insufficient_access.InsufficientAccessApiException;
import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.Post;
import com.example.crud_app.model.User;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DraftApiServiceImpl implements DraftApiService{
    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostRepository postRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> getDraftsPosts(HttpServletRequest request) {
        Map<String, Object> responseMapModel = new HashMap<>();
        int start, prev, limit = 2;
        int pagableIndex = 0;
        if (request.getParameter("start") != null) {
            start = Math.max(Integer.parseInt(request.getParameter("start")), 0);
            limit = Integer.parseInt(request.getParameter("limit"));
            pagableIndex = start / limit;
            responseMapModel.put("start", (start + limit));

            responseMapModel.put("limit", limit);
        } else {
            responseMapModel.put("start", limit);
            responseMapModel.put("limit", limit);
        }

        Pageable postPageable = PageRequest.of(pagableIndex, limit);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByUsername(auth.getName());
        if (optionalUser.isEmpty()) {
            throw new InsufficientAccessApiException();
        }
        User user = optionalUser.get();
        if (user.getRole().equals(Constants.ADMIN)) {
            List<Post> drafts = postRepository.findPosts(false, postPageable);
            if (drafts == null || drafts.isEmpty()){
                throw new DataNotFoundApiException();
            }
            responseMapModel.put("drafts", drafts );
            return new ResponseEntity<>(responseMapModel, HttpStatus.OK);
        }else {
            List<Post> drafts = postRepository.findDraftsOfAuthor(user.getUsername());
            if (drafts == null || drafts.isEmpty()){
                throw new DataNotFoundException();
            }
            responseMapModel.put("drafts", drafts );
            return new ResponseEntity<>(responseMapModel, HttpStatus.OK);
        }
    }
}
