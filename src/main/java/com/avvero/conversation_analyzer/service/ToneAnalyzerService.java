package com.avvero.conversation_analyzer.service;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Avvero
 */
@Service
public class ToneAnalyzerService {

    @Value("${conversation_analyzer.tone_analyzer.username}")
    private String username;
    @Value("${conversation_analyzer.tone_analyzer.password}")
    private String password;
    private ToneAnalyzer service;

    @PostConstruct
    public void init() {
        service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
        service.setUsernameAndPassword(username, password);

    }

    public ToneAnalysis analyze(String text) {
        return service.getTone(text, null).execute();
    }

}
