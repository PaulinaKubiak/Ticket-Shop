package pl.lodz.p.it.spjava.TSS.model;

public enum PoziomDostepu {
    KONTO(KluczePoziomuDostepu.KONTO_KEY),
    PRACOWNIK(KluczePoziomuDostepu.PRACOWNIK_KEY),
    KLIENT(KluczePoziomuDostepu.KLIENT_KEY),
    ADMINISTRATOR(KluczePoziomuDostepu.ADMINISTRATOR_KEY),
    NOWEKONTO(KluczePoziomuDostepu.NOWEKONTO_KEY);

    private String accessLevelKey;
    private String accessLevelI18NValue;

    private PoziomDostepu(final String key) {
        this.accessLevelKey = key;
    }

    public String getAccessLevelKey() {
        return accessLevelKey;
    }

    public String getAccessLevelI18NValue() {
        return accessLevelI18NValue;
    }

    public void setAccessLevelI18NValue(String accessLevelI18NValue) {
        this.accessLevelI18NValue = accessLevelI18NValue;
    }

    public static class KluczePoziomuDostepu {

        public static final String KONTO_KEY = "konto";
        public static final String PRACOWNIK_KEY = "pracownik";
        public static final String KLIENT_KEY = "klient";
        public static final String ADMINISTRATOR_KEY = "administrator";
        public static final String NOWEKONTO_KEY = "nowe konto";
    }
}
