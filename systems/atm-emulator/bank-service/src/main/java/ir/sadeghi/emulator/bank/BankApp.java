package ir.sadeghi.emulator.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication(scanBasePackages = "ir.sadeghi.emulator.bank")
@EnableJpaRepositories({"ir.sadeghi.emulator.bank.domain.repository"})
@EntityScan({"ir.sadeghi.emulator.bank.model"})
//@EnableEurekaClient
public class BankApp{
    public static void main(String[] args) {
        SpringApplication.run(BankApp.class, args);
    }
}