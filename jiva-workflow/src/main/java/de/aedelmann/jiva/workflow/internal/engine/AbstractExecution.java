package de.aedelmann.jiva.workflow.internal.engine;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.aedelmann.jiva.workflow.engine.Execution;

@MappedSuperclass
public abstract class AbstractExecution implements Execution {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date completeBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;
    
    protected String name;

    public AbstractExecution(String modelName, Date createdOn) {
		this.name = modelName;
		this.createdOn = createdOn;
	}
    
    protected AbstractExecution() {
    	// used by JPA
	}
    
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
