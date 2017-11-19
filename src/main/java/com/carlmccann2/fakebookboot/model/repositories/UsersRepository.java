package com.carlmccann2.fakebookboot.model.repositories;

import com.carlmccann2.fakebookboot.model.orm.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {

    User getUserByEmail(String email);

    User getUserById(int id);

    @Query(value = "SELECT * FROM users WHERE CONCAT(first_name, ' ', last_name) RLIKE ?1", nativeQuery = true)
    List<User> getUsersByNameNearestMatch(String fullName);

}
