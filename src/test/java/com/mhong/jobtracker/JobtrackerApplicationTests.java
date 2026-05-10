package com.mhong.jobtracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"JWT_SECRET=test-secret-for-tests"})
class JobtrackerApplicationTests {
	@Test
	void contextLoads() {}
}
