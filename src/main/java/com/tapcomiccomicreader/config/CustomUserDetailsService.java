package com.tapcomiccomicreader.config;

import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Value("${spring.security.user.name}")
    private String adminUsername;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Value("${spring.security.user.roles}")
    private String adminRole;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUsername)) {
            String[] roles = adminRole.split(",");

            return org.springframework.security.core.userdetails.User.builder()
                    .username(adminUsername)
                    .password(adminPassword)
                    .roles(roles)
                    .build();
        }

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("could not find user with name " + username));

        return new CustomUserDetails(user);
    }
}
