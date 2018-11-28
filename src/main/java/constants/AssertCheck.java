package constants;

import static constants.Constants.HASH;

public enum AssertCheck {
    ANSWERS_SIZE("Answers size is wrong"), ANSWER_SIZE(" answer size is wrong"),
    TEXT_CORRECTION(" text should contain correction"), CODE(" text wrong error code");

    private String text;

    private AssertCheck(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

    public static String contains(AssertCheck check, int order) {
        return HASH + (order + 1) + check.text();
    }

}
