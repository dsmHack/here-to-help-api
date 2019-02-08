package org.dsmhack.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CodeGeneratorTest {

  @Test
  public void generateGuidReturnsUniqueUUID() throws Exception {
    CodeGenerator codeGenerator = new CodeGenerator();
    String firstUUID = codeGenerator.generateUuid();
    String secondUUID = codeGenerator.generateUuid();
    assertNotEquals(firstUUID, secondUUID);
  }

  @Test
  public void generateLoginTokenGeneratesUnique6DigitLoginToken() throws Exception {
    CodeGenerator codeGenerator = new CodeGenerator();
    assertEquals(6, codeGenerator.generateLoginToken().length());
    assertNotEquals(codeGenerator.generateLoginToken(), codeGenerator.generateLoginToken());
  }
}