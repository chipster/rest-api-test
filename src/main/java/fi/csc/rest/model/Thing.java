package fi.csc.rest.model;

import java.time.Instant;

public class Thing {

	private String name;
	private Instant created;

	public Thing() {
	}
	
	public Thing(String name) {
		this.name = name;
		this.created = Instant.now();
	}

	public Thing(String name, Instant created) {
		this.name = name;
		this.created = created;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}
	
	public String toString() {
		return name + " " + created.toString();
	}
}
