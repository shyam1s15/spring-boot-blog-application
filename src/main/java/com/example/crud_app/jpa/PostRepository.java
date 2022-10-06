package com.example.crud_app.jpa;

import com.example.crud_app.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    //    public List<Post> findAllByAuthor();
//    public List<Post> findAllByAuthorAsc();
    public List<Post> findAllByOrderByAuthorAsc();

    public List<Post> findAllByOrderByPublishedDateTimeDesc();

    @Query("select post.author from Post post where post.isPublished = ?1")
    List<String> getAuthors(boolean isPublished);

    @Query("select post from Post post where post.author = ?1 and post.isPublished = ?2")
    List<Post> getAllByAuthor(String authorName, Boolean isPublished);

    @Query("select post from Post post join post.tags tag " +
            "where (post.isPublished = true) and " +
            "upper(tag.name) like concat('%', upper(?1), '%') " +
            "or upper(post.title) like concat('%', upper(?1), '%') " +
            "or upper(post.content) like concat('%', upper(?1), '%') " +
            "or upper(post.author) like concat('%', upper(?1), '%') group by  post.id")
    List<Post> getBySearch(String search, Pageable pageable);

    @Query("select post from Post post where post.isPublished=true and post.author in :authors")
    List<Post> getByAuthors(@Param("authors") String[] authors, Pageable pageable);
    @Query("select post from Post post join post.tags tag where post.isPublished = true and tag.name in :tags")
    List<Post> getByTags(@Param("tags") String[] tags, Pageable pageable);

    @Query("select post from Post post join post.tags tag where post.isPublished=true and post.author in :authors and tag.name in :tags")
    List<Post> getByAuthorsAndTags(@Param("authors") String[] authors, @Param("tags") String[] tags, Pageable pageable);

    @Query("select post from Post post " +
            "where post.isPublished = true and " +
            "post.author in :authors and " +
            "( upper(post.title) like concat('%', upper(:search), '%') " +
                "or upper(post.content) like concat('%', upper(:search), '%') " +
                "or upper(post.author) like concat('%', upper(:search), '%') " +
            ") group by  post.id")
    List<Post> getByAuthorsAndSearch(@Param("authors") String[] authors, @Param("search") String search, Pageable pageable);
    @Query("select post from Post post join post.tags tag " +
            "where post.isPublished = true and " +
            "tag.name in :tags and " +
            "( upper(post.title) like concat('%', upper(:search), '%') " +
            "or upper(post.content) like concat('%', upper(:search), '%') " +
            "or upper(post.author) like concat('%', upper(:search), '%') " +
            ") group by  post.id")
    List<Post> getByTagsAndSearch(@Param("tags") String[] tags, @Param("search") String search, Pageable pageable);

    @Query("select post from Post post join post.tags tag " +
            "where post.isPublished = true and " +
            "post.isPublished = :isPublished and " +
            "post.author in :authorNames and " +
            "tag.name in :tagNames and " +
            "( upper(post.title) like concat('%', upper(:search), '%') " +
                "or upper(post.content) like concat('%', upper(:search), '%') " +
                "or upper(post.author) like concat('%', upper(:search), '%') " +
            ") group by  post.id")
    List<Post> getPostsByAllParams(@Param("isPublished") boolean isPublished,
                                   @Param("search") String search,
                                   @Param("authorNames") String[] authorNames,
                                   @Param("tagNames") String[] tagNames, Pageable pageable);

    @Query("select post from Post post where post.isPublished=:isPublished")
    List<Post> findPosts(Boolean isPublished, Pageable pageable);

    @Query("select post from Post post where post.isPublished = :isPublished and post.author in :authorNames")
    List<Post> findByAuthors(@Param("authorNames") List<String> authorNames, Boolean isPublished);

    @Query("select post from Post post where post.isPublished = false and (:author is null or post.author = :author)")
    List<Post> findDraftsOfAuthor(@Param("author") String author);
//    @Query("select post from Post post join post.tags tags " +
//            "where post.isPublished = true and (:search is empty or post.title = :search " +
//            "or post.author = :search or post.content = :search or tags.name = :search) " +
//            "and (:authorNames is null or post.author in :authorNames)" +
//            "and (:tagNames is null or tags.name in :tagNames) group by post.id")
//    List<Post> findAllByParams( @Param("search") String search,
//                               @Param("authorNames") String[] authorNames,
//                               @Param("tagNames") String[] tagNames, Pageable pageable);


}
