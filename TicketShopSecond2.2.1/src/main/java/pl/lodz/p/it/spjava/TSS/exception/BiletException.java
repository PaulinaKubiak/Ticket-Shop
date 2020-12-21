package pl.lodz.p.it.spjava.TSS.exception;

import pl.lodz.p.it.spjava.TSS.model.Bilet;

public class BiletException extends AppBaseException {

    static final public String KEY_TICKET_SELL_OUT = "blad.bilet.wyprzedane.problem";
    static final public String KEY_TICKET_DAY = "blad.bilet.koniec.sprzedazy.problem";
    static final public String KEY_TICKET_NEGATIV_VALUE = "blad.bilet.wartosc.ujemna";
    static final public String KEY_TICKET_NOT_SOLD = "blad.bilet.brak.biletow";

    private Bilet bilet;

    public Bilet getBilet() {
        return bilet;
    }

    public BiletException(String message, Bilet bilet) {
        super(message);
        this.bilet = bilet;
    }

    public BiletException(Bilet bilet, String message, Throwable cause) {
        super(message, cause);
        this.bilet = bilet;
    }

    public BiletException(String message) {
        super(message);
    }

    public BiletException(String message, Throwable cause) {
        super(message, cause);
    }

    static public BiletException createExceptionSellOut(Bilet bilet) {
        return new BiletException(KEY_TICKET_SELL_OUT, bilet);
    }

    static public BiletException createExceptionDay(Bilet bilet) {
        return new BiletException(KEY_TICKET_DAY, bilet);
    }

    static public BiletException createNegativValue(Bilet bilet) {
        return new BiletException(KEY_TICKET_NEGATIV_VALUE, bilet);
    }

    static public BiletException createNotSold(Bilet bilet) {
        return new BiletException(KEY_TICKET_NOT_SOLD, bilet);
    }

}
