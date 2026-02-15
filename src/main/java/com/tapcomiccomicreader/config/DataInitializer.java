package com.tapcomiccomicreader.config;

import com.tapcomiccomicreader.service.ComicService;
import com.tapcomiccomicreader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private UserService userService;
    private ComicService comicService;

    @Autowired
    public DataInitializer(UserService userService, ComicService comicService) {
        this.userService = userService;
        this.comicService = comicService;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
//            userService.save(new User("xaceTx", "212512"));
//            userService.save(new User("GuShueArmy", "armo"));
//
//            comicService.save(new Comic("Reverend Insanity", "Gu Yue Fang Yuan"));
        };
    }
}
