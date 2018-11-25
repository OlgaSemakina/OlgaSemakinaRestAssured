package core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Language;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import schemes.YandexSpellerAnswer;

import java.util.*;
import java.util.stream.Collectors;

import static constants.Constants.*;
import static org.hamcrest.Matchers.lessThan;

public class YandexSpellerRestApi {

    public static final String YANDEX_SPELLER_API_URI = "https://speller.yandex.net/services/spellservice.json/checkTexts";

    private YandexSpellerRestApi() {
    }

    private HashMap<String, List<String>> params = new HashMap<>();

    public static class ApiBuilder {
        YandexSpellerRestApi spellerApi;

        private ApiBuilder(YandexSpellerRestApi gcApi) {
            spellerApi = gcApi;
        }

        public ApiBuilder text(String... text) {
            spellerApi.params.put(PARAM_TEXT, Arrays.asList(text));
            return this;
        }

        public ApiBuilder options(String options) {
            spellerApi.params.put(PARAM_OPTIONS, Collections.singletonList(options));
            return this;
        }

        public ApiBuilder language(Language... language) {
            spellerApi.params.put(PARAM_LANG,
                    Arrays.stream(language).map(Language::langCode).collect(Collectors.toList()));
            return this;
        }

        public Response callApi() {
            return RestAssured
                    .given(baseRequestConfiguration())
                    .queryParams(spellerApi.params)
                    .log().all()
                    .get()
                    .prettyPeek();
        }

        public Response callPostApi() {
            return RestAssured
                    .given(baseRequestConfiguration())
                    .formParams(spellerApi.params)
                    .log().all()
                    .post()
                    .prettyPeek();
        }
    }

    public static ApiBuilder with() {
        YandexSpellerRestApi api = new YandexSpellerRestApi();
        return new ApiBuilder(api);
    }


    // Get ready Speller answers list form api response
    public static List<List<YandexSpellerAnswer>> getYandexSpellerAnswers(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<List<YandexSpellerAnswer>>>() {
        }.getType());
    }

    // Set base request and response specifications to use in tests
    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .setRelaxedHTTPSValidation()
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(YANDEX_SPELLER_API_URI)
                .build();
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification failedResponse() {
        return new ResponseSpecBuilder()
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_GATEWAY_TIMEOUT)
                .build();
    }

}
