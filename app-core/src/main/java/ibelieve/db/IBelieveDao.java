package ibelieve.db;

import ibelieve.entities.IBelieveData;
import ibelieve.exception.EntityDoesNotExistException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class IBelieveDao {

    private final String tableName;
    private final DynamoDbEnhancedClient dynamoDb;
    private final int pageSize;


    public IBelieveDao( DynamoDbEnhancedClient dynamoDb,  String tableName,
                     int pageSize) {
        this.dynamoDb = dynamoDb;
        this.tableName = tableName;
        this.pageSize = pageSize;
    }

    public IBelieveData createUser( IBelieveData user) {
        if (user == null || user.getUserId()==null) {
            throw new IllegalArgumentException("User to create is null or UserId is null");
        }
        try {
            if(user.getUserId()!=null) {
                user.setMetadata("PROFILE#" + user.getUserId());
                user.setCreatedDate(LocalDateTime.now());
                user.setLastUpdatedDate(LocalDateTime.now());
                dynamoDb.table(tableName, TableSchema.fromBean(IBelieveData.class)).putItem(user);
            }
        } catch (ResourceNotFoundException e) {
            throw new EntityDoesNotExistException("Table " + tableName + " does not exist");
        }
        return user;
    }

}
