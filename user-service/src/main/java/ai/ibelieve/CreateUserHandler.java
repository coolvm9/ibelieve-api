package ai.ibelieve;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ibelieve.Constants;
import ibelieve.db.DependencyFactory;
import ibelieve.db.IBelieveDao;
import ibelieve.entities.IBelieveData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

/**
 * Handler for requests to Lambda function.
 */
public class CreateUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<IBelieveData> ibelieveTableSchema;
    private Map<String, String> headers = new HashMap<>();
    private Region REGION = Region.US_WEST_1;

    @Inject
    IBelieveDao believeDao;

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
//                String uuid = UUID.randomUUID().toString();
                if (data != null) {
                    data.setMetadata("PROFILE#"+ data.getUserId());
                    data.setCreatedDate(Instant.now());
                    data.setLastUpdatedDate(Instant.now());
                    dbClient.table(tableName, TableSchema.fromBean(IBelieveData.class)).putItem(data);

                }
                headers.put("Location", "/v1/user/"+data.getUserId());
                response.setHeaders(headers);
                response.withStatusCode(Constants.STATUS_CODE_CREATED);
                output = gson.toJson(data) ;
            }
            if (input.getHttpMethod().equals("DELETE")){
                System.out.println("DELETE");
                output ="User from DELETE";
            }
            if (input.getHttpMethod().equals("PUT")){
                System.out.println("PUT");
                output ="USer from PUT";
            }
            int statusCode = Constants.STATUS_CODE_NO_CONTENT;

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
