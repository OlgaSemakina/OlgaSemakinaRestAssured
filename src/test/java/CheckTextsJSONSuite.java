import constants.CorrectTexts;
import constants.IncorrectTexts;
import core.YandexSpellerRestApi;
import org.testng.annotations.Test;
import schemes.YandexSpellerAnswer;

import java.util.List;

import static constants.AssertCheck.*;
import static constants.Constants.LONG_TEXT;
import static constants.CorrectTexts.URL_TEXT;
import static constants.ErrorCodes.ERROR_CAPITALIZATION;
import static constants.ErrorCodes.ERROR_REPEAT_WORD;
import static constants.IncorrectTexts.*;
import static constants.Language.*;
import static constants.Options.*;
import static core.YandexSpellerRestApi.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckTextsJSONSuite {

    @Test(description = "Check two misspelled English text")
    public void checkTwoTexts() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(MISSING_LETTER.text(), SEVERAL_MISSING_LETTERS.text())
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 2
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(2));

        // Check that the first text contains correction
        assertThat(contains(TEXT_CORRECTION, 0), answers.get(0).get(0).s, hasItem(MISSING_LETTER.
                corrections().get(0)));

        // Check that the second text contains correction
        assertThat(contains(TEXT_CORRECTION, 1), answers.get(1).get(0).s, hasItem(SEVERAL_MISSING_LETTERS.
                corrections().get(0)));

        // Check that the third text contains correction
        assertThat(contains(TEXT_CORRECTION, 1), answers.get(1).get(1).s, hasItem(SEVERAL_MISSING_LETTERS.
                corrections().get(1)));
    }

    @Test(description = "Check 3 misspelled texts with different languages and with language parameter")
    public void checkDifferentLanguages() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(IncorrectTexts.RUSSIAN.text(), IncorrectTexts.ENGLISH.text(), IncorrectTexts.UKRAINIAN.text())
                .language(RU, EN, UK)
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 3
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(3));

        // Check that the first text contains correction
        assertThat(contains(ANSWER_SIZE, 0), answers.get(0).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 0), answers.get(0).get(0).s, hasItem(IncorrectTexts.RUSSIAN.
                corrections().get(0)));

        // Check that second text contains correction
        assertThat(contains(ANSWER_SIZE, 1), answers.get(1).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 1), answers.get(1).get(0).s, hasItem(IncorrectTexts.ENGLISH.
                corrections().get(0)));

        // Check that th third text contains correction
        assertThat(contains(ANSWER_SIZE, 2), answers.get(2).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 2), answers.get(2).get(0).s, hasItem(IncorrectTexts.UKRAINIAN.
                corrections().get(0)));

    }

    // Found service bug
    @Test(description = "Check texts with mistakes matching enabled options")
    public void checkOptionsEnabled() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(DIGIT_TEXT.text(), URL_TEXT.text(), WRONG_CAPITALIZATION.text(), REPEATED_WORDS_TEXT.text())
                .language(EN)
                .options(computeOptions(IGNORE_DIGITS, IGNORE_URLS, IGNORE_CAPITALIZATION, FIND_REPEAT_WORDS))
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 4
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(4));

        // Check that first three texts don't contain error
        assertThat(contains(ANSWER_SIZE, 0), answers.get(0).size(), equalTo(0));
        assertThat(contains(ANSWER_SIZE, 1), answers.get(1).size(), equalTo(0));
        assertThat(contains(ANSWER_SIZE, 2), answers.get(2).size(), equalTo(0));

        // Check that the fourth text contains correction
        assertThat(contains(ANSWER_SIZE, 3), answers.get(3).size(), equalTo(1));
        assertThat(contains(CODE, 3), answers.get(3).get(0).code, equalTo(ERROR_REPEAT_WORD));
        assertThat(contains(TEXT_CORRECTION, 3), answers.get(3).get(0).s, hasItem(REPEATED_WORDS_TEXT.
                corrections().get(0)));
    }

    // Found service bug
    @Test(description = "Check texts with mistakes matching disabled options")
    public void checkOptionsDisabled() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(DIGIT_TEXT.text(), URL_TEXT.text(), WRONG_CAPITALIZATION.text(), REPEATED_WORDS_TEXT.text())
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 4
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(4));

        // Check that the first text contains correction
        assertThat(contains(ANSWER_SIZE, 0), answers.get(0).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 0), answers.get(0).get(0).s, hasItem(DIGIT_TEXT.
                corrections().get(0)));

        // Check that the second text doesn't contain correction
        assertThat(contains(ANSWER_SIZE, 1), answers.get(1).size(), equalTo(0));

        // Check that the third text contains correction
        assertThat(contains(ANSWER_SIZE, 2), answers.get(2).size(), equalTo(1));
        assertThat(contains(CODE, 2), answers.get(2).get(0).code, equalTo(ERROR_CAPITALIZATION));
        assertThat(contains(TEXT_CORRECTION, 2), answers.get(2).get(0).s, hasItem(WRONG_CAPITALIZATION.
                corrections().get(0)));

        // Check that the third text doesn't contain correction
        assertThat(contains(ANSWER_SIZE, 0), answers.get(3).size(), equalTo(0));

    }

    // Found service bug
    @Test(description = "Check incorrect English and Ukrainian texts with only UK language parameter")
    public void checkWrongEngTextWithUKlangParams() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(IncorrectTexts.ENGLISH.text(), IncorrectTexts.UKRAINIAN.text())
                .language(UK)
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 2
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(2));

        // Check that no errors are found in the first text
        assertThat(contains(ANSWER_SIZE, 0), answers.get(0).size(), equalTo(0));

        // Check that errors are found in the second text
        assertThat(contains(ANSWER_SIZE, 1), answers.get(1).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 1), answers.get(1).get(0).s, hasItem(IncorrectTexts.UKRAINIAN.
                corrections().get(0)));

    }

    // Found service bug
    @Test(description = "Check incorrect English and Russian texts with only RU language parameter")
    public void checkWrongEngTextWithRUlangParams() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(IncorrectTexts.ENGLISH.text(), IncorrectTexts.RUSSIAN.text())
                .language(RU)
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 2
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(2));

        // Check that no errors are found in the first text
        assertThat(contains(ANSWER_SIZE, 0), answers.get(0).size(), equalTo(0));

        // Check that errors are found in the second text
        assertThat(contains(ANSWER_SIZE, 1), answers.get(1).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 1), answers.get(1).get(0).s, hasItem(IncorrectTexts.RUSSIAN.
                corrections().get(0)));

    }

    @Test(description = "Check Russian text with English letters in it")
    public void checkRusTextWithEngLetters() {
        List<List<YandexSpellerAnswer>> answers = YandexSpellerRestApi.getYandexSpellerAnswers(with()
                .text(CorrectTexts.RUSSIAN.text(), IncorrectTexts.RUSSIAN_ENG_LETTER.text())
                .language(RU)
                .callApi()
                .then()
                .specification(successResponse())
                .extract().response());

        // Check that answers size is 2
        assertThat(ANSWERS_SIZE.text(), answers.size(), equalTo(2));

        // Check that no errors are found in the first text
        assertThat(contains(ANSWER_SIZE, 0), answers.get(0).size(), equalTo(0));

        // Check that errors are found in the second text
        assertThat(contains(ANSWER_SIZE, 1), answers.get(1).size(), equalTo(1));
        assertThat(contains(TEXT_CORRECTION, 1), answers.get(1).get(0).s, hasItem(IncorrectTexts.
                RUSSIAN_ENG_LETTER.corrections().get(0)));
    }

    @Test(description = "POST request exceeding max length of text")
    public void checkTooLongPostRequest() {
        with()
                .text(LONG_TEXT)
                .callPostApi()
                .then()
                .specification(failedResponse());
    }

}

