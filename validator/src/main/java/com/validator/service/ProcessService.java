package com.validator.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.validator.entities.BatchProcess;
import com.validator.repository.ProcessRepository;

@Service
public class ProcessService {

	Logger logger = LoggerFactory.getLogger(ProcessService.class);

	private ProcessRepository processRepository;
	@Autowired
	private ApplicationContext context;

	@Autowired
	public void context(ApplicationContext context) {
		this.context = context;
	}

	public ProcessService(ProcessRepository processRepository) {
		this.processRepository = processRepository;
	}

	public int executeProcess(Integer id) {
		logger.info("Received id to check: " + id);
		logger.info("Checking if the process exists: " + id);

		Optional<BatchProcess> optionalProcess = processRepository.findById(id);
		if (optionalProcess.isEmpty()) {
			logger.error("The process does not exists with id: " + id);
			return -1;
		}
		BatchProcess process = optionalProcess.get();
		logger.info("The process exists: " + process);
		logger.info("Checking if the process is active");

		if (!process.isActive()) {
			logger.error("The process is not active: " + process);
			return -1;
		}
		logger.info("The process is active: " + process);
		logger.info("Checking if the process has a terraform file: " + process);
		if (process.getPathTemplate() == null) {
			logger.error("The process has not a terraform file: " + process);

			return -1;
		}
		logger.info("Sending the process through kafka to executor (creation): " + process);
		MessageProducer producer = context.getBean(MessageProducer.class);
		producer.sendexecutableMessage(process);

		return 0;

	}

	@Bean
	public MessageProducer messageProducer() {
		return new MessageProducer();
	}

	public static class MessageProducer {

		@Autowired
		private KafkaTemplate<String, BatchProcess> executableKafkaTemplate;

		@Value("${message.topic.name}")
		private String executableTopicName;

		@Value("${message.topic.destroy}")
		private String destroyTopicName;

		public void sendexecutableMessage(BatchProcess executable) {
			executableKafkaTemplate.send(executableTopicName, executable);
		}

		public void sendDestroyMessage(BatchProcess executable) {
			executableKafkaTemplate.send(destroyTopicName, executable);
		}
	}

	public int destroyProcess(Integer id) {
		logger.info("Received id to check: " + id);
		logger.info("Checking if the process exists: " + id);
		Optional<BatchProcess> optionalProcess = processRepository.findById(id);
		if (optionalProcess.isEmpty()) {
			logger.error("The process does not exists with id: " + id);
			return -1;
		}
		BatchProcess process = optionalProcess.get();
		logger.info("The process exists: " + process);
		logger.info("Checking if the process is active");

		if (!process.isActive()) {
			logger.error("The process is not active: " + process);
			return -1;
		}
		logger.info("The process is active: " + process);
		logger.info("Checking if the process has a terraform file: " + process);
		if (process.getPathTemplate() == null) {
			return -1;
		}
		logger.info("Sending the process through kafka to executor (destroy): " + process);
		MessageProducer producer = context.getBean(MessageProducer.class);

		producer.sendDestroyMessage(process);
		return 0;

	}

}
