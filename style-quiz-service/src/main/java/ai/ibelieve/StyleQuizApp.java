package ai.ibelieve;


import ai.ibelieve.entities.StyleQuiz;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import ai.ibelieve.db.DependencyFactory;
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
public class StyleQuizApp implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    ai.ibelieve.entities.StyleQuiz styleQuiz;
    static final int STATUS_CODE_NO_CONTENT = 204;
    static final int STATUS_CODE_CREATED = 201;
    private final DynamoDbEnhancedClient dbClient;
    private final String styleQuizTableName;
    private final TableSchema<ai.ibelieve.entities.StyleQuiz> styleQuizTableSchema;
    private Map<String, String> headers = new HashMap<>();

    public StyleQuizApp() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        styleQuizTableName = DependencyFactory.styleQuizTableName();
        styleQuizTableSchema = TableSchema.fromBean(ai.ibelieve.entities.StyleQuiz.class);
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
    }


    private Region REGION = Region.US_WEST_1;
    public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent input,  Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        String output = "EMPTY";
        StyleQuiz styleQuiz;
        try {
            if (input.getHttpMethod().equals("GET")){
                System.out.println("GET");
                Map<String, String> inputParams = input.getQueryStringParameters();
                for (Map.Entry<String,String> entry : inputParams.entrySet())
                    System.out.println("Key = " + entry.getKey() +
                            ", Value = " + entry.getValue());
                String userId = inputParams.get("userId");
                String styleQuizId = inputParams.get("styleQuizId");
                DynamoDbTable<StyleQuiz> styleQuizDynamoDbTable = dbClient.table(styleQuizTableName, TableSchema.fromBean(StyleQuiz.class));
                Key key = Key.builder()
                        .partitionValue(userId)
                        .sortValue(styleQuizId)
                        .build();
                output = gson.toJson(styleQuizDynamoDbTable.getItem(key));
            }
            if (input.getHttpMethod().equals("POST")){
                styleQuiz = gson.fromJson(input.getBody(), StyleQuiz.class);
                if (styleQuiz != null) {
                    dbClient.table(styleQuizTableName, TableSchema.fromBean(StyleQuiz.class)).putItem(styleQuiz);
                }
                System.out.println("POST");
                System.out.println("TABLE NAME"  + styleQuizTableName);
                output = input.getBody() + "styleQ from Post";
            }
            if (input.getHttpMethod().equals("DELETE")){
                System.out.println("DELETE");
                output ="styleQ from DELETE";
            }
            if (input.getHttpMethod().equals("PUT")){
                System.out.println("PUT");
                output ="styleQ from PUT";
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
