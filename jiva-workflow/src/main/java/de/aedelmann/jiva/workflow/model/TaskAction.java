package de.aedelmann.jiva.workflow.model;

public enum TaskAction {
    Claim("jiva.workflow.action.claim"),
    Release("jiva.workflow.action.release");

    String id;

    TaskAction(String id) {
        this.id = id;
    }

    public static TaskAction fromId(String id) {
        if (Claim.getId().equals(id)) {
            return Claim;
        } else if (Release.getId().equals(id)) {
            return Release;
        } else {
            return null;
        }
    }

    public String getId(){
        return id;
    }
}
