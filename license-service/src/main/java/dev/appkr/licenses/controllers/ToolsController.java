package dev.appkr.licenses.controllers;

import dev.appkr.licenses.services.DiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/tools")
@RequiredArgsConstructor
public class ToolsController {

    private final DiscoveryService discoveryService;

    @GetMapping("/eureka/services")
    public List<String> getEurekaServices() {
        return discoveryService.getEurekaServices();
    }
}
