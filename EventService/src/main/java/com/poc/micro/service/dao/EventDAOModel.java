package com.poc.micro.service.dao;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Event")
public class EventDAOModel {
	@Id
	private String Id;

	@Field(value = "eventSourceNode")
	private String eventSourceName;

	@Field(value = "eventMsg")
	private String eventMsg;

	@Field(value = "eventCretTs")
	private Date eventCretTs;
	
	@Field(value = "applicationId")
	private String applicationId;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getEventSourceName() {
		return eventSourceName;
	}

	public void setEventSourceName(String eventSourceName) {
		this.eventSourceName = eventSourceName;
	}

	public String getEventMsg() {
		return eventMsg;
	}

	public void setEventMsg(String eventMsg) {
		this.eventMsg = eventMsg;
	}

	public Date getEventCretTs() {
		return eventCretTs;
	}

	public void setEventCretTs(Date eventCretTs) {
		this.eventCretTs = eventCretTs;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}
