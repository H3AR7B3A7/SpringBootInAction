package be.dog.d.steven.readinglist.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JdbcTemplateCondition implements Condition {

    // When we annotate bean creation with @Conditional(JdbcTemplateCondition.class)
    // the bean will only get created when this matcher returns true.
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        try {
            conditionContext.getClassLoader().loadClass(
                    "org.springframework.jdbc.core.JdbcTemplate"
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
