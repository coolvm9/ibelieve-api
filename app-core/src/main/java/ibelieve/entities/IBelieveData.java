package ibelieve.entities;


import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbUpdateBehavior;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@DynamoDbBean
public class IBelieveData {

    private String metadata;
    private String userId;
    private LocalDateTime createdDate;
    private LocalDateTime  lastUpdatedDate;

    private  String firstName;
    private String lastName;
    private String emailId;
    private boolean subscribe;


    private String styleId;
    private List<String> occasion;
    private List<String> dressingStyle;
    private List<String> weather;
    private List<String> weatherCondition;
    private int height;
    private int bust;
    private int waist;
    private int hip;
    private int inseam;
    private String bodyType;
    private String underTone;
    private String skinTone;
    private List<String> brands;

    private List<String> topStyle;
    private List<String> neckStyle;
    private List<String> sleeveStyle;
    private List<String> pantStyle;
    private List<String> skirtLength;
    private List<String> dressType;
    private List<String> shoeType;
    private List<String> colorPalatte;
    private List<String> patternStyle;
    private List<String> fabricAllergies;
    private List<String> socialMedia;


    @DynamoDbSortKey
    public String getMetadata() {
        return metadata;
    }
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @DynamoDbPartitionKey
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }



    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

}
