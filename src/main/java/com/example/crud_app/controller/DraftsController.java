package com.example.crud_app.controller;

import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.TagRepository;
import com.example.crud_app.model.Post;
import com.example.crud_app.services.draft.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/drafts")
public class DraftsController {
    @Autowired
    private DraftService draftService;

    @GetMapping("/read")
    public String getDraftsPosts(Model model, HttpServletRequest request){
        model = draftService.getDraftsPosts(model, request);
        return "drafts/draftsOverview";
    }
}
