package com.github.bpark.examples.rules;

import org.eclipse.rdf4j.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class FromFact implements Fact {

    private List<Detail> details = new ArrayList<>();

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public List<Detail> listMyDetails(int param) {
        return details;
    }

    public List<Detail> listByResource(Resource resource) {
        return details;
    }

    public Boolean containsResource(Resource resource) {
        return true;
    }
}
