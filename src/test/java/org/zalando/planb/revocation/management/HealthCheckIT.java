package org.zalando.planb.revocation.management;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.zalando.planb.revocation.AbstractSpringIT;
import org.zalando.planb.revocation.Main;

import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.get;


@SpringApplicationConfiguration(classes = {Main.class})
@WebIntegrationTest(randomPort = true)
public class HealthCheckIT extends AbstractSpringIT {

    private static final ParameterizedTypeReference<Map<String, Object>> HEALTH_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    @Value("${local.management.port}")
    private int mgmtPort;

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    @Test
    public void testGetHealth() throws Exception {
        final Map<String, Object> health = restTemplate.exchange(
                get(URI.create("http://localhost:" + mgmtPort + "/health"))
                        .accept(APPLICATION_JSON).build(),
                HEALTH_TYPE)
                .getBody();
        assertThat(health).containsKey("cassandra");
    }
}
