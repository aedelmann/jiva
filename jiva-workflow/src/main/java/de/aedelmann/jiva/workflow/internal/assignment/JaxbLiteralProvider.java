package de.aedelmann.jiva.workflow.internal.assignment;


import de.aedelmann.jiva.workflow.assignment.PeopleResolutionProvider;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "literal")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbLiteralProvider implements PeopleResolutionProvider {

    @XmlElement
    private String groups;

    @XmlElement
    private String users;

    public String getGroups() {
        return groups;
    }

    public String getUsers() {
        return users;
    }

    @Override
    public Set<String> resolve(WorkflowInstance workflowInstance) {
        Set<String> resolvedUsers = new HashSet<String>();
        if (groups != null) {
            List<String> groupNames = new ArrayList<String>();

            for (String groupId : groups.split(",")){
                groupNames.add(groupId);
            }
        }

        if (users != null) {
            for (String user : users.split(",")) {
                resolvedUsers.add(user);
            }
        }


        return resolvedUsers;
    }

}
