package com.example.crud_app.controller.drafts;

import com.example.crud_app.services.draft.DraftApiService;
import com.example.crud_app.services.draft.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/drafts")
public class DraftsApiController {
    @Autowired
    public void setDraftApiService(DraftApiService draftApiService) {
        this.draftApiService = draftApiService;
    }
    private DraftApiService draftApiService;

    @GetMapping("/read")
    public ResponseEntity<Object> getDraftsPosts(Model model, HttpServletRequest request){
        return draftApiService.getDraftsPosts(request);
//        return "drafts/draftsOverview";
    }
}
