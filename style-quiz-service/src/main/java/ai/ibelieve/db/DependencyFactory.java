
package ai.ibelieve.db;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

public class DependencyFactory {

    public static final String ENV_VARIABLE_USER_TABLE = "USER_TABLE";
    public static final String ENV_VARIABLE_STYLE_QUIZ_TABLE = "STYLE_QUIZ_TABLE";

    private DependencyFactory() {}

    /**
     * @return an instance of LambdaClient
     */
    public static DynamoDbEnhancedClient dynamoDbEnhancedClient() {
      return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDbClient.builder()
                        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                        .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
                        .httpClientBuilder(UrlConnectionHttpClient.builder())
                        .build())
                .build();

       /* // Lcoal Host
        *//*return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDbClient.builder()
                        .credentialsProvider(StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("v3409", "iphjx")))
                        .region(Region.US_WEST_1)
                        .httpClientBuilder(UrlConnectionHttpClient.builder())
                        .endpointOverride(URI.create("http://localhost:8000"))
                        .build())
                .build();*//*

        DynamoDbClient ddb = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .region(Region.US_WEST_1)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("dummy-key", "dummy-secret")))
                .build();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        return enhancedClient;*/


    }

    public static String userTableName() {
        return System.getenv(ENV_VARIABLE_USER_TABLE);
    }
    public static String styleQuizTableName() {
        return System.getenv(ENV_VARIABLE_STYLE_QUIZ_TABLE);
    }

}
