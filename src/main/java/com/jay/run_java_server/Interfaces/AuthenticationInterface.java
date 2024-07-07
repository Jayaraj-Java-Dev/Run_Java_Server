package com.jay.run_java_server.Interfaces;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.http.HttpHeaders;
import com.jay.run_java_server.Controllers.AuthenticationData.*;

public interface AuthenticationInterface {

    @PostMapping("/CreateUser")
    Boolean createUser(@RequestHeader HttpHeaders header, @RequestBody CreateUser input);

}
