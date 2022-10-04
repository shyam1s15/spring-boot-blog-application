package com.example.crud_app.controller;
import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.TagRepository;
import com.example.crud_app.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    public static final String TITLE = "title";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String AUTHOR = "author";
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(){
        return "redirect:/posts/read";
    }
    @GetMapping("/create/post")
    public String getPostPage(Model model) {
        model = postService.getPostPage(model);
        return "posts/postPage";
    }

    @PostMapping("/create/post")
    public String savePostPageData(HttpServletRequest request) {
        postService.savePostPageData(request);
        return "redirect:/posts/read";
    }
    @GetMapping("/read")
    public String readAllPostsWithParams(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "tagName", required = false) String[] tagNames,
            @RequestParam(value = "authorName", required = false) String[] authorNames,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "limit", required = false) String limit) {

        model = postService.readAllPostsWithParams(model, request, tagNames,
                authorNames, search, sortBy, start, limit);
        return "posts/postsOverview";
    }

    @GetMapping("/filter")
    public String getFilteredResultsPage(HttpServletRequest request, Model model) {
        model = postService.getFilteredResultsPage(request, model);
        if (request.getParameter("filter").equalsIgnoreCase("authors")) {
            return "filters/authors";
        }
        return "filters/tags";
    }

    @GetMapping("/read/{id}")
    public String readPostDetail(@PathVariable int id, Model model) {
        model = postService.readPostDetail(id, model);
        return "posts/postDetail";
    }

    @GetMapping("/update/{postId}")
    public String getPostEditPage(@PathVariable int postId, Model model) {
        model = postService.getPostEditPage(postId, model);
        return "posts/editPost";
    }

    @PostMapping("/update/{postId}")
    public String updatePostPageData(@PathVariable int postId, HttpServletRequest request) {
        postService.updatePostPageData(postId, request);
        return "redirect:/posts/read/" + postId;
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return "redirect:/posts/read";
    }
    @GetMapping("/drafts")
    public String getDraftsPosts(Model model, HttpServletRequest request) {
        model = postService.getDraftsPosts(model, request);
        return "drafts/draftsOverview";
    }
}
