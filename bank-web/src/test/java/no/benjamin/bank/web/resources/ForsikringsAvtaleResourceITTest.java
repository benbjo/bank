package no.benjamin.bank.web.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.benjamin.bank.dto.AvtaleStatus;
import no.benjamin.bank.dto.ForsikringsAvtaleDto;
import no.benjamin.bank.dto.ForsikringsDetaljerDto;
import no.benjamin.bank.dto.ForsikringsavtaleResultatDto;
import no.benjamin.bank.dto.KundeDetaljerDto;
import no.benjamin.bank.dto.UtsendelsesStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ForsikringsAvtaleResourceITTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testOpprettAvtaleHappyPath() throws URISyntaxException, JsonProcessingException {
        final ForsikringsAvtaleDto onsketAvtale = ForsikringsAvtaleDto.builder()
                .kundeDetaljer(KundeDetaljerDto.builder()
                        .navn("Benjamin Johansson")
                        .adresse("Slottet")
                        .build())
                .forsikringsDetaljer(ForsikringsDetaljerDto.builder().build())
                .build();

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8080/fagsystem/api/kunde")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("133742")
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8080/fagsystem/api/avtale/133742")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.TEXT_PLAIN)
                        .body("14501453")
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8080/brevtjeneste/api/send-forsikringsavtale")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(UtsendelsesStatus.SENDT))
                );

        mockServer.expect(ExpectedCount.once(),
        requestTo(new URI("http://localhost:8080/fagsystem/api/avtale/14501453/oppdater-status")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(AvtaleStatus.AVTALE_SENDT))
                );

        final ResponseEntity<ForsikringsavtaleResultatDto> response = testRestTemplate.exchange(
                "/api/avtale",
                HttpMethod.POST,
                new HttpEntity<>(onsketAvtale),
                ForsikringsavtaleResultatDto.class);

        ForsikringsavtaleResultatDto responseBody = response.getBody();

        mockServer.verify();
        Assert.assertEquals(responseBody.getAvtaleNummer(), "14501453");
        Assert.assertEquals(responseBody.getAvtaleStatus(), AvtaleStatus.AVTALE_SENDT);
    }

    @Test
    public void testOpprettAvtaleOpprettKundeFeiler(){

    }

    @Test
    public void testOpprettAvtaleFeiler() {

    }

    @Test
    public void testOpprettAvtaleBrevtjenesteFeiler() {

    }

    @Test
    public void testOpprettAvtaleOppdaterAvtalestatusFeiler (){

    }
}
