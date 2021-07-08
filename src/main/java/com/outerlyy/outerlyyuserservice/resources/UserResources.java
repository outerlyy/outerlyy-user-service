package com.outerlyy.outerlyyuserservice.resources;

import java.util.concurrent.CompletableFuture;

import com.outerlyy.outerlyyuserservice.models.generated.EchoRequest;
import com.outerlyy.outerlyyuserservice.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    // @GetMapping(path = "/{username}", produces = "application/json")
    // @RequestMapping("/{username}")
    @GetMapping("/{username}")
    public CompletableFuture<String> get(@PathVariable String username) {
        return userService.get(username);
    }

    // @GetMapping(path = "/echo", produces = "application/x-protobuf")
    // @RequestMapping("/echo")
    @GetMapping("/echo")
    public CompletableFuture<EchoRequest> echo() {
        return userService.echoCompleteFuture();
    }
    
}
