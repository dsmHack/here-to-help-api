package org.dsmhack.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CodeGenerator {

  public String generateUuid() {
    return UUID.randomUUID().toString();
  }

  public String generateLoginToken() {
    int uniqueSixDigitCode = 100000 + new Random().nextInt(900000);
    return String.valueOf(uniqueSixDigitCode);
  }
}