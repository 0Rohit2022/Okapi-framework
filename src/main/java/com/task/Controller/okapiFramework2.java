package com.task.Controller;

import net.sf.okapi.common.Event;
import net.sf.okapi.common.EventType;
import net.sf.okapi.common.filters.IFilter;
import net.sf.okapi.common.resource.RawDocument;
import net.sf.okapi.common.resource.ITextUnit;
import net.sf.okapi.common.LocaleId;
import net.sf.okapi.filters.plaintext.PlainTextFilter;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// ----------Task-2----------
//Using the Okapi framework, develop a REST API using Spring Boot to extract
//        the content of a markdown file (.md) file.


@RestController
@RequestMapping("/api")
public class okapiFramework2 {
    @PostMapping("/extract")
    public String extractContent(@RequestParam("file") MultipartFile file) throws IOException {
        IFilter filter = new PlainTextFilter();
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);

        StringBuilder extractedContent = new StringBuilder();
        try {
            filter.open(new RawDocument(content, LocaleId.ENGLISH));

            while (filter.hasNext()) {
                Event event = filter.next();

                // Check if the event is a TextUnit event
                if(event.getEventType() == EventType.TEXT_UNIT) {
                    ITextUnit tu = event.getTextUnit();
                    if (tu != null) {
                        extractedContent.append(tu.getSource().getFirstContent().toText());
                    }
                }
            }

        } finally {
            if (filter != null) filter.close();
        }

        return extractedContent.toString();
    }






}

