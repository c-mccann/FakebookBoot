package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.repositories.PhotosRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photos")
public class PhotosController {

    private Log log = LogFactory.getLog(PhotosController.class);

    @Autowired
    private PhotosRepository photosRepository;
}
