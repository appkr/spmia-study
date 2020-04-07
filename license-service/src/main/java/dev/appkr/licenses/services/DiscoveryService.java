package dev.appkr.licenses.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public List<String> getEurekaServices() {
        final ArrayList<String> services = new ArrayList<>();

        discoveryClient.getServices().forEach(serviceName -> {
            discoveryClient.getInstances(serviceName).forEach(i -> {
                services.add(String.format("%s - %s:%s", i.getServiceId(), i.getUri(), i.getPort()));
            });
        });

        return services;
    }
}
