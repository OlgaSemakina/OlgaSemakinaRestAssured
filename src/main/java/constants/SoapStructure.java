package constants;

public enum SoapStructure {
    ENVELOPE("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel=\"http://" +
            "speller.yandex.net/services/spellservice\">\n", "</soapenv:Envelope>"),
    HEADER("   <soapenv:Header/>\n", ""),
    BODY("   <soapenv:Body>\n", "   </soapenv:Body>\n"),
    SPEL_TEXT("         <spel:text>", "</spel:text>\n"),
    OPEN_SPEL("      <spel:", ">\n"),
    CLOSE_SPEl("      </spel:", ">\n");


    private String open;
    private String close;

    public String open() {
        return this.open;
    }

    public String close() {
        return this.close;
    }

    private SoapStructure(String open, String close) {
        this.open = open;
        this.close = close;
    }

}
