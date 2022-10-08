package com.example.crud_app.jpa;

import com.example.crud_app.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    public List<Post> findAllByOrderByAuthorAsc();

    public List<Post> findAllByOrderByPublishedDateTimeDesc();

    @Query("select post.author from Post post where post.isPublished = ?1")
    List<String> getAuthors(boolean isPublished);

    @Query("select post from Post post where post.author = ?1 and post.isPublished = ?2")
    List<Post> getAllByAuthor(String authorName, Boolean isPublished);

    @Query("select post from Post post where post.isPublished=true and post.author in :authors")
    List<Post> getByAuthors(@Param("authors") String[] authors, Pageable pageable);
    @Query("select post from Post post join post.tags tag where post.isPublished = true and tag.name in :tags")
    List<Post> getByTags(@Param("tags") String[] tags, Pageable pageable);

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
