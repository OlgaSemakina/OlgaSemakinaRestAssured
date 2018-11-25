package constants;

public enum ErrorCodes {
    ERROR_UNKNOWN_WORD("1"), ERROR_REPEAT_WORD("2"), ERROR_CAPITALIZATION("3"), ERROR_TOO_MANY_ERRORS("4");

    private String code;

    public String getCode() {
        return code;
    }

    private ErrorCodes(String code) {
        this.code = code;
    }
}
