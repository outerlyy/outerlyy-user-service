package com.outerlyy.outerlyyuserservice.services;

import com.outerlyy.outerlyyuserservice.dao.UserDao;
import com.outerlyy.outerlyyuserservice.formatters.UserFormatter;
import com.outerlyy.outerlyyuserservice.models.generated.OuterlyyUser;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;


@Service
public class UserService {
    
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public Mono<OuterlyyUser> getUser(String outerlyyUserId) {
        return Mono.fromCompletionStage(userDao.getUser(outerlyyUserId))
                .map(GetItemResponse::item)
                .map(UserFormatter::fromMap);
    }

    public Mono<OuterlyyUser> createUser(OuterlyyUser user) {
        return Mono.fromCompletionStage(userDao.createUser(user))
                .map(PutItemResponse::attributes)
                .map(attributeValueMap -> user);
    }
}
