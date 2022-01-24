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

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
