package com.avvero.conversation_analyzer.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Avvero
 */
@Service
public class TranslatorService {

    @Value("${conversation_analyzer.translate.api_key}")
    private String apiKey;
    private Translate translate;

    @PostConstruct
    public void init(){
        translate = TranslateOptions.builder().apiKey(apiKey).build().service();
    }

    public String translate(String s){
        return translate.translate(s, TranslateOption.sourceLanguage("ru"), TranslateOption.targetLanguage("en"))
                .translatedText();
    }

}
