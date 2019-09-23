package no.benjamin.bank.integration.exception;

public class FagsystemException extends RuntimeException {
    private final Integer statusKode;

    public FagsystemException(String feilMelding, Integer statusKode) {
        super(feilMelding);
        this.statusKode = statusKode;
    }

    public Integer getStatusKode() {
        return statusKode;
    }
}
