package com.github.bpark.examples;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.spin.SpinSail;

public class SpinDemo {

    public static void main(String[] args) throws Exception {
        SpinSail spinSail = new SpinSail();
        spinSail.setBaseSail(
                new ForwardChainingRDFSInferencer(
                        //new DedupingInferencer(new MemoryStore())
                        new MemoryStore()
                )
        );
        Repository rep = new SailRepository(spinSail);
        rep.initialize();

        rep.getConnection().add(SpinDemo.class.getResourceAsStream("/spin/example.ttl"), "", RDFFormat.TURTLE);
        rep.getConnection().add(SpinDemo.class.getResourceAsStream("/spin/spin.ttl"), "", RDFFormat.TURTLE);

        ValueFactory vf = SimpleValueFactory.getInstance();

        String ex = "http://example.org/";

        IRI iri = vf.createIRI(ex, "Lucy");
        IRI parentOf = vf.createIRI(ex, "childOf");

        RepositoryResult<Statement> statements = rep.getConnection().getStatements(iri, parentOf, null);

        System.out.println(statements.next().getObject().stringValue());
    }
}
