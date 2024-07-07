package com.jay.run_java_server.Controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jay.run_java_server.Basics.AES.DataSafety.TokenTypes;
import com.jay.run_java_server.Basics.Token.TokenController;
import com.jay.run_java_server.Basics.Token.KindOfTokens.ApiToken;
import com.jay.run_java_server.Interfaces.AuthenticationInterface;
import com.jay.run_java_server.Controllers.AuthenticationData.*;

@RestController
@RequestMapping("/Auth")
public class Authentication implements AuthenticationInterface {

    @Override
    public Boolean createUser(HttpHeaders header, CreateUser input) {
        ApiToken detail = TokenController.getTokenDetailsFrom(header, TokenTypes.API_TOKEN, ApiToken.class);
        String TrustKeyString = detail.TrustKeyString;

        return detail.isValid();
    }

}
