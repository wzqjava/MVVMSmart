package com.wzq.mvvmsmart.net.token;

/**
 * created 王志强 2020.04.30
 * 和服务器约定的TokenBena(不同公司字段略有差异,任意修改即可)
 */
public class TokenBean {

    /**
     * accessToken : ccc859e6-45ce-4df9-9500-e6de1e4bb6d2
     * expired : null
     * accessTokenExpiration : null
     * accessTokenExpiresIn : 3599
     * scope : null
     * tokenType : bearer
     * refreshTokenExpiration : null
     * refreshToken : 8c247745-4366-4cf6-a6ff-9039248448f4
     * expirationTime : 1576756867
     */

    private String accessToken;
    private String expired;
    private String accessTokenExpiration;
    private int accessTokenExpiresIn;
    private String scope;
    private String tokenType;
    private String refreshTokenExpiration;
    private String refreshToken;
    private int expirationTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(String accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public int getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public void setAccessTokenExpiresIn(int accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(String refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "accessToken='" + accessToken + '\'' +
                ", expired='" + expired + '\'' +
                ", accessTokenExpiration='" + accessTokenExpiration + '\'' +
                ", accessTokenExpiresIn=" + accessTokenExpiresIn +
                ", scope='" + scope + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshTokenExpiration='" + refreshTokenExpiration + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
