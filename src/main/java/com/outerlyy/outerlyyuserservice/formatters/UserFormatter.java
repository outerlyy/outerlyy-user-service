package com.outerlyy.outerlyyuserservice.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.outerlyy.outerlyyuserservice.models.generated.OuterlyyUser;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.utils.ImmutableMap;

public class UserFormatter {
    
    public static List<OuterlyyUser> fromList(List<Map<String, AttributeValue>> items) {
        return items.stream()
                .map(UserFormatter::fromMap)
                .collect(Collectors.toList());
    }

    public static OuterlyyUser fromMap(Map<String, AttributeValue> attributeValueMap) {
        return OuterlyyUser.newBuilder()
            .setUsername(attributeValueMap.get("username").s())
            .setPassword(attributeValueMap.get("password").s())
            .setFirstName(attributeValueMap.get("firstName").s())
            .setLastName(attributeValueMap.get("lastName").s())
            .build();
    }

    public static Map<String, AttributeValue> toMap(OuterlyyUser user) {
        return ImmutableMap.of(
                "username", AttributeValue.builder().s(user.getUsername()).build(),
                "password", AttributeValue.builder().s(user.getPassword()).build(),
                "firstName", AttributeValue.builder().s(user.getFirstName()).build(),
                "lastName", AttributeValue.builder().s(user.getLastName()).build()
        );
    }
}
