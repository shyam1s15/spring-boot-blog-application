package com.example.crud_app.services.draft;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;

@Service
public interface DraftService {
    public Model getDraftsPosts(Model model, HttpServletRequest request);
}
