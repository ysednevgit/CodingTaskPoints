package com.yury.codingTask.points.integrationtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CodingTaskApplicationTests {

	@Test
	void testMainControllerGetPoints() {

		HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

		ResponseEntity<String> response = new RestTemplate().exchange("http://localhost:8080/points/get_points",
				HttpMethod.GET, entity, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
