# jiva

[![Build Status](https://drone.io/github.com/aedelmann/jiva/status.png)](https://drone.io/github.com/aedelmann/jiva/latest)
[![Join the chat at https://gitter.im/aedelmann/jiva](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/aedelmann/jiva?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
Jiva is an easy and elegant way to create and manage human-oriented workflows and are processed by a light-weight engine. 

## Example Workflow
```xml
<?xml version="1.0" encoding="UTF-8"?>
<workflow id="two-step-approval">
     <completeBy lang="xpath" expression="date:after($workflowInstance/createdOn,'3')"/>
	 <start>
	 	<transition id="request" to="approval"/>
	 </start>

	 <task id="approval">
	    <completeBy lang="time" expression="2 days"/>
        <assignment routing="sequential"> <!-- Sequentially route to every resolved user -->
            <handler>de.aedelmann.jiva.assignment.WeekdayAssignment</handler>
            <postprocessor>
                <processor key="addSubstitutes"/>
                <processor key="removeAbsent"/>
            </postprocessor>
            <completionPolicy>
                <allMatch transitionId="Approve"/> <!-- Transition 'Approve' is taken if everyone in sequence approves  -->
            </completionPolicy>
        </assignment>
	 	<transition id="Approve" to="follow-up"/>
	 	<transition id="Reject"  to="rejected"/>
	 </task>

	 <task id="follow-up">
	    <completeBy lang="time" expression="1 day">
	        <escalation>
	            <condition lang="groovy" expression="$workflowInstance.priority == 'high'" />
	            <emailRule>
	                <literal>
	                  <groups>humanResources</groups>
	                </literal>
	            </emailRule>
	        </escalation>
	    </completeBy>
        <assignment routing="exclusive">
            <notify remind="yes">
                <email/>
            </notify>
            <initiator/>
        </assignment>
     	<transition id="Complete" to="approved"/>
     </task>

     <end id="approved"/>
     <end id="rejected"/>

</workflow>
```

