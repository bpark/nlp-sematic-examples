package com.github.bpark.examples.rules;

public class RuleDemo {

    public static void main(String[] args) {
        RuleHandler ruleHandler = new RuleHandler();
        ruleHandler.init();

        FromFact fromFact = new FromFact();

        Detail detail = new Detail("test");
        fromFact.getDetails().add(detail);

        ruleHandler.addFact(fromFact);

        ruleHandler.fireAllRules();
    }
}
