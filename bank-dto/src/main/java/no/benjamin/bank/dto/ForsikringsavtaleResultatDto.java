package no.benjamin.bank.dto;

public class ForsikringsavtaleResultatDto {
    private final String avtaleNummer;
    private final AvtaleStatus avtaleStatus;

    private ForsikringsavtaleResultatDto(String avtaleNummer, AvtaleStatus avtaleStatus) {
        this.avtaleNummer = avtaleNummer;
        this.avtaleStatus = avtaleStatus;
    }

    public String getAvtaleNummer() {
        return avtaleNummer;
    }

    public AvtaleStatus getAvtaleStatus() {
        return avtaleStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String avtaleNummer;
        private AvtaleStatus avtaleStatus;

        private Builder() {
        }

        public Builder avtaleNummer(String avtaleNummer) {
            this.avtaleNummer = avtaleNummer;
            return this;
        }

        public Builder avtaleStatus(AvtaleStatus avtaleStatus) {
            this.avtaleStatus = avtaleStatus;
            return this;
        }

        public ForsikringsavtaleResultatDto build() {
            return new ForsikringsavtaleResultatDto(avtaleNummer, avtaleStatus);
        }
    }
}
