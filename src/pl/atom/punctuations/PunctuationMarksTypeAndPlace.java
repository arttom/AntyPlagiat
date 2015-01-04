package pl.atom.punctuations;

/**
 * Created by Artur Tomaszewski on 2014-11-21.
 */
public class PunctuationMarksTypeAndPlace {
    private PunctuationMark punctuationMarkType;
    private int punctuationMarkPlace;

    public PunctuationMark getPunctuationMarkType() {
        return punctuationMarkType;
    }

    public void setPunctuationMarkType(PunctuationMark punctuationMarkType) {
        this.punctuationMarkType = punctuationMarkType;
    }

    public int getPunctuationMarkPlace() {
        return punctuationMarkPlace;
    }

    public void setPunctuationMarkPlace(int punctuationMarkPlace) {
        this.punctuationMarkPlace = punctuationMarkPlace;
    }

    public PunctuationMarksTypeAndPlace(PunctuationMark punctuationMarkType, int punctuationMarkPlace) {

        this.punctuationMarkType = punctuationMarkType;
        this.punctuationMarkPlace = punctuationMarkPlace;
    }
}
