package com.adrianomenezes.test_eis_persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

@SpringBootApplication(
//		exclude = { CassandraDataAutoConfiguration.class, CassandraAutoConfiguration.class }
)
public class TestEisPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestEisPersistenceApplication.class, args);
	}

}
