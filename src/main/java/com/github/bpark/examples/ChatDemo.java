package com.github.bpark.examples;

import com.github.bpark.examples.rules.RuleHandler;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.vocabulary.FOAF;

import java.util.ArrayList;
import java.util.List;

public class ChatDemo {

    private Model model;

    private List<String> chatLog = new ArrayList<>();

    private RuleHandler ruleHandler = new RuleHandler();

    public ChatDemo() {

        ruleHandler.init();

        createModel();
        createIdentity();
    }

    private void createModel() {

        // Create a new, empty Model object.
        model = new TreeModel();
        model.setNamespace("ex", "http;//example.org");



        /*
        model = ModelFactory.createDefaultModel();

        model.setNsPrefix("foaf", FOAF.NS);
        model.setNsPrefix("", "http;//example.org");
        */
    }

    private void createIdentity() {

        ValueFactory vf = SimpleValueFactory.getInstance();

        String ex = "http://example.org/";

        // Create IRIs for the resources we want to add.
        IRI lilie = vf.createIRI(ex, "Lilie");

        model.add(lilie, FOAF.NAME, vf.createLiteral("Lilie"));
        model.add(lilie, FOAF.MBOX, vf.createIRI("mailto:alice@example.org"));
        model.add(lilie, FOAF.KNOWS, vf.createIRI(ex, "Bob"));

        IRI test = vf.createIRI(ex, "testid");

        model.add(test, FOAF.NAME, vf.createLiteral("Test"));
        model.add(test, FOAF.MBOX, vf.createIRI("mailto:alice@example.org"));
        model.add(test, FOAF.KNOWS, vf.createIRI(ex, "Lilie"));

    }

    private void eval() {
        String command = chatLog.get(chatLog.size() - 1);
        if (command.startsWith("init#")) {
            String id = command.split("#")[1];
            String ex = "http://example.org/";
            ValueFactory vf = SimpleValueFactory.getInstance();
            boolean contains = model.contains(vf.createIRI(ex, id), null, null);
            if (contains) {
                Model statements = model.filter(vf.createIRI(ex, id), FOAF.NAME, null);
                chatLog.add("Hello " + statements.iterator().next().getObject().stringValue());
                say();
            } else {
                chatLog.add("Hello, what's your name?");
                say();
            }
        }
    }

    public void say(String sentence) {
        chatLog.add(sentence);
        eval();
    }

    public String waitForUserInput() {
        //Scanner scanIn = new Scanner(System.in);
        //String inputString = scanIn.nextLine();
        //scanIn.close();

        String inputString = "Test";

        return inputString;
    }

    private void say() {
        String s = chatLog.get(chatLog.size() - 1);
        System.out.println(s);
    }

    public static void main(String[] args) {
        ChatDemo chatDemo = new ChatDemo();
        chatDemo.say("init#testid");
        chatDemo.say(chatDemo.waitForUserInput());
    }
}
