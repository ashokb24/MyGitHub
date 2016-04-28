package com.poc.micro.service;

/**
 * 
 * @author Ashok
 *
 */
public class EventMessageResponse {

	private int partition;
	private String topicName;
	private long offSet;
	private String statusDescription;

	public int getPartition() {
		return partition;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public long getOffSet() {
		return offSet;
	}

	public void setOffSet(long offSet) {
		this.offSet = offSet;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}
