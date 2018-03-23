package org.dsmhack.service;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GuidGeneratorTest {

    @Test
    public void generateGuidReturnsUniqueUUID() throws Exception {
        GuidGenerator guidGenerator = new GuidGenerator();
        String firstUUID = guidGenerator.generate();
        String secondUUID = guidGenerator.generate();
        assertNotEquals(firstUUID, secondUUID);
    }
}