package no.benjamin.bank.integration;

import no.benjamin.bank.dto.AvtaleStatus;
import no.benjamin.bank.dto.ForsikringsDetaljerDto;
import no.benjamin.bank.dto.KundeDetaljerDto;
import no.benjamin.bank.integration.exception.FagsystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Component
public class FagsystemConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FagsystemConsumer.class);

    private final RestTemplate restTemplate;

    @Value("${bank.integration.fagsystem.url}")
    private String fagsystemUrl;
    @Value("${bank.integration.fagsystem.port}")
    private int port;

    @Autowired
    public FagsystemConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String opprettKunde(KundeDetaljerDto kunde) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(fagsystemUrl);
        String uri = uriComponentsBuilder
                .port(port)
                .pathSegment("kunde")
                .build()
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(kunde), String.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                LOGGER.warn("Klarte ikke opprette kunde i fagsystem. Statuskode: {}", response.getStatusCode());
                throw new FagsystemException("Det oppstod en feil. Forsikringsavtale har ikke blitt opprettet. Vennligst prøv igjen.",
                        response.getStatusCodeValue());
            }
        } catch (Exception e) {
            LOGGER.error("Fagsystem ikke tilgjengelig", e);
            throw new FagsystemException("Fagsystemet er for øyeblikket ikke tilgjengelig. Forsikringsavtale har ikke blitt opprettet", 404);
        }
    }

    public String opprettAvtale(String kundenr, ForsikringsDetaljerDto forsikringsDetaljer) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(fagsystemUrl);
        String uri = uriComponentsBuilder
                .port(port)
                .pathSegment("avtale", "{kundeNr}")
                .build()
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(forsikringsDetaljer), String.class, Collections.singletonMap("kundeNr", kundenr));
            if(response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                LOGGER.warn("Klarte ikke opprette avtale i fagsystem for kundenr {}. Statuskode: {}", kundenr, response.getStatusCode());
                throw new FagsystemException("Det oppstod en feil. Forsikringsavtale har ikke blitt opprettet. Vennligst prøv igjen.",
                        response.getStatusCodeValue());
            }
        } catch (Exception e) {
            LOGGER.error("Fagsystem ikke tilgjengelig", e);
            throw new FagsystemException("Fagsystemet er for øyeblikket ikke tilgjengelig. Forsikringsavtale har ikke blitt opprettet", 404);
        }
    }

    public AvtaleStatus oppdaterAvtaleStatus(AvtaleStatus avtaleStatus, String avtaleNr) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(fagsystemUrl);
        String uri = uriComponentsBuilder
                .port(port)
                .pathSegment("avtale", "{avtaleNr}", "oppdater-status")
                .build()
                .toUriString();

        try {
            ResponseEntity<AvtaleStatus> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(avtaleStatus), AvtaleStatus.class, Collections.singletonMap("avtaleNr", avtaleNr));

            if(response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                LOGGER.warn("Klarte ikke oppdatere avtalestatus for avtale med avtalenr {}. Statuskode: {}", avtaleNr, response.getStatusCode());
                return AvtaleStatus.AVTALE_OPPRETTET;
            }
        }catch (Exception e){
            LOGGER.error("Fagsystem ikke tilgjengelig", e);
            return AvtaleStatus.AVTALE_OPPRETTET;
        }
    }
}
