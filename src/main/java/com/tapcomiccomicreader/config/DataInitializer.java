package com.tapcomiccomicreader.config;

import com.tapcomiccomicreader.service.ComicService;
import com.tapcomiccomicreader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataInitializer {
    private final UserService userService;
    private final ComicService comicService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(UserService userService, ComicService comicService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.comicService = comicService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void enableExtensions() {
        jdbcTemplate.execute("CREATE EXTENSION IF NOT EXISTS pg_trgm;");
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
