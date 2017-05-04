package com.github.bpark.examples;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.inferencer.fc.CustomGraphQueryInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

public class CustomInferenceDemo {

    public static void main(String[] args) throws Exception {
        String pre = "PREFIX ex: <http://example.org/>\n";
        String rule = pre + "CONSTRUCT { ?p ex:relatesTo ex:Cryptography } WHERE " +
                "{ { ex:Bob ?p ex:Alice } UNION { ex:Alice ?p ex:Bob } }";
        String match = pre + "CONSTRUCT { ?p ex:relatesTo ex:Cryptography } " +
                "WHERE { ?p ex:relatesTo ex:Cryptography }";
        Repository repo = new SailRepository(new CustomGraphQueryInferencer(
                new MemoryStore(), QueryLanguage.SPARQL, rule, match));

        repo.initialize();

        repo.getConnection().add(SpinDemo.class.getResourceAsStream("/custom/custom.ttl"), "", RDFFormat.TURTLE);

        ValueFactory vf = SimpleValueFactory.getInstance();

        String ex = "http://example.org/";

        IRI iri = vf.createIRI(ex, "exchangesKeysWith");
        IRI parentOf = vf.createIRI(ex, "relatesTo");

        RepositoryResult<Statement> statements = repo.getConnection().getStatements(iri, parentOf, null);

        System.out.println(statements.next().getObject().stringValue());
    }
}
