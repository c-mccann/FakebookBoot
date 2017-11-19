package com.carlmccann2.fakebookboot;


import com.carlmccann2.fakebookboot.model.services.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.carlmccann2.fakebookboot.model.services")
public class ServiceConfig {

    @Bean
    public CommentsService commentsService(){
        return new CommentsServiceImp();
    }
    @Bean
    public FriendsService FriendsService(){
        return new FriendsServiceImp();
    }
    @Bean
    public LikesService likesService(){
        return new LikesServiceImp();
    }
    @Bean
    public PhotosService photosService(){
        return new PhotosServiceImp();
    }
    @Bean
    public PostsService postsService(){
        return new PostsServiceImp();
    }

    @Bean
    public UsersService usersService(){
        return new UsersServiceImp();
    }

}
