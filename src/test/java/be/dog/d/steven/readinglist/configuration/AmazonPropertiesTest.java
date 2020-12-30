package be.dog.d.steven.readinglist.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonPropertiesTest {
    @Test
    public void testSetAssociateId() {
        AmazonProperties amazonProperties = new AmazonProperties();
        amazonProperties.setAssociateId("42");
        assertEquals("42", amazonProperties.getAssociateId());
    }
}

