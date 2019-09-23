package no.benjamin.bank.dto;

import java.time.LocalDateTime;

public class ForsikringsAvtaleDto {
    private String avtalenummer;
    private AvtaleStatus avtaleStatus;
    private KundeDetaljerDto kundeDetaljer;
    private ForsikringsDetaljerDto forsikringsDetaljer;
    private LocalDateTime avtaleOpprettet;

    public ForsikringsAvtaleDto () {
        //Jackson
    }

    private ForsikringsAvtaleDto(String avtalenummer, ForsikringsDetaljerDto avtaleDetaljer, AvtaleStatus avtaleStatus, KundeDetaljerDto kundeDetaljer, LocalDateTime avtaleOpprettet) {
        this.avtalenummer = avtalenummer;
        this.forsikringsDetaljer = avtaleDetaljer;
        this.avtaleStatus = avtaleStatus;
        this.kundeDetaljer = kundeDetaljer;
        this.avtaleOpprettet = avtaleOpprettet;
    }

    public String getAvtalenummer() {
        return avtalenummer;
    }

    public AvtaleStatus getAvtaleStatus() {
        return avtaleStatus;
    }

    public KundeDetaljerDto getKundeDetaljer() {
        return kundeDetaljer;
    }

    public ForsikringsDetaljerDto getForsikringsDetaljer() {
        return forsikringsDetaljer;
    }

    public LocalDateTime getAvtaleOpprettet() {
        return avtaleOpprettet;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String avtalenummer;
        private AvtaleStatus avtaleStatus;
        private KundeDetaljerDto kundeDetaljer;
        private ForsikringsDetaljerDto forsikringsDetaljer;
        private LocalDateTime avtaleOpprettet;

        private Builder() {
        }

        public Builder avtalenummer(String avtalenummer) {
            this.avtalenummer = avtalenummer;
            return this;
        }

        public Builder avtaleStatus(AvtaleStatus avtaleStatus) {
            this.avtaleStatus = avtaleStatus;
            return this;
        }

        public Builder kundeDetaljer(KundeDetaljerDto kundeDetaljer) {
            this.kundeDetaljer = kundeDetaljer;
            return this;
        }

        public Builder forsikringsDetaljer(ForsikringsDetaljerDto forsikringsDetaljer) {
            this.forsikringsDetaljer = forsikringsDetaljer;
            return this;
        }

        public Builder avtaleOpprettet(LocalDateTime avtaleOpprettet){
            this.avtaleOpprettet = avtaleOpprettet;
            return this;
        }

        public ForsikringsAvtaleDto build() {
            return new ForsikringsAvtaleDto(avtalenummer, forsikringsDetaljer, avtaleStatus, kundeDetaljer, avtaleOpprettet);
        }
    }
}
