package com.jay.run_java_server.Basics.Token.KindOfTokens;

import com.jay.run_java_server.Basics.AES.DataSafety;

public class ApiToken {
    public String TrustKeyString;

    public Boolean isValid() {
        if (TrustKeyString.equals(DataSafety.TrustKeyString)) {
            return true;
        }
        return false;
    }
}