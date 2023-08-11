package com.task.Controller;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;



// ----------Task-3----------
//Develop a REST API to extract the terminology in a given Plain text file using
//Okapi framework.





@RestController
public class okapiFramework3 {

    @PostMapping("/extractTerms")
    public String extractTerms(@RequestParam("file") MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);

        StringBuilder extractedTerms = new StringBuilder();

        Analyzer analyzer = new StandardAnalyzer();
        TokenStream stream = analyzer.tokenStream(null, new StringReader(content));
        CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);

        try {
            stream.reset();
            while (stream.incrementToken()) {
                extractedTerms.append(termAtt.toString()).append("\n");
            }
            stream.end();
        } finally {
            stream.close();
            analyzer.close();
        }

        return extractedTerms.toString();
    }
}
