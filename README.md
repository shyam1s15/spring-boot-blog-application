# Blog application in spring boot

## Explanation
Creating a small application where one can post something 
then one can comment or add tags to the posts.

## tables: 
- Users: id, name, email. password
- Posts: id, title, excerpt, content, author, published_at, is_published, created_at, updated_at
- Tags: id, name, created_at, updated_at
- post_tags: post_id, tag_id, created_at, updated_at
- comments: id, name, email, comment, post_id, created_at, updated_at

## APIs
Apis & it's authorization can be readed from top to bottom, while implementation
is bottom to top.

| /                 | Admin, Author, User |
|-------------------|---------------------|
| posts/            | Admin, Author, User |
| posts/read        | Admin, Author, User |
| posts/create/post | Admin, Author       |
| posts/filter      | Admin, Author, User |
| posts/update      | Admin, Author       |
| posts/delete      | Admin, Author       |
 | posts/drafts      | Admin, Author       |
| drafts/read       | Admin, Author       |
| comment/create    | Admin, Author, User |
 | comment/delete    | Admin, Author       |
| comment/update    | Admin, Author       |
| /error            | Admin, Author, User |


