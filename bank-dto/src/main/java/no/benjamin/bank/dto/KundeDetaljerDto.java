package no.benjamin.bank.dto;

public class KundeDetaljerDto {
    private String navn;
    private String adresse;
    private String kundenummer;

    private KundeDetaljerDto(String navn, String adresse, String kundenummer) {
        this.navn = navn;
        this.adresse = adresse;
        this.kundenummer = kundenummer;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getKundenummer() {
        return kundenummer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String navn;
        private String adresse;
        private String kundenummer;

        private Builder() {
        }

        public Builder navn(String navn) {
            this.navn = navn;
            return this;
        }

        public Builder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        public Builder kundenummer(String kundenummer) {
            this.kundenummer = kundenummer;
            return this;
        }

        public KundeDetaljerDto build() {
            return new KundeDetaljerDto(navn, adresse, kundenummer);
        }
    }
}
