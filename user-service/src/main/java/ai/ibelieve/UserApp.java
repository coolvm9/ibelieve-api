package ai.ibelieve;


import ai.ibelieve.db.DependencyFactory;
import ai.ibelieve.entities.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class UserApp implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    ai.ibelieve.entities.User user;
    static final int STATUS_CODE_NO_CONTENT = 204;
    static final int STATUS_CODE_CREATED = 201;
    private final DynamoDbEnhancedClient dbClient;
    private final String userTableName;
    private final TableSchema<ai.ibelieve.entities.User> userTableSchema;
    private Map<String, String> headers = new HashMap<>();

    public UserApp() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        userTableName = DependencyFactory.userTableName();
        userTableSchema = TableSchema.fromBean(ai.ibelieve.entities.User.class);
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
    }


    private Region REGION = Region.US_WEST_1;
    public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent input,  Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        String output = "EMPTY";
        User user;
        try {
            if (input.getHttpMethod().equals("GET")){
                System.out.println("GET");
                Map<String, String> inputParams = input.getQueryStringParameters();
                for (Map.Entry<String,String> entry : inputParams.entrySet())
                    System.out.println("Key = " + entry.getKey() +
                            ", Value = " + entry.getValue());
                String userId = inputParams.get("userId");
                String metadata = inputParams.get("metadata");

                DynamoDbTable<User> styleQuizDynamoDbTable = dbClient.table(userTableName, TableSchema.fromBean(User.class));
                Key key = Key.builder()
                        .partitionValue(userId)
                        .build();
                output = gson.toJson(styleQuizDynamoDbTable.getItem(key));
            }
            if (input.getHttpMethod().equals("POST")){
                user = gson.fromJson(input.getBody(), User.class);
                if (user != null) {
                    dbClient.table(userTableName, TableSchema.fromBean(User.class)).putItem(user);
                }
                System.out.println("POST");
                System.out.println("TABLE NAME"  + userTableName);
                output = input.getBody() ;
            }
            if (input.getHttpMethod().equals("DELETE")){
                System.out.println("DELETE");
                output ="User from DELETE";
            }
            if (input.getHttpMethod().equals("PUT")){
                System.out.println("PUT");
                output ="USer from PUT";
            }
            int statusCode = STATUS_CODE_NO_CONTENT;

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            logger.log("Error : " + e.getMessage());
            return response
                    .withBody("{\n" +
                            "  \"status\": \"error\",\n" +
                            "  \"data\": null, /* " + e.getMessage() + " */\n" +
                            "  \"message\": \"Error xyz has occurred\"\n" +
                            "}")
                    .withStatusCode(500);
        }
    }
}
