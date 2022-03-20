package ibelieve.util;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;

public class CustomConvertor implements AttributeConverter<Instant> {

    @Override
    public AttributeValue transformFrom(Instant input) {
        return null;
    }

    @Override
    public Instant transformTo(AttributeValue input) {
        return null;
    }

    @Override
    public EnhancedType<Instant> type() {
        return null;
    }

    @Override
    public AttributeValueType attributeValueType() {
        return null;
    }
}
