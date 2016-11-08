package com.avvero.conversation_analyzer.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Avvero
 */
@Component
public class MessageRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Document document) {
        mongoTemplate.insert(document, "toneAnalysis");
    }

}
