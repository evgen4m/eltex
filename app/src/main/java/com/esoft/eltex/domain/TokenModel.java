package com.esoft.eltex.domain;

import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @SerializedName("access_token")
    private String token = null;

    @SerializedName("token_type")
    private String tokenType = null;

    @SerializedName("refresh_token")
    private String refreshToken = null;

    @SerializedName("expires_in")
    private Long expiresIn = null;

    @SerializedName("scope")
    private String scope = null;

    public String getToken() { return token; }

    public String getTokenType() {
        return tokenType;
    }
}
