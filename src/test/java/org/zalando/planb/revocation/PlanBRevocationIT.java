package org.zalando.planb.revocation;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringApplicationConfiguration(classes = { Main.class })
@WebIntegrationTest(randomPort = true)
@ActiveProfiles("it")
public class PlanBRevocationIT extends AbstractSpringTest {

    private static final Logger log = LoggerFactory.getLogger(PlanBRevocationIT.class);

    @Value("${local.server.port}")
    private int port;

    @Test
    public void run() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity(
                URI.create("http://localhost:" + port + "/foo"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        log.info(response.getBody());
    }
}
