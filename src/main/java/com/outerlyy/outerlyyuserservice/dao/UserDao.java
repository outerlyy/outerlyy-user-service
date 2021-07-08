package com.outerlyy.outerlyyuserservice.dao;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Service
public class UserDao {

    public static final String TABLE_NAME = "appUsers";
    public static final String ID_COLUMN = "id";
    public static final String BODY_COLUMN = "body";

    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    // private final String tableName;

    public UserDao(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
        // this.tableName = tableName;
    }

    public CompletableFuture<String> echoCompleteFuture(String username) {
        return  CompletableFuture.supplyAsync(() -> String.format("Hello %s",username));
    }
    
}
