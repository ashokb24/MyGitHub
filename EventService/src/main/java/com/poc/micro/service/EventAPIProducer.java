package com.poc.micro.service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.poc.micro.service.model.EventMessageRequest;

/**
 * 
 * @author Ashok
 *
 */
@Configuration
public class EventAPIProducer {
	@Value("${brokerList}")
	private String brokerList;

	@Value("${sync}")
	private String sync;

	@Value("${topic}")
	private String topic;

	@Value("${producer.key.serializer.class.config}")
	private String keyValueSerClassConfig;

	@Value("${producer.value.serializer.class.config}")
	private String valueSerClassConfig;

	@Value("${producer.acks}")
	private String acks;

	@Value("${producer.retries}")
	private String retries;

	@Value("${producer.linger.ms}")
	private String linger;

	private Producer<String, String> producer;

	@Autowired
	EventAPIConsumer eventAPIConsumer;

	/**
	 * 
	 */
	@PostConstruct
	public void initIt() {
		Properties kafkaProps = new Properties();
		kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
		kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyValueSerClassConfig);
		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerClassConfig);
		kafkaProps.put(ProducerConfig.ACKS_CONFIG, acks);
		kafkaProps.put(ProducerConfig.RETRIES_CONFIG, retries);
		kafkaProps.put(ProducerConfig.LINGER_MS_CONFIG, Integer.valueOf(linger));
		producer = new KafkaProducer<>(kafkaProps);
	}

	/**
	 * 
	 * @param req
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public EventMessageResponse sendEvent(EventMessageRequest req) throws ExecutionException, InterruptedException {
		ProducerRecord<String, String> record = new ProducerRecord<>(topic,
				req.getEventSourceNode() + "##" + req.getMessageTxt() + "##"+req.getApplicationIdentifier());
		RecordMetadata recordMetaData = producer.send(record).get();
		// System.out.println("recordMetaData : " +" TopicName "
		// +recordMetaData.topic() + " Offset " +recordMetaData.offset()+"
		// PartitionId " +recordMetaData.partition());
		EventMessageResponse eventResponse = new EventMessageResponse();
		eventResponse.setOffSet(recordMetaData.offset());
		eventResponse.setPartition(recordMetaData.partition());
		eventResponse.setTopicName(recordMetaData.topic());
		eventResponse.setStatusDescription("Event Produced Successfully");
		eventAPIConsumer.run();
		return eventResponse;

	}

	/**
	 * 
	 * @param req
	 */
	private void sendAsync(EventMessageRequest req) {
		ProducerRecord<String, String> record = new ProducerRecord<>(topic,
				req.getEventSourceNode() + " : " + req.getMessageTxt());
		producer.send(record, (RecordMetadata recordMetadata, Exception e) -> {
			if (e != null) {
				e.printStackTrace();
			}
		});
		eventAPIConsumer.run();
	}
}
