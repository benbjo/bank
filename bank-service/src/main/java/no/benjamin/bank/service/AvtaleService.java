package no.benjamin.bank.service;

import no.benjamin.bank.dto.AvtaleStatus;
import no.benjamin.bank.dto.ForsikringsAvtaleDto;
import no.benjamin.bank.dto.ForsikringsavtaleResultatDto;
import no.benjamin.bank.dto.UtsendelsesStatus;
import no.benjamin.bank.integration.BrevtjenesteConsumer;
import no.benjamin.bank.integration.FagsystemConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AvtaleService {
    private final FagsystemConsumer fagSystemConsumer;
    private final BrevtjenesteConsumer brevtjenesteConsumer;

    @Autowired
    public AvtaleService(FagsystemConsumer fagSystemConsumer, BrevtjenesteConsumer brevtjenesteConsumer){
        this.fagSystemConsumer = fagSystemConsumer;
        this.brevtjenesteConsumer = brevtjenesteConsumer;
    }

    public ForsikringsavtaleResultatDto opprettAvtale(ForsikringsAvtaleDto onsketForsikringsAvtale){

        String kundenr = fagSystemConsumer.opprettKunde(onsketForsikringsAvtale.getKundeDetaljer());
        String avtalenr = fagSystemConsumer.opprettAvtale(kundenr, onsketForsikringsAvtale.getForsikringsDetaljer());

        AvtaleStatus avtaleStatus = AvtaleStatus.AVTALE_OPPRETTET;

        ForsikringsAvtaleDto forsikringsAvtale = ForsikringsAvtaleDto.builder()
                .kundeDetaljer(onsketForsikringsAvtale.getKundeDetaljer())
                .forsikringsDetaljer(onsketForsikringsAvtale.getForsikringsDetaljer())
                .avtalenummer(avtalenr)
                .avtaleStatus(avtaleStatus)
                .avtaleOpprettet(LocalDateTime.now())
                .build();

        UtsendelsesStatus utsendelsesStatus = brevtjenesteConsumer.sendAvtaleTilKunde(forsikringsAvtale);

        if(utsendelsesStatus == UtsendelsesStatus.SENDT) {
            avtaleStatus = fagSystemConsumer.oppdaterAvtaleStatus(AvtaleStatus.AVTALE_SENDT, avtalenr);
        }

        return ForsikringsavtaleResultatDto.builder()
                .avtaleNummer(avtalenr)
                .avtaleStatus(avtaleStatus)
                .build();
    }
}
