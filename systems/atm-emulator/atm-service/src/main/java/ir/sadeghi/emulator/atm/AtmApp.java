package ir.sadeghi.emulator.atm;

//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import com.rabbitmq.client.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;

@SpringBootApplication(scanBasePackages = "ir.sadeghi.emulator.atm")
@EnableCircuitBreaker
@EnableHystrixDashboard
public class AtmApp {
    private static final Logger LOG = LoggerFactory.getLogger(AtmApp.class);

    static {
        // for localhost testing only
        LOG.warn("Will now disable hostname check in SSL, only to be used during development");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }

//    @Value("${app.rabbitmq.host:localhost}")
//    String rabbitMqHost;

    //    @Bean
//    public ConnectionFactory connectionFactory() {
//        LOG.info("Create RabbitMqCF for host: {}", rabbitMqHost);
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqHost);
//        return connectionFactory.getRabbitConnectionFactory();
//    }
//    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        LOG.info("Register MDCHystrixConcurrencyStrategy");
        SpringApplication.run(AtmApp.class, args);
    }
}
