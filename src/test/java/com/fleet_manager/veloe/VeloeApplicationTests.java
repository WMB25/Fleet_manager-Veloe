package com.fleet_manager.veloe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class VeloeApplicationTests {

	@Test
	void contextLoads() {
        // Implementable Test
	}

}
