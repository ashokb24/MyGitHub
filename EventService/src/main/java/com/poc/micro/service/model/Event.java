package com.poc.micro.service.model;

import java.util.Date;

public class Event {
	private String Id;

	private String eventSourceName;

	private String eventMsg;

	private String eventCretTs;
	
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

	public String getEventCretTs() {
		return eventCretTs;
	}

	public void setEventCretTs(String eventCretTs) {
		this.eventCretTs = eventCretTs;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}
