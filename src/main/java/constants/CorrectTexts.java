package constants;

public enum CorrectTexts {
    RUSSIAN("Этот текст на русском языке"),
    URL_TEXT("This text contains URL http://yandex.ru");
    private String text;

    public String text() {
        return text;
    }

    private CorrectTexts(String text) {
        this.text = text;
    }
}