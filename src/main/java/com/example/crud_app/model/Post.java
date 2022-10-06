package com.example.crud_app.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    private String title;
    private String excerpt;
    @Lob
    private String content;
    private String author;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST
    })
    private List<Tag> tags;

    @Column(name = "published_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime publishedDateTime;
    private boolean isPublished;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postId")
    private List<Comment> comments;

    public boolean isPublished() {
        return isPublished;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDateTime == null ? "null" : publishedDateTime.toString();
//        return publishedDate.toString().length() == 0 ? "null" : publishedDate.toString();
    }
    public void setPublishedDate(LocalDateTime publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public boolean setPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public String getCreatedAt() {
        return createdAt == null ? "null" : createdAt.toString();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt == null ? "null" : updatedAt.toString();
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }
    public String getTagsToString() {
        String tagsToString = "";
        if (tags == null){
            return "";
        }
        for (Tag tag : tags){
            tagsToString += ("#" + tag.getName() + " ");
        }
        return tagsToString;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                ", publishedDate=" + publishedDateTime +
                ", isPublished=" + isPublished +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
//                ", comments=" + comments +
                '}';
    }
}
