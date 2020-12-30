package be.dog.d.steven.readinglist.condition;

import org.junit.jupiter.api.Test;
import org.springframework.core.type.StandardAnnotationMetadata;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JdbcTemplateConditionTest {
    @Test
    public void testMatches() {
        StandardAnnotationMetadata annotatedTypeMetadata = new StandardAnnotationMetadata(Object.class);
        assertFalse((new JdbcTemplateCondition()).matches(null, annotatedTypeMetadata));
    }
}

