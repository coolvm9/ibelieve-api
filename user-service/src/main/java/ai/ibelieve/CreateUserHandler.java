package ai.ibelieve;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ibelieve.db.DependencyFactory;
import ibelieve.entities.IBelieveData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for requests to Lambda function.
 */
public class CreateUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static final int STATUS_CODE_NO_CONTENT = 204;
    static final int STATUS_CODE_CREATED = 201;
    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<IBelieveData> ibelieveTableSchema;
    private Map<String, String> headers = new HashMap<>();
    private Region REGION = Region.US_WEST_1;

    public CreateUserHandler() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        tableName = DependencyFactory.iBelieveTableName();
        ibelieveTableSchema = TableSchema.fromBean(IBelieveData.class);
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
    }



    public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent input,  Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        String output = "EMPTY";
        IBelieveData data;
        try {
            if (input.getHttpMethod().equals("POST")){
                data = gson.fromJson(input.getBody(), IBelieveData.class);
                String uuid = UUID.randomUUID().toString();
                if (data != null) {
                    data.setMetadata("PROFILE#"+uuid);
                    data.setCreatedDate(Instant.now());
                    data.setLastUpdatedDate(Instant.now());
                    dbClient.table(tableName, TableSchema.fromBean(IBelieveData.class)).putItem(data);
                }
                System.out.println("POST");
                System.out.println("TABLE NAME"  + tableName);
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
