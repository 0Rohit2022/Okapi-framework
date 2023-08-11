package com.task.Controller;

import net.sf.okapi.common.Event;
import net.sf.okapi.common.EventType;
import net.sf.okapi.common.filters.IFilter;
import net.sf.okapi.common.resource.RawDocument;
import net.sf.okapi.filters.plaintext.PlainTextFilter;
import net.sf.okapi.common.LocaleId;
import net.sf.okapi.common.resource.ITextUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//-------------Task-3------------
//Using the Okapi framework (https://okapiframework.org/) develop a REST API
//using Spring Boot to find the word count of a given sentence.


@RestController
@RequestMapping("/api")
public class okapiController {

    @PostMapping("/wordcount")
    public ResponseEntity<?> countWords(@RequestBody String text) {
        IFilter filter = new PlainTextFilter();
        try {
            filter.open(new RawDocument(text, LocaleId.ENGLISH));

            int wordCount = 0;

            while (filter.hasNext()) {
                Event event = filter.next();
                if (event.getEventType() == EventType.TEXT_UNIT) {
                    ITextUnit tu = event.getTextUnit();
                    if (tu != null) {
                        String sentence = tu.getSource().getFirstContent().toText();
                        String[] words = sentence.split("\\s+");
                        wordCount += words.length;
                    }
                }
            }

            return ResponseEntity.ok(wordCount);
        } finally {
            if (filter != null) filter.close();
        }
    }
}
