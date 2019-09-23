package no.benjamin.bank.integration;

import no.benjamin.bank.dto.ForsikringsAvtaleDto;
import no.benjamin.bank.dto.UtsendelsesStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BrevtjenesteConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrevtjenesteConsumer.class);

    private final RestTemplate restTemplate;

    @Value("${bank.integration.brevtjeneste.url}")
    private String brevtjenesteUrl;
    @Value("${bank.integration.brevtjeneste.port}")
    private int port;


    @Autowired
    public BrevtjenesteConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UtsendelsesStatus sendAvtaleTilKunde(ForsikringsAvtaleDto forsikringsAvtale) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(brevtjenesteUrl);
        String uri = uriComponentsBuilder
                .port(port)
                .pathSegment("send-forsikringsavtale")
                .build()
                .toUriString();

        try {
            ResponseEntity<UtsendelsesStatus> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(forsikringsAvtale), UtsendelsesStatus.class);

            if(response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                LOGGER.warn("Klarte ikke sende avtale til kunde via brevtjeneste. Statuskode: " + response.getStatusCode());
                return UtsendelsesStatus.IKKE_SENDT;
            }
        }catch(Exception e){
            LOGGER.error("Brevtjeneste ikke tilgjengelig", e);
            return UtsendelsesStatus.IKKE_SENDT;
        }
    }
}
