package com.github.bpark.examples;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

public class MemoryInitializer {

    public static void main(String[] args) {
        Repository repo = new SailRepository(new MemoryStore());
        repo.initialize();

        try (RepositoryConnection connection = repo.getConnection()) {

            ValueFactory vf = SimpleValueFactory.getInstance();

            String ex = "http://lilie.ai/";

            IRI lilie = vf.createIRI(ex, "Lilie");

            connection.add(lilie, FOAF.NAME, vf.createLiteral("Lilie"));
            connection.add(lilie, FOAF.NICK, vf.createLiteral("Lil"));

        }
    }
}
