package no.benjamin.bank.web.config;

import no.benjamin.bank.service.config.BankServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BankServiceConfig.class})
public class BankWebConfig {
}
