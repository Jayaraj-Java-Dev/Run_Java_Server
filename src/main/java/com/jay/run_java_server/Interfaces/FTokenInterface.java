package com.jay.run_java_server.Interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;

public interface FTokenInterface {

    @GetMapping("/getApiToken")
    String getApiToken(@RequestHeader HttpHeaders header);

    @GetMapping("/getTrustKey")
    String getTrustKey();

}
