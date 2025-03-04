package com.service.a;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AController {

    private final BServiceClient bServiceClient;
    @GetMapping("/hi")
    public String hi(){
        String hello = bServiceClient.getHello();
        return "service-a : hi ###### service-b: hello";
    }
}
