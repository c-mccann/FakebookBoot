package com.carlmccann2.fakebookboot.model.orm;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post implements Serializable {
    private Integer postId;
    private User user;
    private User userPostedTo;
    private String postText;
    private Photo photo;
    private Timestamp postCreated;

    private List<Comment> comments;
    private List<Like> likes;

    public Post() {
    }

    public Post(User user, User userPostedTo, String postText, Photo photo, Timestamp postCreated, List<Comment> comments, List<Like> likes) {
        this.user = user;
        this.userPostedTo = userPostedTo;
        this.postText = postText;
        this.photo = photo;
        this.postCreated = postCreated;
        this.comments = comments;
        this.likes = likes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", nullable = false, unique = true)
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

//    @JsonIgnore
    @ManyToOne//(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @JsonIgnore
    @ManyToOne//(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_posted_to_id", referencedColumnName = "user_id", nullable = false)
    public User getUserPostedTo() {
        return userPostedTo;
    }
    public void setUserPostedTo(User userPostedToEntity) {
        this.userPostedTo = userPostedToEntity;
    }


    @Column(name = "post_text")
    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    @ManyToOne
    @JoinColumn(name = "photo_id", referencedColumnName = "photo_id")
    public Photo getPhoto() {
        return photo;
    }


    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @OneToMany()
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @Column(name = "post_created", nullable = false)
    public Timestamp getPostCreated() {
        return postCreated;
    }

    public void setPostCreated(Timestamp postCreated) {
        this.postCreated = postCreated;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", user=" + user +
                ", userPostedTo=" + userPostedTo +
                ", postText='" + postText + '\'' +
                ", photo=" + photo +
                ", postCreated=" + postCreated +
                ", comments=" + comments +
                ", likes=" + likes +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!postId.equals(post.postId)) return false;
        if (!user.equals(post.user)) return false;
        if (!userPostedTo.equals(post.userPostedTo)) return false;
        if (postText != null ? !postText.equals(post.postText) : post.postText != null) return false;
        if (photo != null ? !photo.equals(post.photo) : post.photo != null) return false;
        return postCreated.equals(post.postCreated);
    }

    @Override
    public int hashCode() {
        int result = postId.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + userPostedTo.hashCode();
        result = 31 * result + (postText != null ? postText.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + postCreated.hashCode();
        return result;
    }
}
