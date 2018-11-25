package constants;

public enum SoapAction {
    CHECK_TEXT("checkText", "CheckTextRequest"),
    CHECK_TEXTS("checkTexts", "CheckTextsRequest");

    private String method;
    private String reqName;

    public String method() {
        return this.method;
    }

    public String reqName() {
        return this.reqName;
    }

    private SoapAction(String action, String reqName) {
        this.method = action;
        this.reqName = reqName;
    }
}