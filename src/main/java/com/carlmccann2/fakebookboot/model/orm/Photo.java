package com.carlmccann2.fakebookboot.model.orm;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "photos")
public class Photo implements Serializable{
    private Integer photoId;
    private User user;
    private Timestamp photoAdded;
    private byte[] photo;
    private Timestamp profilePic;


    public Photo() {
    }

    public Photo(Integer photoId, User user, Timestamp photoAdded, byte[] photo, Timestamp profilePic) {
        this.photoId = photoId;
        this.user = user;
        this.photoAdded = photoAdded;
        this.photo = photo;
        this.profilePic = profilePic;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="photo_id", unique = true, nullable = false)
    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer id) {
        this.photoId = id;
    }

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name="photo_added", nullable = false)
    public Timestamp getPhotoAdded() {
        return photoAdded;
    }

    public void setPhotoAdded(Timestamp photoAdded) {
        this.photoAdded = photoAdded;
    }

    @Column(name="photo", nullable = false)
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Column(name = "profile_pic")
    public Timestamp getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Timestamp profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", user=" + user +
                ", photoAdded=" + photoAdded +
                ", photo=" + Arrays.toString(photo) +
                ", profilePic=" + profilePic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo1 = (Photo) o;

        if (!photoId.equals(photo1.photoId)) return false;
        if (!user.equals(photo1.user)) return false;
        if (!photoAdded.equals(photo1.photoAdded)) return false;
        if (!Arrays.equals(photo, photo1.photo)) return false;
        return profilePic != null ? profilePic.equals(photo1.profilePic) : photo1.profilePic == null;
    }

    @Override
    public int hashCode() {
        int result = photoId.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + photoAdded.hashCode();
        result = 31 * result + Arrays.hashCode(photo);
        result = 31 * result + (profilePic != null ? profilePic.hashCode() : 0);
        return result;
    }
}
