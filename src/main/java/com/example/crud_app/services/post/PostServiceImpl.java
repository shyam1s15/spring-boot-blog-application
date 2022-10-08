package com.example.crud_app.services.post;

import com.example.crud_app.exceptions.data_not_found.DataNotFoundException;
import com.example.crud_app.exceptions.insufficient_access.InsufficientAccessException;
import com.example.crud_app.exceptions.other.OtherException;
import com.example.crud_app.jpa.PostRepository;
import com.example.crud_app.jpa.PostSpecification;
import com.example.crud_app.jpa.TagRepository;
import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.Post;
import com.example.crud_app.model.Tag;
import com.example.crud_app.model.User;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    public static final String TITLE = "title";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String AUTHOR = "author";
    private PostRepository postRepository;
    private TagRepository tagRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private UserRepository userRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    private Pageable getPageableOfPosts(String sortBy, int offset, int pageSize){
        if (sortBy != null) {
            if (sortBy.equalsIgnoreCase(TITLE)) {
                sortBy = "title";
            } else if (sortBy.equalsIgnoreCase(PUBLISHED_DATE)) {
                sortBy = "publishedDateTime";
                return PageRequest.of(offset, pageSize, Sort.by(sortBy).descending());
            } else if (sortBy.equalsIgnoreCase(AUTHOR)) {
                sortBy = "author";
            }
            return PageRequest.of(offset, pageSize, Sort.by(sortBy));
        } else{
            return PageRequest.of(offset, pageSize);
        }
    }
    @Override
    public Model getDraftsPosts(Model model, HttpServletRequest request) {
        int start, limit = 2;
        int pageableIndex = 0;
        if (request.getParameter("start") != null) {
            start = Math.max(Integer.parseInt(request.getParameter("start")), 0);
            limit = Integer.parseInt(request.getParameter("limit"));
            pageableIndex = start / limit;
            model.addAttribute("start", (start + limit));
            model.addAttribute("limit", limit);
        } else {
            model.addAttribute("start", (limit));
            model.addAttribute("limit", limit);
        }

        Pageable postPageable = PageRequest.of(pageableIndex, limit);
        List<Post> drafts = postRepository.findPosts(false, postPageable);
        model.addAttribute("drafts", drafts);
        return model;
    }

    public Tag findOrCreateTag(String rowTag) {
        Tag tag = tagRepository.findByName(rowTag);
        if (tag == null) {
            Tag newTag = new Tag();
            newTag.setName(rowTag);
            newTag.setCreatedAt(LocalDateTime.now());
            newTag.setUpdatedAt(LocalDateTime.now());
            return newTag;
        }
        return tag;
    }

    @Override
    public String index() {
        return null;
    }

    @Override
    public Model getPostPage(Model model) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        model.addAttribute("date", date);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedUser", auth.getName());
        return model;
    }

    @Override
    public void savePostPageData(HttpServletRequest request) {
        Post post = new Post();
        post.setTitle(request.getParameter("title").trim());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(auth.getName());
        post.setContent(request.getParameter("content").trim());

        if (request.getParameter("excerpt") == null || request.getParameter("excerpt").length() == 0) {
            String excerpt = request.getParameter("content");
            if (excerpt.length() > 250) {
                excerpt = excerpt.substring(0, 250);
                excerpt += "...";
            }
            post.setExcerpt(excerpt);
        } else {
            post.setExcerpt(request.getParameter("excerpt"));
        }
        String draft = request.getParameter("draft");
        if (draft != null && draft.equals("on")) {
            post.setPublishedDate(LocalDateTime.now());
            post.setPublished(true);
        } else {
            post.setPublished(false);
            post.setPublishedDate(null);
        }
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        List<Tag> tags = new ArrayList<>();
        String[] rowTags = request.getParameter("tags").split("[\\s,]+");
        for (String rowTag : rowTags) {
            rowTag = rowTag.replace("#", "").trim();
            if (rowTag.length() > 0) {
                Tag tag = findOrCreateTag(rowTag);
                tags.add(tag);
            }
        }
        post.setTags(tags);
        postRepository.save(post);
    }

    private String getResultsQuery(String search, String[] tagNames, String[] authorNames){
        StringBuilder result;
        if (search == null && tagNames == null && authorNames == null){
            result = new StringBuilder("Result Showing for All Posts");
            return result.toString();
        } else{
            result = new StringBuilder("Result Showing for : ");
        }
        result.append((search != null) ? search : "").append(" ");
        if (tagNames != null)
            for (String tag: tagNames) result.append(tag).append(" ");
        if (authorNames != null)
            for (String author: authorNames) result.append(author).append(" ");

        return result.toString();
    }
    private String getPaginationUrl(String search, String[] tagNames, String[] authorNames, String sortBy){
        StringBuilder url = new StringBuilder();
        if (search == null && tagNames == null && authorNames == null && sortBy == null){
            url = new StringBuilder();
            return url.toString();
        }
        url.append((search != null) ? ("&search="+search) : "");
        if (tagNames != null)
            for (String tag: tagNames) url.append("&").append("tagName=").append(tag);
        if (authorNames != null)
            for (String author: authorNames) url.append("&").append("authorName=").append(author);
        if (sortBy != null)
            url.append("&sortBy=").append(sortBy);
        return url.toString();
    }
    @Override
    public Model readAllPostsWithParams(Model model, HttpServletRequest request, String[] tagNames,
                                        String[] authorNames, String search, String sortBy, String start,
                                        String limit) {
        model.addAttribute("resultQuery", getResultsQuery(search, tagNames, authorNames));
        int offset = Integer.parseInt(start == null ? "0" : start);
        int pageSize = Integer.parseInt(limit == null ? "10" : limit);
        int paginationIndex = offset / pageSize;

        Pageable pageable = getPageableOfPosts(sortBy, paginationIndex, pageSize);
        List<Post> posts;

        model.addAttribute("start", offset + pageSize);
        model.addAttribute("limit", pageSize);

        model.addAttribute("startUrl", "/posts/read?start="+ (offset + pageSize) + "&limit=" + pageSize + getPaginationUrl(search, tagNames, authorNames, sortBy));
        model.addAttribute("prevUrl", "/posts/read?start="+ (offset - pageSize) + "&limit=" + pageSize + getPaginationUrl(search, tagNames, authorNames, sortBy));
        List<String> authors = postRepository.getAuthors(true);
        model.addAttribute("authors", authors);

        List<Tag> tags = (List<Tag>) tagRepository.findAll();
        model.addAttribute("tags", tags);
        try {
            PostSpecification postSpecification = new PostSpecification();
            Specification<Post> filteredSpecification = postSpecification.findByFilters(search, authorNames, tagNames);
            posts = postRepository.findAll(filteredSpecification, pageable).getContent();
            if (posts == null || posts.isEmpty()){
                throw new DataNotFoundException();
            }
        } catch (DataNotFoundException exception){
            throw new DataNotFoundException();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new OtherException();
        }
        model.addAttribute("posts", posts);
        return model;
    }

    @Override
    public Model getFilteredResultsPage(HttpServletRequest request, Model model) {
        if (request.getParameter("filter").equalsIgnoreCase("authors")) {
            List<String> authors = postRepository.getAuthors(true);
            model.addAttribute("authors", authors);
        } else {
            List<Tag> tags = (List<Tag>) tagRepository.findAll();
            model.addAttribute("tags", tags);
        }
        return model;
    }

    @Override
    public Model readPostDetail(int id, Model model) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post;
        if (optionalPost.isEmpty()) {
            throw new DataNotFoundException();
//            return "commons/test";
        }
        post = optionalPost.get();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );
        optionalUser.ifPresent(user -> model.addAttribute("authenticatedUser", user));
        model.addAttribute("post", post);
        return model;
    }

    @Override
    public Model getPostEditPage(int postId, Model model) {
        Optional<Post> optional = postRepository.findById(postId);
        if (optional.isPresent()) {
            Post post = optional.get();
            model.addAttribute("post", post);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );
            if (optionalUser.isEmpty()) {
                throw new InsufficientAccessException();
            }
            User user = optionalUser.get();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                return model;
            } else if (user.getRole().equals(Constants.ADMIN)) {
                return model;
            } else{
                throw new InsufficientAccessException();
            }
        } else {
            throw new DataNotFoundException();
        }
    }

    @Override
    public void updatePostPageData(int postId, HttpServletRequest request) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(request.getParameter("title").trim());
            post.setAuthor(request.getParameter("author").trim());
            post.setContent(request.getParameter("content").trim());
            post.setExcerpt(request.getParameter("excerpt").trim());
            String draft = request.getParameter("draft");
            if (draft != null && draft.equals("on")) {
                post.setPublishedDate(LocalDateTime.now());
                post.setPublished(true);
            } else {
                post.setPublished(false);
                post.setPublishedDate(null);
            }
            post.setUpdatedAt(LocalDateTime.now());
            List<Tag> tags = new ArrayList<>();
            String[] rowTags = request.getParameter("tags").split("[\\s,]+");
            for (String rowTag : rowTags) {
                rowTag = rowTag.replace("#", "");
                if (rowTag.length() > 0) {
                    Tag tag = findOrCreateTag(rowTag);
                    tags.add(tag);
                }
            }
            post.setTags(tags);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );
            if (optionalUser.isEmpty()) {
                throw new InsufficientAccessException();
            }

            User user = optionalUser.get();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                tagRepository.saveAll(tags);
                postRepository.save(post);
            } else if (user.getRole().equals(Constants.ADMIN)) {
                postRepository.delete(post);
            } else{
                throw new InsufficientAccessException();
            }
        }
    }

    @Override
    public void deletePost(int postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> optionalUser = userRepository.findByUsername( auth.getName() );
            if (optionalUser.isEmpty()) {
                throw new InsufficientAccessException();
            }

            User user = optionalUser.get();
            if (user.getRole().equals(Constants.AUTHOR) &&
                    user.getUsername().equals(post.getAuthor())) {
                postRepository.delete(post);
            } else if (user.getRole().equals(Constants.ADMIN)) {
                postRepository.delete(post);
            } else{
                throw new InsufficientAccessException();
            }
        }else {
            throw new DataNotFoundException();
        }
    }
}

