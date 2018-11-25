package core;

import constants.Language;
import constants.Options;
import constants.SoapAction;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static constants.Constants.*;
import static constants.SoapStructure.*;

public class YandexSpellerSOAP {

    public static final String YANDEX_SPELLER_SOAP_URI = "http://speller.yandex.net/services/spellservice";

    private YandexSpellerSOAP() {
    }

    private HashMap<String, String> params = new HashMap<>();
    private SoapAction action = SoapAction.CHECK_TEXT;

    public static class SOAPBuilder {
        YandexSpellerSOAP soapReq;

        private SOAPBuilder(YandexSpellerSOAP soap) {
            soapReq = soap;
        }

        public SOAPBuilder action(SoapAction action) {
            soapReq.action = action;
            return this;
        }

        public SOAPBuilder text(String text) {
            soapReq.params.put(PARAM_TEXT, text);
            return this;
        }

        public SOAPBuilder options(String options) {
            soapReq.params.put(PARAM_OPTIONS, options);
            return this;
        }

        public SOAPBuilder language(Language language) {
            soapReq.params.put(PARAM_LANG, language.langCode());
            return this;
        }

        public Response callSOAP() {
            String soapBody = ENVELOPE.open() +
                    HEADER.open() +
                    BODY.open() +
                    OPEN_SPEL.open() +
                    soapReq.action.reqName() + " " + PARAM_LANG + "=" + QUOTES + (
                    soapReq.params.getOrDefault(PARAM_LANG, Language.EN.langCode()))
                    + QUOTES + " " + PARAM_OPTIONS + "=" + QUOTES + (
                    soapReq.params.getOrDefault(PARAM_OPTIONS, Options.computeOptions()))
                    + QUOTES
                    + " " + PARAM_FORMAT + "=" + OPEN_SPEL.close() +
                    SPEL_TEXT.open() + (
                    soapReq.params.getOrDefault(PARAM_TEXT, ""))
                    + SPEL_TEXT.close() +
                    CLOSE_SPEl.open() +
                    soapReq.action.reqName() + CLOSE_SPEl.close() +
                    BODY.close() +
                    ENVELOPE.close();


            return RestAssured.with()
                    .spec(spellerSOAPreqSpec())
                    .header("SOAPAction", YANDEX_SPELLER_SOAP_URI + "/" + soapReq.action.method())
                    .body(soapBody)
                    .log().all()
                    .with()
                    .post().prettyPeek();
        }
    }

    public static SOAPBuilder with() {
        YandexSpellerSOAP soap = new YandexSpellerSOAP();
        return new SOAPBuilder(soap);
    }

    public static RequestSpecification spellerSOAPreqSpec() {
        return new RequestSpecBuilder()
                .setContentType("text/xml;charset=UTF-8")
                .addHeader("Host", "speller.yandex.net")
                .setBaseUri(YANDEX_SPELLER_SOAP_URI)
                .build();
    }

}
