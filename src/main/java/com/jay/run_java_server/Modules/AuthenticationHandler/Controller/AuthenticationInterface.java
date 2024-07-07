package com.jay.run_java_server.Modules.AuthenticationHandler.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.jay.run_java_server.Modules.AuthenticationHandler.Data.*;

public interface AuthenticationInterface {

    @PostMapping("/CreateUser")
    ResponseEntity<Boolean> createUser(@RequestHeader HttpHeaders header, @RequestBody CreateUser input);

}
