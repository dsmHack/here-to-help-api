package org.dsmhack.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class CodeGenerator {

    public String generateUUID(){
        return UUID.randomUUID().toString();
    }
}