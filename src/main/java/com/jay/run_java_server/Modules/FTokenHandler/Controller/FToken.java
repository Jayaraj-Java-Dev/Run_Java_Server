package com.jay.run_java_server.Modules.FTokenHandler;

import java.util.*;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jay.run_java_server.Basics.AES.DataSafety;
import com.jay.run_java_server.Basics.AES.DataSafety.TokenTypes;
import com.jay.run_java_server.Basics.Token.KindOfTokens.ApiToken;
import com.jay.run_java_server.Basics.Token.TokenDetails;

@RestController
@RequestMapping("/FToken")
public class FToken implements FTokenInterface {

    @Override
    public String getApiToken(HttpHeaders header) {
        if (header == null)
            return null;

        List<String> encKeys = header.get("TrustKey");

        if (encKeys == null || encKeys.size() == 0) {
            return null;
        }

        String encKey = encKeys.getFirst();

        String key = DataSafety.decrypt(encKey, TokenTypes.ONLY_FOR_GET_API_KEY);

        if (key.equals(DataSafety.TrustKeyString)) {
            ApiToken token = new ApiToken();
            token.TrustKeyString = DataSafety.TrustKeyString;
            TokenDetails detail = new TokenDetails(token, 1, TokenTypes.API_TOKEN);
            return detail.encrypt();
        } else {
            return null;
        }
    }

    @Override
    public String getTrustKey() {
        return DataSafety.encrypt(DataSafety.TrustKeyString, TokenTypes.ONLY_FOR_GET_API_KEY);
    }

}
