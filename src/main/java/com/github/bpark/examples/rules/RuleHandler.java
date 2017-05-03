package com.github.bpark.examples.rules;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

public class RuleHandler {

    private KieSession ksession;

    @PostConstruct
    public void init() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        ksession = kc.newKieSession("DemoKS");
        ksession.setGlobal( "list", new ArrayList<>() );
        ksession.setGlobal( "vf", SimpleValueFactory.getInstance());
    }

    public void addFact(Fact fact) {
        ksession.insert(fact);
    }

    public void deleteFact(Fact fact) {
        ksession.delete(ksession.getFactHandle(fact));
    }

    public void updateFact(Fact fact) {
        ksession.update(ksession.getFactHandle(fact), fact);
    }

    public void fireAllRules() {
        ksession.fireAllRules();
    }

    public void close() {
        ksession.dispose();
    }
}
