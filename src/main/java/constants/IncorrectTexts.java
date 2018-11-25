package constants;

import java.util.Arrays;
import java.util.List;

public enum IncorrectTexts {

    FIRST_TEXT("Have you ever dramed of running a bookshop?", "dreamed"),
    SECOND_TEXT("Grannies are vey important for femle elephants.", "very", "female"),

    RUSSIAN("Этот текст на руском языке", "русском"),
    ENGLISH("This text is written in Englsh language", "English"),
    UKRAINIAN("Цей текст українькою мовою", "українською"),

    DIGIT_TEXT("This text contains123 digit word", "contains 123"),
    WRONG_CAPITALIZATION("This text contains wrong capitalization of london", "London"),
    REPEATED_WORDS_TEXT("This text contains contains repeated words", "contains"),

    RUSSIAN_ENG_LETTER("Этот текст на руcском языке", "русском");
    private String text;
    private List<String> corrections;

    public String text() {
        return text;
    }

    public List<String> corrections() {
        return corrections;
    }

    private IncorrectTexts(String text, String... corrections) {
        this.text = text;
        this.corrections = Arrays.asList(corrections);
    }
}
