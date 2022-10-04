package com.example.crud_app.services.post;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public interface PostService {
    public String index();
    public Model getPostPage(Model model);
    public void savePostPageData(HttpServletRequest request);
    public Model readAllPostsWithParams(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "tagName", required = false) String[] tagNames,
            @RequestParam(value = "authorName", required = false) String[] authorNames,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "limit", required = false) String limit);
    public Model getFilteredResultsPage(HttpServletRequest request, Model model);
    public Model readPostDetail(@PathVariable int id, Model model);
    public Model getPostEditPage(@PathVariable int postId, Model model);
    public void updatePostPageData(@PathVariable int postId, HttpServletRequest request);

    public void deletePost(@PathVariable int postId);
    public Model getDraftsPosts(Model model, HttpServletRequest request);
}
