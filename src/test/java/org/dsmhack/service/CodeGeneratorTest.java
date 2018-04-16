package org.dsmhack.service;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class CodeGeneratorTest {

    @Test
    public void generateGuidReturnsUniqueUUID() throws Exception {
        CodeGenerator codeGenerator = new CodeGenerator();
        UUID firstUUID = codeGenerator.generateUUID();
        UUID secondUUID = codeGenerator.generateUUID();
        assertNotEquals(firstUUID, secondUUID);
    }

    @Test
    public void generateLoginTokenGeneratesUnique6DigitLoginToken() throws Exception {
        CodeGenerator codeGenerator = new CodeGenerator();
        assertEquals(6, codeGenerator.generateLoginToken().length());
        assertNotEquals(codeGenerator.generateLoginToken(), codeGenerator.generateLoginToken());
    }
}