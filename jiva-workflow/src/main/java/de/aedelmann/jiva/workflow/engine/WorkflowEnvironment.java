package de.aedelmann.jiva.workflow.engine;

/**
 * @author Alexander Edelmann
 */
public class WorkflowEnvironment {
    private static final WorkflowEnvironment environment = new WorkflowEnvironment();

    static final ThreadLocal<String> userThreadLocal = new ThreadLocal<String>();


    private WorkflowEnvironment() {
    }

    public static WorkflowEnvironment current() {
        return environment;
    }


    public String getCurrentUserId() {
        return userThreadLocal.get();
    }


    public static void setCurrentUserId(String userId) {
        userThreadLocal.set(userId);
    }
}
