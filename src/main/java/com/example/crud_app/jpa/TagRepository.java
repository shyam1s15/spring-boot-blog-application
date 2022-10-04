package com.example.crud_app.jpa;

import com.example.crud_app.model.Post;
import com.example.crud_app.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    public Tag findByName(String name);
    @Query("select post from Tag tag join tag.posts post where post.isPublished = :isPublished and  tag.name in :tagNames")
    List<Post> findByTags(@Param("tagNames") List<String> tagNames, Boolean isPublished);

    @Query("select post from Tag tag join tag.posts post where (:tagName is null or tag.name = :tagName) and post.isPublished = :isPublished")
    List<Post> getByTagName(@Param("tagName") String tagName, Boolean isPublished);

}
