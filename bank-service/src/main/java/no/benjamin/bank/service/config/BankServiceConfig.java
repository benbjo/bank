package no.benjamin.bank.service.config;

import no.benjamin.bank.integration.config.BankIntegrationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"no.benjamin.bank.service"})
@Import({BankIntegrationConfig.class})
public class BankServiceConfig {
}
