package com.poc.micro.service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.poc.micro.service.dao.EventDAOService;

@Configuration
@PropertySource("consumer.properties")
public class EventAPIConsumer {

	@Autowired
	private EventDAOService eventDaoService;

	@Value("${bootstrap.servers}")
	private String bootStrapServers;

	@Value("${pollingInterval}")
	private int pollInterval;

	@Value("${topic}")
	private String topic;

	@Value("$groupId")
	private String groupId;

	@Value("${consumer.key.serializer.class.config}")
	private String keyValueDeSerClassConfig;

	@Value("${consumer.value.serializer.class.config}")
	private String valueDeSerClassConfig;

	@Value("${consumer.enable.auto.commit}")
	private String enableAutoCommit;

	@Value("${consumer.auto.commit.interval}")
	private String autoCommitInterval;

	@Value("${consumer.session.time.out.interval}")
	private String sessionTimeOutInterval;

	private KafkaConsumer<String, String> consumer;

	@PostConstruct
	public void initIt() {
		Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
		kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		kafkaProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		kafkaProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeOutInterval);
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyValueDeSerClassConfig);
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeSerClassConfig);
		consumer = new KafkaConsumer<>(kafkaProps);
		consumer.subscribe(Arrays.asList(topic));
	}

	/**
	 * 
	 */
	public void run() {
		ConsumerRecords<String, String> records = consumer.poll(5000);
		if (records != null) {
			for (TopicPartition partition : records.partitions()) {
				List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
				for (ConsumerRecord<String, String> record : partitionRecords) {
					System.out.printf(" offset = %d, partition=%d, key = %s, value = %s", record.offset(),
							record.partition(), record.key(), record.value());
					// MongoDB code goes here
					String event[] = record.value().split("##");
					eventDaoService.saveEvent(event[0].trim(), event[1].trim(),event[2].trim());
					consumer.commitSync();
				}
			}
		}
	}
}
