package ru.stepic.webservice.util;

public class LongId<T> {

	private long id;
	
	public LongId(long id) {
		this.setId(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id; 
	}
}


