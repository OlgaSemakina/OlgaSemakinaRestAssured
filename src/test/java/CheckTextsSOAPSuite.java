import constants.IncorrectTexts;
import core.YandexSpellerSOAP;
import org.testng.annotations.Test;

import java.util.Collections;

import static constants.Constants.SPACE;
import static constants.CorrectTexts.URL_TEXT;
import static constants.IncorrectTexts.*;
import static constants.Language.RU;
import static constants.Language.UK;
import static constants.Options.*;
import static org.hamcrest.Matchers.*;

public class CheckTextsSOAPSuite {

    @Test(description = "Check misspelled Russian text")
    public void checkRULang() {
        YandexSpellerSOAP
                .with()
                .text(IncorrectTexts.RUSSIAN.text())
                .language(RU)
                .callSOAP()
                .then()
                .body(stringContainsInOrder
                        (Collections.singleton(IncorrectTexts.RUSSIAN.corrections().get(0))));
    }

    @Test(description = "Check misspelled English text")
    public void checkENLang() {
        YandexSpellerSOAP
                .with()
                .text(IncorrectTexts.ENGLISH.text())
                .callSOAP()
                .then()
                .body(stringContainsInOrder
                        (Collections.singleton(IncorrectTexts.ENGLISH.corrections().get(0))));
    }

    @Test(description = "Check misspelled Ukrainian text")
    public void checkUKLang() {
        YandexSpellerSOAP
                .with()
                .text(IncorrectTexts.UKRAINIAN.text())
                .language(UK)
                .callSOAP()
                .then()
                .body(stringContainsInOrder
                        (Collections.singleton(IncorrectTexts.UKRAINIAN.corrections().get(0))));
    }

    @Test(description = "Check texts with mistakes matching disabled options")
    public void checkOptions() {
        YandexSpellerSOAP
                .with()
                .text(DIGIT_TEXT.text() + SPACE + URL_TEXT.text() + SPACE + WRONG_CAPITALIZATION.text() + SPACE +
                        REPEATED_WORDS_TEXT.text())
                .options(computeOptions(IGNORE_DIGITS, IGNORE_URLS, IGNORE_CAPITALIZATION, FIND_REPEAT_WORDS))
                .callSOAP()
                .then()
                .body(stringContainsInOrder
                        (Collections.singleton(REPEATED_WORDS_TEXT.corrections().get(0))))
                .body(not(containsInAnyOrder(WRONG_CAPITALIZATION.corrections().get(0),
                        DIGIT_TEXT.corrections().get(0), URL_TEXT.text())));
    }

}
