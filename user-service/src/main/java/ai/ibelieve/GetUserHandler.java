package ai.ibelieve;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ibelieve.db.DependencyFactory;
import ibelieve.db.IBelieveDao;
import ibelieve.entities.IBelieveData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;

import java.util.HashMap;
import java.util.Map;


public class GetUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDbEnhancedClient dbClient;
    private final String userTableName;
    private final TableSchema<IBelieveData> userTableSchema;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    IBelieveDao believeDao;
    private Map<String, String> headers = new HashMap<>();
    private Region REGION = Region.US_WEST_1;


    public GetUserHandler() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        userTableName = DependencyFactory.iBelieveTableName();
        userTableSchema = TableSchema.fromBean(IBelieveData.class);
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        believeDao = IBelieveDao.getInstance(dbClient, userTableName, 10);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        IBelieveData user;
        String userId;
        Map<String, String> inputParams;
        LambdaLogger logger = context.getLogger();
        try {
            inputParams = input.getPathParameters();
            for (Map.Entry<String, String> entry : inputParams.entrySet())
                logger.log("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            userId = inputParams.get("userId");
            user = believeDao.getUser(userId);
            if (user != null) {
                return response
                        .withStatusCode(200)
                        .withBody(gson.toJson(user));
            } else
                return response
                        .withStatusCode(404)
                        .withBody("User with ID: '" + userId + "' not found.");
        } catch (Exception e) {
            logger.log("Error " + e.getMessage());
            return response
                    .withBody("Error" + e.getMessage())
                    .withStatusCode(500);
        }
    }
}
