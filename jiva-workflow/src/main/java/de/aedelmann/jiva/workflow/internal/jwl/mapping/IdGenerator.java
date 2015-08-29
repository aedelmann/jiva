package de.aedelmann.jiva.workflow.internal.jwl.mapping;

/**
 * @author Alexander Edelmann
 */
public class IdGenerator {
    private int id = 1;

    public IdGenerator() {
        this(1);
    }

    public IdGenerator(int offStart) {
        id = offStart;
    }

    public int next() {
        return id++;
    }

    public int current() {
        return id;
    }
}

