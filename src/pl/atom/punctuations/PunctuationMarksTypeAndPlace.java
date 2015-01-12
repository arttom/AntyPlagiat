package pl.atom.punctuations;

/**
 * Klasa zawierająca typ znaku interpunkcyjnego oraz jego umiejscowienie w tekście.
 * Created by Artur Tomaszewski on 2014-11-21.
 */
public class PunctuationMarksTypeAndPlace {
    /**
     * Typ znaku interpunkcyjnego
     */
    private PunctuationMark punctuationMarkType;
    /**
     * Umiejscowienie znaku interpunkcyjnego w tek?cie
     */
    private int punctuationMarkPlace;

    /**
     * Getter na pole punctiationMarkType
     * @return - wartość pola punctuationMarkType
     */
    public PunctuationMark getPunctuationMarkType() {
        return punctuationMarkType;
    }
    /**
     * Setter na pole punctiationMarkType
     */
    public void setPunctuationMarkType(PunctuationMark punctuationMarkType) {
        this.punctuationMarkType = punctuationMarkType;
    }
    /**
     * Getter na pole punctiationMarkPlace
     * @return - wartość pola punctuationMarkPlace
     */
    public int getPunctuationMarkPlace() {
        return punctuationMarkPlace;
    }
    /**
     * Setter na pole punctiationMarkPlace
     */
    public void setPunctuationMarkPlace(int punctuationMarkPlace) {
        this.punctuationMarkPlace = punctuationMarkPlace;
    }

    /**
     * Konstruktor tworzący obiekt klasy.
     * @param punctuationMarkType
     * @param punctuationMarkPlace
     */
    public PunctuationMarksTypeAndPlace(PunctuationMark punctuationMarkType, int punctuationMarkPlace) {

        this.punctuationMarkType = punctuationMarkType;
        this.punctuationMarkPlace = punctuationMarkPlace;
    }
}
