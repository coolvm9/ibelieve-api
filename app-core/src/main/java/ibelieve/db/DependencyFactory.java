
package ibelieve.db;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

public class DependencyFactory {

    public static final String ENV_VARIABLE_USER_TABLE = "USER_TABLE";
    public static final String ENV_VARIABLE_IBELIEVE_DATA_TABLE = "IBELIEVE_DATA_TABLE";
    public static final String ENV_VARIABLE_STYLE_QUIZ_TABLE = "STYLE_QUIZ_TABLE";

    private DependencyFactory() {}

    /**
     * @return an instance of LambdaClient
     */
    public static DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        // Local Testing
       /* DynamoDbClientBuilder builder = DynamoDbClient.builder();
        builder.httpClient(ApacheHttpClient.builder().build());
        builder.endpointOverride(URI.create("http://dynamodb-local:8000"));
        builder.region(Region.US_WEST_1);
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(builder.build())
                .build();*/

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDbClient.builder()
                        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                        .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
                        .build())
                .build();


    }



    public static String iBelieveTableName() {
        return System.getenv(ENV_VARIABLE_IBELIEVE_DATA_TABLE);
    }
    /*public static String userTableName() {
        return System.getenv(ENV_VARIABLE_USER_TABLE);
    }
    public static String styleQuizTableName() {
        return System.getenv(ENV_VARIABLE_STYLE_QUIZ_TABLE);
    }
*/
}
