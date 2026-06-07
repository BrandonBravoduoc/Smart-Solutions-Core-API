package com.smarth.solutions.core.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootTest
@EnableCaching
@EnableScheduling
class ApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
