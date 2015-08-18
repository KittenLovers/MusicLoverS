package it.univr.musiclovers.types;

public final class CodiceFiscale {

    private final String codiceFiscale;

    public CodiceFiscale(String codiceFiscale) throws MalformedCodiceFiscale {
        this.codiceFiscale = codiceFiscale;
    }

    public String check(String codiceFiscale) throws MalformedCodiceFiscale {
        int i, s, c;
        String refactorCF;
        int[] setdisp = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        if (codiceFiscale.length() == 0 || codiceFiscale.length() != 16) {
            throw new MalformedCodiceFiscale("La lunghezza del codice fiscale non &egrave; corretta: il codice fiscale dovrebbe essere lungo esattamente 16 caratteri.");
        }

        refactorCF = codiceFiscale.toUpperCase();
        for (i = 0; i < 16; i++) {
            c = refactorCF.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                throw new MalformedCodiceFiscale("Il codice fiscale contiene dei caratteri non validi:\n i soli caratteri validi sono le lettere e le cifre.");
            }
        }
        s = 0;
        for (i = 1; i <= 13; i += 2) {
            c = refactorCF.charAt(i);
            if (c >= '0' && c <= '9') {
                s = s + c - '0';
            } else {
                s = s + c - 'A';
            }
        }
        for (i = 0; i <= 14; i += 2) {
            c = refactorCF.charAt(i);
            if (c >= '0' && c <= '9') {
                c = c - '0' + 'A';
            }
            s += setdisp[c - 'A'];
        }
        if (s % 26 + 'A' != refactorCF.charAt(15)) {
            throw new MalformedCodiceFiscale("Il codice fiscale non &egrave; corretto:\n il codice di controllo non corrisponde.");
        }
        return refactorCF;
    }

    private static class MalformedCodiceFiscale extends Exception {

        private static final long serialVersionUID = 1L;

        public MalformedCodiceFiscale(String errorMessage) {
            super(errorMessage);
        }
    }
}
