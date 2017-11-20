package com.carlmccann2.fakebookboot.model.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    private Integer commentId;
    private Post post;
    private User user;
    private String commentText;
    private Timestamp commentCreated;

    private Set<Like> likes;

    public Comment() {
    }

    public Comment(Post post, User user, String commentText, Timestamp commentCreated, Set<Like> likes) {
        this.post = post;
        this.user = user;
        this.commentText = commentText;
        this.commentCreated = commentCreated;
        this.likes = likes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", unique = true, nullable = false)
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    @JsonIgnore
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany
    @JoinColumn(name="comment_id", referencedColumnName="comment_id")
    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @Column(name = "comment_text", nullable = false)
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }


    @Column(name = "comment_created", nullable = false)
    public Timestamp getCommentCreated() {
        return commentCreated;
    }

    public void setCommentCreated(Timestamp commentCreated) {
        this.commentCreated = commentCreated;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId=" + post.getPostId() +
                ", userId=" + user.getId() +
                ", commentText='" + commentText + '\'' +
                ", commentCreated=" + commentCreated +
                ", likes=" + likes.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!commentId.equals(comment.commentId)) return false;
        if (!post.equals(comment.post)) return false;
        if (!user.equals(comment.user)) return false;
        if (!commentText.equals(comment.commentText)) return false;
        return commentCreated.equals(comment.commentCreated);
    }

    @Override
    public int hashCode() {
        int result = commentId.hashCode();
        result = 31 * result + post.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + commentText.hashCode();
        result = 31 * result + commentCreated.hashCode();
        return result;
    }


}
