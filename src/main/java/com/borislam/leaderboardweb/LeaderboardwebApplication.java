package com.borislam.leaderboardweb;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class LeaderboardwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderboardwebApplication.class, args);
	}


	@Bean
	public DynamoDbClient dynamoDbClient() {
		Region region = Region.US_EAST_1;
		DynamoDbClient ddb = DynamoDbClient.builder()
				.region(region)
				.build();

		return ddb;
	}
}
