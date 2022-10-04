package com.example.crud_app.services.draft;

import com.example.crud_app.exceptions.DataNotFoundException;
import com.example.crud_app.exceptions.InsufficientAccessException;
import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.Post;
import com.example.crud_app.model.User;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class DraftServiceImpl implements DraftService {
    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Model getDraftsPosts(Model model, HttpServletRequest request) {
        int start, prev, limit = 2;
        int pagableIndex = 0;
        if (request.getParameter("start") != null) {
            start = Math.max(Integer.parseInt(request.getParameter("start")), 0);
            limit = Integer.parseInt(request.getParameter("limit"));
            pagableIndex = start / limit;
            model.addAttribute("start", (start + limit));
            model.addAttribute("limit", limit);
        } else {
            model.addAttribute("start", limit);
            model.addAttribute("limit", limit);
        }

        Pageable postPageable = PageRequest.of(pagableIndex, limit);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByUsername(auth.getName());
        if (optionalUser.isEmpty()) {
            throw new InsufficientAccessException();
        }
        User user = optionalUser.get();
        if (user.getRole().equals(Constants.ADMIN)) {
            List<Post> drafts = postRepository.findByIsPublished(false, postPageable);
            if (drafts == null || drafts.isEmpty()){
                throw new DataNotFoundException();
            }
            model.addAttribute("drafts", drafts );

            return model;
        }else {
            List<Post> drafts = postRepository.findDraftsOfAuthor(user.getUsername());
            if (drafts == null || drafts.isEmpty()){
                throw new DataNotFoundException();
            }
            model.addAttribute("drafts", drafts );
            return model;
        }
    }
}
