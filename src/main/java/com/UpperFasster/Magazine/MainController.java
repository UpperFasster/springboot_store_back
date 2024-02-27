package com.UpperFasster.Magazine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MainController {

    @Value("${server.address}")
    private String address;
    @Value("${server.port}")
    private int port;
    @GetMapping("/")
    public RedirectView redirectView(RedirectAttributes attributes) {
        return new RedirectView(String.format("http://%s:%d/swagger-ui/index.html", address, port));
    }
}
