package com.example.crud_app.services.post;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PostApiService {
    public ResponseEntity<Object> index();
    public ResponseEntity<Object> getPostPage(Model model);
    public ResponseEntity<Object> savePostPageData(HttpServletRequest request);
    public ResponseEntity<?> readAllPostsWithParams(
            HttpServletRequest request,
            @RequestParam(value = "tagName", required = false) String[] tagNames,
            @RequestParam(value = "authorName", required = false) String[] authorNames,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "limit", required = false) String limit);
    public ResponseEntity<Object> getFilteredResultsPage(HttpServletRequest request, Model model);
    public ResponseEntity<Object> readPostDetail(@PathVariable int id, Model model);
    public ResponseEntity<Object> getPostEditPage(@PathVariable int postId, Model model);
    public ResponseEntity<Object> updatePostPageData(@PathVariable int postId, HttpServletRequest request);

    public ResponseEntity<Object> deletePost(@PathVariable int postId);
    public ResponseEntity<Object> getDraftsPosts(Model model, HttpServletRequest request);
}
