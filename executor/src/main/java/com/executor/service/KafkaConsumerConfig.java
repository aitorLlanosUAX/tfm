package com.executor.service;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.executor.entities.BatchProcess;
import com.executor.entities.ExecutableFile;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory("executable"));
        return factory;
    }
    
    public ConsumerFactory<String, BatchProcess> executableConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "executable");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(BatchProcess.class,false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BatchProcess> executableKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BatchProcess> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(executableConsumerFactory());
        return factory;
    }
    
    public ConsumerFactory<String, ExecutableFile> fileExecutableConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "executable");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ExecutableFile.class,false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExecutableFile> fileExecutableKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ExecutableFile> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(fileExecutableConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, JSONObject> jsonConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "executable");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(JSONObject.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JSONObject> jsonKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JSONObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jsonConsumerFactory());
        return factory;
    }
    
	@SuppressWarnings("rawtypes")
	@Bean 
	@Primary
	public DataSource getDataSource() { 
	    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/dbcloudbatch");
	    dataSourceBuilder.username("root"); 
	    dataSourceBuilder.password(""); 
	    return dataSourceBuilder.build(); 
	}

}