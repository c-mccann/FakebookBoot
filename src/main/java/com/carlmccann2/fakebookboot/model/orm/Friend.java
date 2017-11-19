package com.carlmccann2.fakebookboot.model.orm;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "friends")
public class Friend implements Serializable{
    private Integer friendshipId;
    private User userOne;
    private User userTwo;
    private Timestamp friendsSince;
    private String relationship;

    public Friend() {
    }

    public Friend(User userOne, User userTwo, Timestamp friendsSince, String relationship) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.friendsSince = friendsSince;
        this.relationship = relationship;
    }

    public Friend(Integer friendshipId, User userOne, User userTwo, Timestamp friendsSince, String relationship) {
        this.friendshipId = friendshipId;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.friendsSince = friendsSince;
        this.relationship = relationship;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friendship_id", unique = true, nullable = false)
    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    @ManyToOne
    @JoinColumn(name = "user_one_id", referencedColumnName = "user_id", nullable = false)
    public User getUserOne() {
        return userOne;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    @ManyToOne
    @JoinColumn(name = "user_two_id", referencedColumnName = "user_id", nullable = false)
    public User getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }

    @Column(name = "friends_since", nullable = false)
    public Timestamp getFriendsSince() {
        return friendsSince;
    }

    public void setFriendsSince(Timestamp friendsSince) {
        this.friendsSince = friendsSince;
    }

    @Column(name = "relationship")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendshipId=" + friendshipId +
                ", userOne=" + userOne +
                ", userTwo=" + userTwo +
                ", friendsSince=" + friendsSince +
                ", relationship='" + relationship + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        if (!friendshipId.equals(friend.friendshipId)) return false;
        if (!userOne.equals(friend.userOne)) return false;
        if (!userTwo.equals(friend.userTwo)) return false;
        if (friendsSince != null ? !friendsSince.equals(friend.friendsSince) : friend.friendsSince != null)
            return false;
        return relationship != null ? relationship.equals(friend.relationship) : friend.relationship == null;
    }

    @Override
    public int hashCode() {
        int result = friendshipId.hashCode();
        result = 31 * result + userOne.hashCode();
        result = 31 * result + userTwo.hashCode();
        result = 31 * result + (friendsSince != null ? friendsSince.hashCode() : 0);
        result = 31 * result + (relationship != null ? relationship.hashCode() : 0);
        return result;
    }
}
