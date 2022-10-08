package com.example.crud_app.jpa;

import com.example.crud_app.model.Post;
import com.example.crud_app.model.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class PostSpecification implements Specification<Post> {

    @Override
    public Specification<Post> and(Specification<Post> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Post> or(Specification<Post> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public static Specification<Post> bySearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null) {
                return criteriaBuilder.conjunction();
            }
            Predicate titlePredicate = criteriaBuilder.like(root.<String>get("title"), "%" + search + "%");
            Predicate contentPredicate = criteriaBuilder.like(root.<String>get("content"), "%" + search + "%");
            Predicate authorPredicate = criteriaBuilder.like(root.<String>get("author"), "%" + search + "%");

            Join<Post, Tag> join = root.join("tags", JoinType.INNER);
            Predicate tagPredicate = criteriaBuilder.like(join.get("name"), "%" + search + "%");

            return criteriaBuilder.or(titlePredicate, contentPredicate,
                    authorPredicate, tagPredicate);
        };
    }
    public static Specification<Post> byPublished() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isPublished"),true);
    }


    public static Specification<Post> byAuthorNames(String[] authorNames) {
        return (root, query, criteriaBuilder) -> {
            if (authorNames == null || authorNames.length == 0) {
                return criteriaBuilder.conjunction();
            }
            return root.<String>get("author").in(authorNames);
        };
    }

    public static Specification<Post> byTagNames(String[] tagNames) {
        return (root, query, criteriaBuilder) -> {
            if (tagNames == null || tagNames.length > 0) {
                return criteriaBuilder.conjunction();
            }
            Join<Post, Tag> postTagsTable = root.join("tags", JoinType.INNER);
            return postTagsTable.<String>get("name").in(tagNames);
        };
    }

    public Specification<Post> findByFilters(String search, String[] authorNames, String[] tagNames) {
        return byPublished()
                .and(bySearch(search))
                .and(byAuthorNames(authorNames))
                .and(byTagNames(tagNames));
    }
}
