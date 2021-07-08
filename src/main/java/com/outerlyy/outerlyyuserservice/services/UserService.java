package com.outerlyy.outerlyyuserservice.services;

import java.util.concurrent.CompletableFuture;

import com.outerlyy.outerlyyuserservice.dao.UserDao;
import com.outerlyy.outerlyyuserservice.models.generated.EchoRequest;

import org.springframework.stereotype.Service;


@Service
public class UserService {
    
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public CompletableFuture<String> get(String username) {
        return userDao.echoCompleteFuture(username);
    }

    public CompletableFuture<EchoRequest> echoCompleteFuture() {
        return CompletableFuture.supplyAsync(() -> EchoRequest.newBuilder().setName("Echo ...").build());
    }
}
