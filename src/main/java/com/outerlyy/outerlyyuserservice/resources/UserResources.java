package com.outerlyy.outerlyyuserservice.resources;

import com.outerlyy.outerlyyuserservice.models.generated.OuterlyyUser;
import com.outerlyy.outerlyyuserservice.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/user")
public class UserResources {
    
    private final UserService userService;
    
    public UserResources(UserService userService){
        this.userService = userService;
    }

    /**
     * Retrieve a user profile information "api/v1/user/{username}"
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public Mono<OuterlyyUser> get(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping
    public Mono<OuterlyyUser> post(@RequestBody OuterlyyUser outerlyyUser) {
        return userService.createUser(outerlyyUser);
    }
    
}
