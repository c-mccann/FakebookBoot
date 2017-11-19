package com.carlmccann2.fakebookboot.model.repositories;

import com.carlmccann2.fakebookboot.model.orm.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepository extends JpaRepository<Photo, Integer> {

}
