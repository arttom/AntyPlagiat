package pl.atom;

import pl.atom.punctuations.PunctuationMarksTypeAndPlace;
import pl.atom.utils.GoogleSearcher;
import pl.atom.utils.SearcherMOK;
import pl.atom.utils.SearchException;
import pl.atom.utils.Searcher;

import java.util.LinkedList;
import java.util.List;

import static pl.atom.punctuations.PunctuationMark.*;

/**
 * Główna klasa aplikacji. Zajmuje się operacjami związanymi ze sprawdzeniem tekstu pod kątem plagiatu
 * Created by Artur Tomaszewski on 2014-11-20.
 */
public class PlagiarismChecker {

    private String[] wordsFromText; //tablica słów z tekstu
    private LinkedList<PunctuationMarksTypeAndPlace> punctuationMarksTypeAndPlaceList; //lista znaków interpunkcyjnych
    private LinkedList<String> searchPhrases; //lista fraz wyszukiwania
    private LinkedList<String> negativePhrases; //lista znalezionych fraz w trakcie wyszukiwania
    private String textToCheck; //tekst do sprawdzenia

    public String getTextToCheck() {
        return textToCheck;
    }

    private Searcher searcher; //wyszukiwacz, póki co jest jedynie Google.
    private int phrasesLimit; //pole określające limit fraz
    private final int wordsBeforeColonsAndComa = 3; //liczba słów przed średnikiem, dwukropkiem i przecinkiem
    private final int wordsBeforeQuestionAndExclamationMark = 6; //liczba słów przed znakiem zapytania i wykrzyknienia
    private final int wordsAfterDot = 6; //liczba słów po kropce
    private final int wordsAfterColonsAndComa = 3; //liczba słów po średniku, dwukropku i przecinku

    public LinkedList<String> getSearchPhrases() {
        return searchPhrases;
    }

    /**
     * Sprawdzaenie czy dany tekst jest plagiatem
     * @param text - tekst do sprawdzenia
     * @return - lista fraz które zostały odnalezione w sieci
     * @throws SearchException - wyjątek w przypadku błędu wyszukiwania
     */
    public List<String> checkTextForPlagiarism(String text) throws SearchException {
       if(searcher==null){
           searcher=new SearcherMOK();
           phrasesLimit=100;
       }
        this.textToCheck=text;
        punctuationMarksTypeAndPlaceList=new LinkedList<PunctuationMarksTypeAndPlace>();
        removeQuotation();
        createSearchPhrases();
        searchPhrases();
        highlightPhrases();
        return negativePhrases;
    }

    private void highlightPhrases() {
        for(String s:negativePhrases){
            textToCheck=textToCheck.replace(s,s.toUpperCase());
        }
    }

    /**
     * Usunięcie z tekstu fragmentów które są pomiędzy cytatami. Nie obsługuje cytatów zagnieżdżonych
     */
    private void removeQuotation() {
        this.textToCheck=this.textToCheck.replaceAll("\"(.*?)\"","");
        this.textToCheck=this.textToCheck.replace(" . ",". ");
        this.textToCheck=this.textToCheck.replace(" , ",". ");
        this.textToCheck=this.textToCheck.replace(" ! ",". ");
        this.textToCheck=this.textToCheck.replace(" ? ",". ");
        this.textToCheck=this.textToCheck.replace(" ; ",". ");
        this.textToCheck=this.textToCheck.replace(" : ",". ");
    }

    /**
     * getter na listę fraz odnalezionych w sieci
     * @return zwraca listę fraz które zostały odnalezione w sieci
     */
    public LinkedList<String> getNegativePhrases() {
        return negativePhrases;
    }

    /**
     * Utworzenie z tekstu fraz do wyszukiwania
     */
    private void createSearchPhrases(){
        wordsFromText=textToCheck.split(" ");
        findPunctuationMarks();
        createPhrases();
    }

    /**
     * utworzenie fraz na podstawie znalezionych znaków interpunkcyjnych
     */
    private void createPhrases() {
        searchPhrases=new LinkedList<String>();
        StringBuilder tempPhrase = new StringBuilder("");
        if(wordsFromText.length>wordsAfterDot) {
            for(int i=0;i<wordsAfterDot;i++){
                tempPhrase.append(wordsFromText[i]+" ");

            }
            searchPhrases.add(new String(tempPhrase));
           for(PunctuationMarksTypeAndPlace mark: punctuationMarksTypeAndPlaceList) {
               tempPhrase = new StringBuilder("");
                switch(mark.getPunctuationMarkType()){
                    case DOT:
                        if(wordsFromText.length>mark.getPunctuationMarkPlace()+wordsAfterDot){
                            for(int i=mark.getPunctuationMarkPlace()+1;i<mark.getPunctuationMarkPlace()+wordsAfterDot+1;i++){
                                tempPhrase.append(wordsFromText[i]+" ");
                            }
                            searchPhrases.add(new String(tempPhrase));
                        }

                        break;
                    case SEMICOLON:
                        if(wordsFromText.length-wordsBeforeColonsAndComa>mark.getPunctuationMarkPlace()){
                            for(int i=mark.getPunctuationMarkPlace()-wordsBeforeColonsAndComa+1;i<mark.getPunctuationMarkPlace()+wordsAfterColonsAndComa+1;i++){
                                tempPhrase.append(wordsFromText[i]+" ");
                            }
                            searchPhrases.add(new String(tempPhrase));
                        }

                        break;
                    case COMA:
                        if(wordsFromText.length-3>mark.getPunctuationMarkPlace()){
                            for(int i=mark.getPunctuationMarkPlace()-wordsBeforeColonsAndComa+1;i<mark.getPunctuationMarkPlace()+wordsAfterColonsAndComa+1;i++){
                                tempPhrase.append(wordsFromText[i]+" ");
                            }
                            searchPhrases.add(new String(tempPhrase));
                        }
                        break;
                    case QUESTION_MARK:
                        if(wordsFromText.length-wordsBeforeQuestionAndExclamationMark>mark.getPunctuationMarkPlace()){
                            for(int i=mark.getPunctuationMarkPlace()-wordsBeforeQuestionAndExclamationMark+1;i<mark.getPunctuationMarkPlace()+1;i++){
                                tempPhrase.append(wordsFromText[i]+" ");
                            }
                            searchPhrases.add(new String(tempPhrase));
                        }
                        break;
                    case EXCLAMATION_MARK:
                        if(wordsFromText.length-wordsBeforeQuestionAndExclamationMark>mark.getPunctuationMarkPlace()){
                            for(int i=mark.getPunctuationMarkPlace()-wordsBeforeQuestionAndExclamationMark+1;i<mark.getPunctuationMarkPlace()+1;i++){
                                tempPhrase.append(wordsFromText[i]+" ");
                            }
                            searchPhrases.add(new String(tempPhrase));
                        }
                        break;
                    case COLON:
                        if(wordsFromText.length-wordsBeforeColonsAndComa>mark.getPunctuationMarkPlace()){
                            for(int i=mark.getPunctuationMarkPlace()-wordsBeforeColonsAndComa+1;i<mark.getPunctuationMarkPlace()+wordsAfterColonsAndComa+1;i++){
                                tempPhrase.append(wordsFromText[i]+" ");
                            }
                            searchPhrases.add(new String(tempPhrase));
                        }
                        break;
                    default: break;
                }
            }
        }

    }

    /**
     * Odnajdywanie znaków interupunkcyjnych w tekście. Wywołuje kolejno funkcje
     */
    private void findPunctuationMarks() {
        findDots();
        findComas();
        findQuestionMarks();
        findExclamationMarks();
        findSemiColons();
        findColons();
    }

    /**
     * Funkcja znajdująca znaki cudzysłowia. Ze względu na usuwanie ich z tekstu nie używana.
     */
    private void findQuotationMarks() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains("\"")) {
                punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(QUOTATION_MARK,i));
            }
        }
    }

    /**
     * Funkcja odnajdująca dwukropki
     */
    private void findColons() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains(":") && i> wordsBeforeColonsAndComa) {
                    punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(COLON, i));
            }
        }
    }

    /**
     * Funkcja odnajdująca średniki
     */
    private void findSemiColons() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains(";") && i> wordsBeforeColonsAndComa) {
                    punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(SEMICOLON, i));
            }
        }
    }

    /**
     * Funkcja odnajdująca znaki wykrzyknienia
     */
    private void findExclamationMarks() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains("!") && i>wordsBeforeQuestionAndExclamationMark) {
                    punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(EXCLAMATION_MARK, i));
            }
        }
    }

    /**
     * Funkcja odnajdująca znaki zapytania
     */
    private void findQuestionMarks() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains("?") && i>wordsBeforeQuestionAndExclamationMark) {
                    punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(QUESTION_MARK, i));
            }
        }
    }

    /**
     * Funkcja odnaujdąca przecinki
     */
    private void findComas() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains(",") && i>wordsBeforeColonsAndComa) {
                    punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(COMA, i));
            }
        }
    }

    /**
     * funkcja odnajdująca kropki
     */
    private void findDots() {
        for(int i=0;i<wordsFromText.length;i++) {
            if (wordsFromText[i].contains(".")) {
                punctuationMarksTypeAndPlaceList.add(new PunctuationMarksTypeAndPlace(DOT,i));
            }
        }
    }

    /**
     * Funkcja wywołująca wyszukiwania dla każdej z fraz.
     * Maksymalna liczba fraz to 100 (ze względu na limit API google)
     * @throws SearchException - w przypadku błędów z wyszukiwaniem tj. błędna konfiguracja, koniec limitu
     */
    private void searchPhrases() throws SearchException {
        removeSearchPhrasesAboveLimit();
        negativePhrases=new LinkedList<String>();
        for(String s:searchPhrases){
            if(searcher.getNumberOfResults(s)>0){
                negativePhrases.add(s);
            }
        }

    }

    /**
     * Ze względu na limit google pozostawienie jedynie 99 fraz.
     */
    private void removeSearchPhrasesAboveLimit() {
        if(phrasesLimit!=0) {
            while (searchPhrases.size() >= phrasesLimit) {
                searchPhrases.removeLast();
            }
            cutText();
        }
    }

    /**
     * Skracanie tekstu do ostatniej wyszukiwanej frazy
     */
    private void cutText() {
        int lastPhrasePlace=getLastPhrase();
        textToCheck=textToCheck.substring(0,lastPhrasePlace);
        textToCheck=textToCheck+searchPhrases.getLast();
    }

    /**
     * Zwraca miejsce w tekście ostatniej wyszukiwanej frazy
     * @return
     */
    private int getLastPhrase() {
        int place=0;
        for(String s:searchPhrases){
            int i=textToCheck.lastIndexOf(s);
            if(i>place) place=i;
        }
        return place;
    }

    /**
     * Getter na liczbe fraz wyszukiwania
     * @return liczbę wyszukiwanych fraz
     */
    public int getSearchPhrasesNumber(){
        return searchPhrases.size();
    }

    /**
     * getter na liczbę fraz które zostały znalezione
     * @return liczbę znalezionych fraz
     */
    public int getNegativePhrasesNumber(){
        return negativePhrases.size();
    }

    public void setGoogleSearcher(int limit) {
        searcher=new GoogleSearcher();
        this.phrasesLimit=limit;
    }
}
