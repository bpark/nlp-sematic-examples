package com.github.bpark.examples;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;

public class NameFinder {

    public static void main(String[] args) throws Exception {
        InputStream modelInToken = null;
        InputStream modelIn = null;
        try {
            //1. convert sentence into tokens
            modelInToken = NameFinder.class.getResourceAsStream("/nlp/en-token.bin");
            TokenizerModel modelToken = new TokenizerModel(modelInToken);
            Tokenizer tokenizer = new TokenizerME(modelToken);
            String tokens[] = tokenizer.tokenize("My name is Kurt Sparber");

            //2. find names
            modelIn = NameFinder.class.getResourceAsStream("/nlp/en-ner-person.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);

            Span nameSpans[] = nameFinder.find(tokens);

            //find probabilities for names
            double[] spanProbs = nameFinder.probs(nameSpans);

            //3. print names
            for (int i = 0; i < nameSpans.length; i++) {
                Span nameSpan = nameSpans[i];
                System.out.println("Span: " + nameSpan.toString());
                int start = nameSpan.getStart();
                int end = nameSpan.getEnd() - 1;
                if (start == end) {
                    System.out.println("Covered text is: " + tokens[start]);
                } else {
                    System.out.println("Covered text is: " + tokens[start] + " " + tokens[end]);
                }
                System.out.println("Probability is: " + spanProbs[i]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try { if (modelInToken != null) modelInToken.close(); } catch (IOException e){e.printStackTrace();}
            try { if (modelIn != null) modelIn.close(); } catch (IOException e){e.printStackTrace();}
        }
    }
}
