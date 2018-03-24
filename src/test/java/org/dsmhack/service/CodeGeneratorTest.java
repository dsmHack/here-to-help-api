package org.dsmhack.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class CodeGeneratorTest {

    @Test
    public void generateGuidReturnsUniqueUUID() throws Exception {
        CodeGenerator codeGenerator = new CodeGenerator();
        String firstUUID = codeGenerator.generateUUID();
        String secondUUID = codeGenerator.generateUUID();
        assertNotEquals(firstUUID, secondUUID);
    }
}