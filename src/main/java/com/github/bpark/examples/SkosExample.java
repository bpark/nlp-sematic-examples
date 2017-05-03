package com.github.bpark.examples;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import java.io.InputStream;

public class SkosExample {

    public static void main(String[] args) throws Exception {
        String filename = "basic-synonyms.ttl";

        // read the file 'example-data-artists.ttl' as an InputStream.
        InputStream input = SkosExample.class.getResourceAsStream("/" + filename);

        // Rio also accepts a java.io.Reader as input for the parser.
        Model model = Rio.parse(input, "", RDFFormat.TURTLE);

        for (Statement statement: model) {
            System.out.println(statement);
        }
    }
}
