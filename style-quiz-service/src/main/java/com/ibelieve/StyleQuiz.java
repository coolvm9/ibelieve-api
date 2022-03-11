package com.ibelieve;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ibelieve.db.DependencyFactory;
import com.ibelieve.entities.User;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.regions.Region;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class StyleQuiz implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    User user;
    static final int STATUS_CODE_NO_CONTENT = 204;
    static final int STATUS_CODE_CREATED = 201;
    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<User> userTableSchema;
    private Map<String, String> headers = new HashMap<>();

    public StyleQuiz() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        tableName = DependencyFactory.tableName();
        userTableSchema = TableSchema.fromBean(User.class);
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
    }


    private Region REGION = Region.US_WEST_1;
    public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent input,  Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        String output = null;
        try {
            if (input.getHttpMethod().equals(SdkHttpMethod.GET)){
                System.out.println("GET");
            }
            if (input.getHttpMethod().equals(SdkHttpMethod.POST)){
                System.out.println("POST");
            }
            if (input.getHttpMethod().equals(SdkHttpMethod.DELETE)){
                System.out.println("DELETE");
            }
            if (input.getHttpMethod().equals(SdkHttpMethod.PUT)){
                System.out.println("PUT");
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
