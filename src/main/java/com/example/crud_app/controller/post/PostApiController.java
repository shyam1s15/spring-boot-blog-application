package com.example.crud_app.controller.post;

import com.example.crud_app.exceptions.page_not_found.PageNotFoundApiException;
import com.example.crud_app.services.post.PostApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {
    public static final String TITLE = "title";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String AUTHOR = "author";

    @Autowired
    public void setPostApiService(PostApiService postApiService) {
        this.postApiService = postApiService;
    }
    private PostApiService postApiService;

    @GetMapping("/")
    public void index(){
        throw new PageNotFoundApiException();
    }

    @GetMapping("/create/post")
    public void getPostPage(Model model) {
        throw new PageNotFoundApiException();
    }

    @PostMapping("/create/post")
    public ResponseEntity<Object> savePostPageData(HttpServletRequest request) {
        return postApiService.savePostPageData(request);
    }
    @GetMapping("/read")
    public ResponseEntity<?> readAllPostsWithParams(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "tagName", required = false) String[] tagNames,
            @RequestParam(value = "authorName", required = false) String[] authorNames,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "limit", required = false) String limit) {
        return postApiService.readAllPostsWithParams(request, tagNames,
                authorNames, search, sortBy, start, limit);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> getFilteredResultsPage(HttpServletRequest request, Model model) {
        return postApiService.getFilteredResultsPage(request, model);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Object> readPostDetail(@PathVariable int id, Model model) {
        return postApiService.readPostDetail(id, model);
    }

    @GetMapping("/update/{postId}")
    public ResponseEntity<Object> getPostEditPage(@PathVariable int postId, Model model) {
        return postApiService.getPostEditPage(postId, model);
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<Object> updatePostPageData(@PathVariable int postId, HttpServletRequest request) {
        return postApiService.updatePostPageData(postId, request);
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable int postId) {
        return postApiService.deletePost(postId);
    }
    @GetMapping("/drafts")
    public ResponseEntity<Object> getDraftsPosts(Model model, HttpServletRequest request) {
        return postApiService.getDraftsPosts(model, request);
    }
}
