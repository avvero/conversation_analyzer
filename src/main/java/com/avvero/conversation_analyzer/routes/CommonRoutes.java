package com.avvero.conversation_analyzer.routes;

import com.avvero.conversation_analyzer.dto.data.Message;
import com.avvero.conversation_analyzer.service.ToneAnalyzerService;
import com.avvero.conversation_analyzer.service.TranslatorService;
import com.avvero.conversation_analyzer.utils.CommonUtils;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Avvero
 */
@Component
public class CommonRoutes extends RouteBuilder {

    @Value("${conversation_analyzer.queue_name_conversation}")
    private String conversationQueueName;
    @Autowired
    TranslatorService translator;
    @Autowired
    ToneAnalyzerService toneAnalyzerService;

    @Override
    public void configure() throws Exception {
        from("activemq:" + conversationQueueName)
                .setHeader("Content-Type", constant("application/json; charset=utf-8"))
                .unmarshal().json(JsonLibrary.Jackson, Message.class)
                .to("bean:commonRoutes?method=handle")
                .to("bean:messageRepository?method=save");
    }

    public Document handle(Message message) {
        Document document = Document.parse(CommonUtils.dataToJson(message));
        String text = message.getText();
        //Цитирование, нужная правая часть
        String quotes = "&lt;&lt;&lt;";
        if (text.contains(quotes)) {
            text = text.substring(text.indexOf(quotes) + quotes.length() + 1, text.length()-1);
        }
        String textEng = translator.translate(text);
        document.put("textEng", textEng);
        if (message.getText() != null && !message.getText().trim().equals(textEng.trim())) {
            ToneAnalysis toneAnalysis = toneAnalyzerService.analyze(textEng);
            document.put("analysis", Document.parse(CommonUtils.dataToJson(toneAnalysis)));
        }
        return document;
    }

}
