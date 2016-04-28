package com.poc.micro.service.model;

public class EventMessageRequest {

	private String messageTxt;
	private String eventSourceNode;
	private String applicationIdentifier;

	public String getEventSourceNode() {
		return eventSourceNode;
	}

	public void setEventSourceNode(String eventSourceNode) {
		this.eventSourceNode = eventSourceNode;
	}

	public String getMessageTxt() {
		return messageTxt;
	}

	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}

	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}

	public void setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}
}
