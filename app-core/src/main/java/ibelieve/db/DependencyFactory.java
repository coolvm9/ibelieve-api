
package ibelieve.db;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DependencyFactory {

    public static final String ENV_VARIABLE_USER_TABLE = "USER_TABLE";
    public static final String ENV_VARIABLE_IBELIEVE_DATA_TABLE = "IBELIEVE_DATA_TABLE";
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
                        .build())
                .build();



    }

    public static String iBelieveTableName() {
        return System.getenv(ENV_VARIABLE_IBELIEVE_DATA_TABLE);
    }
    public static String userTableName() {
        return System.getenv(ENV_VARIABLE_USER_TABLE);
    }
    public static String styleQuizTableName() {
        return System.getenv(ENV_VARIABLE_STYLE_QUIZ_TABLE);
    }

}
