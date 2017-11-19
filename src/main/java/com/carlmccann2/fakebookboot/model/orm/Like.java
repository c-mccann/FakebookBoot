package com.carlmccann2.fakebookboot.model.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "likes")
public class Like implements Serializable {

    private Integer likeId;
    private User user;
    private Post post;
    private Comment comment;

    public Like() {
    }

    public Like(User user, Post post, Comment comment) {
        this.likeId = likeId;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id", unique = true, nullable = false)
    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    @JsonIgnore
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    @JsonIgnore
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {

        return  (post == null) ? ("Like{" + "likeId=" + likeId + ", userId=" + user.getId() + ", commentId=" + comment.getCommentId() + '}')
                :
                ("Like{" + "likeId=" + likeId + ", userId=" + user.getId() + ", postId=" + post.getPostId() + '}');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        if (!likeId.equals(like.likeId)) return false;
        if (!user.equals(like.user)) return false;
        if (post != null ? !post.equals(like.post) : like.post != null) return false;
        return comment != null ? comment.equals(like.comment) : like.comment == null;
    }

    @Override
    public int hashCode() {
        int result = likeId.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
