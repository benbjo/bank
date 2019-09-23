package no.benjamin.bank.dto;

public class ForsikringsDetaljerDto {
    private Double aarsPris;
    private Forsikringstype forsikringstype;

    public ForsikringsDetaljerDto(Double aarsPris, Forsikringstype forsikringstype) {
        this.aarsPris = aarsPris;
        this.forsikringstype = forsikringstype;
    }

    public Double getAarsPris() {
        return aarsPris;
    }

    public Forsikringstype getForsikringstype() {
        return forsikringstype;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Double aarsPris;
        private Forsikringstype forsikringstype;

        private Builder() {
        }

        public Builder aarsPris(Double aarsPris) {
            this.aarsPris = aarsPris;
            return this;
        }

        public Builder forsikringstype(Forsikringstype forsikringstype) {
            this.forsikringstype = forsikringstype;
            return this;
        }

        public ForsikringsDetaljerDto build() {
            return new ForsikringsDetaljerDto(aarsPris, forsikringstype);
        }
    }
}
