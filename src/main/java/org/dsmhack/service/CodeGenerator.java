package org.dsmhack.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class CodeGenerator {

    public UUID generateUUID(){
        return UUID.randomUUID();
    }

    public String generateLoginToken(){
        int uniqueSixDigitCode = 100000 + new Random().nextInt(900000);
        return String.valueOf(uniqueSixDigitCode) ;
    }
}