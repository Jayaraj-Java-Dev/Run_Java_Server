package com.jay.run_java_server.Basics.Token;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.jay.run_java_server.Basics.AES.DataSafety;
import com.jay.run_java_server.Basics.AES.DataSafety.TokenTypes;

public class TokenController {

    public static <T> T getTokenDetailsFrom(HttpHeaders headers, TokenTypes tokenType, Class<T> to_clazz) {
        return getTokenDetailsFrom(headers, tokenType, to_clazz, true);
    }

    public static <T> T getTokenDetailsFrom(HttpHeaders headers, TokenTypes tokenType, Class<T> to_clazz,
            Boolean checkExpiry) {
        TokenDetails detail = parseToken(headers, tokenType, false);

        if (detail == null || detail.isValid == false) {
            return null;
        }

        if (checkExpiry && detail.isExpired()) {
            return null;
        }

        return detail.parseDataAs(to_clazz);
    }

    public static TokenDetails parseToken(HttpHeaders headers, TokenTypes tokenType) {
        return parseToken(headers, tokenType, true);
    }

    public static TokenDetails parseToken(HttpHeaders headers, TokenTypes tokenType, Boolean checkExpiry) {
        if (headers == null || tokenType == null || tokenType == null) {
            return null;
        }

        // Retrieve the list of tokens associated with the tokenName
        List<String> tokens = headers.get(DataSafety.KeysAndIvs.getNameOf(tokenType));
        if (tokens == null || tokens.isEmpty()) {
            return null; // Return null if the list of tokens is null or empty
        }

        // Get the first token from the list
        String token = tokens.getFirst();

        // Check if the token is null or empty after trimming whitespace
        if (token == null || token.strip().isEmpty()) {
            return null;
        }

        TokenDetails tokenDetail = new TokenDetails(token, tokenType);

        if (checkExpiry && tokenDetail.isExpired()) {
            return null;
        }

        return tokenDetail;
    }

    // public static TokenDetails createToken(){
    // TokenDetails(, Integer MinutesForExpiration, TokenTypes encryptionLevel)
    // }
}
