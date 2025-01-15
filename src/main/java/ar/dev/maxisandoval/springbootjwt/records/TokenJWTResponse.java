package ar.dev.maxisandoval.springbootjwt.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenJWTResponse(@JsonProperty("access_token") String accessToken,
                               @JsonProperty("refresh_token") String refreshToken) {
}