package com.outerlyy.outerlyyuserservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outerlyy.outerlyyuserservice.models.generated.OuterlyyUser;
import com.outerlyy.outerlyyuserservice.services.UserService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/user")
public class UserResources {
    
    private final UserService userService;
    
    public UserResources(UserService userService){
        this.userService = userService;
    }

    /**
     * Retrieve a user profile information
     *
     * @param username the username to retrieve
     * @return an Outerlyy User
     */
    @GetMapping("/{username}")
    public Mono<OuterlyyUser> get(@PathVariable String username) {
        return userService.getUser(username);
    }

    /**
     * Create a new user
     *
     * @param outerlyyUser the object containing the user information to be created
     * @return the new created outerlyyUser
     */
    @PostMapping
    public Mono<OuterlyyUser> post(@RequestBody OuterlyyUser outerlyyUser) {
        return userService.createUser(outerlyyUser);
    }
}
