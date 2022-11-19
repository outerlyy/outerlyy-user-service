package com.outerlyy.outerlyyuserservice.dao;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import com.outerlyy.outerlyyuserservice.formatters.UserFormatter;
import com.outerlyy.outerlyyuserservice.models.generated.OuterlyyUser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.BillingMode;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.utils.ImmutableMap;

@Service
public class UserDao {

    public static final String PARTITION_KEY_NAME = "username";

    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    private final String userTable;

    public UserDao(DynamoDbAsyncClient dynamoDbAsyncClient, @Value("${application.dynamodb.userTable}") String userTable) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
        this.userTable = userTable;
    }

    public CompletableFuture<PutItemResponse> createUser(OuterlyyUser user) {
        Map<String, AttributeValue> userMap = UserFormatter.toMap(user);
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(userTable)
                .item(userMap)
                .build();
        
        return dynamoDbAsyncClient.putItem(putItemRequest);
    }

    public CompletableFuture<GetItemResponse> getUser(String outerlyyUserId) {
        Map<String, AttributeValue> userKey = ImmutableMap.of(PARTITION_KEY_NAME, 
                                        AttributeValue.builder().s(outerlyyUserId).build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(userTable)
                .key(userKey)
                .build();

        return dynamoDbAsyncClient.getItem(getItemRequest);
    }

    //Creating table on startup if not exists
    @PostConstruct
    public void createTableIfNeeded() throws ExecutionException, InterruptedException {
        // ListTablesRequest request = ListTablesRequest
        //         .builder()
        //         .exclusiveStartTableName(userTable)
        //         .build();
        CompletableFuture<ListTablesResponse> listTableResponse = dynamoDbAsyncClient.listTables();
    
        CompletableFuture<CreateTableResponse> createTableRequest = listTableResponse
                .thenCompose(response -> {
                    boolean tableExist = response
                            .tableNames()
                            .contains(userTable);

                    if (!tableExist) {
                        return createTable();
                    } else {
                        return CompletableFuture.completedFuture(null);
                    }
                });
    
        //Wait in synchronous manner for table creation
        createTableRequest.get();
    }

    private CompletableFuture<CreateTableResponse> createTable() {

        KeySchemaElement keySchemaElement = KeySchemaElement
                .builder()
                .attributeName(PARTITION_KEY_NAME)
                .keyType(KeyType.HASH)
                .build();
        
        AttributeDefinition attributeDefinition = AttributeDefinition
                .builder()
                .attributeName(PARTITION_KEY_NAME)
                .attributeType(ScalarAttributeType.S)
                .build();

        CreateTableRequest request = CreateTableRequest.builder()
                .tableName(userTable)
                .keySchema(keySchemaElement)
                .attributeDefinitions(attributeDefinition)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build();

        return dynamoDbAsyncClient.createTable(request);

    }
}
