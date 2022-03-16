package ibelieve.db;

import ibelieve.entities.IBelieveData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.Instant;

public class IBelieveDao {

    private final String tableName;
    private final DynamoDbEnhancedClient dynamoDb;
    private final int pageSize;


    public IBelieveDao(final DynamoDbEnhancedClient dynamoDb, final String tableName,
                    final int pageSize) {
        this.dynamoDb = dynamoDb;
        this.tableName = tableName;
        this.pageSize = pageSize;
    }

    public IBelieveData createUser(final IBelieveData user) {
        if (user == null) {
            throw new IllegalArgumentException("User was null");
        }
        try {

            if(user.getUserId()!=null) {
                user.setMetadata("PROFILE#" + user.getUserId());
                user.setCreatedDate(Instant.now());
                user.setLastUpdatedDate(Instant.now());
                dynamoDb.table(tableName, TableSchema.fromBean(IBelieveData.class)).putItem(user);
            }

        } catch (ResourceNotFoundException e) {
            throw e;
        }
        return user;
    }

}
