package com.gestNutri;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "jwt.secret=test-secret-for-jwt-context-only-0123456789")
class GestNutriApplicationTests {

	@Test
	void contextLoads() {
	}

}
