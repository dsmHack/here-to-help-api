package org.dsmhack.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuidGenerator {

    public UUID generate(){
        return UUID.randomUUID();
    }
}