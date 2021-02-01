package com.backend.ayBank.securiy;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 900000000; // token will expire after 1000000 second
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user";
    public static final String TOKEN_SECRET = "youssefabbadi97";
    //token (Header payload signature (secret ket))

}
