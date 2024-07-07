package com.jay.run_java_server.Basics.Token.KindOfTokens;

public class ApiToken {
    public String TrustKeyString;

    public Boolean isValid() {
        if (TrustKeyString.equals("java_run_api_key_string")) {
            return true;
        }
        return false;
    }
}