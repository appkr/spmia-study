package dev.appkr.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import dev.appkr.licenses.clients.OrganizationFeignClient;
import dev.appkr.licenses.config.ServiceConfig;
import dev.appkr.licenses.model.License;
import dev.appkr.licenses.model.Organization;
import dev.appkr.licenses.repository.LicenseRepository;
import dev.appkr.licenses.utils.UserContextHolder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;
    private final ServiceConfig config;

    public License getLicense(String organizationId, String licenseId) {
        final Optional<License> optional = licenseRepository.findByLicenseId(licenseId);
        License license = optional.orElseThrow(NoSuchElementException::new);

        license.setComment(config.getExampleProperty());

        final Organization organization = getOrganization(organizationId);
        license.setOrganization(organization);

        return license;
    }

    @HystrixCommand(fallbackMethod = "fallbackOrganization", threadPoolKey = "getOrganizationThreadPool")
    public Organization getOrganization(String organizationId) {
        Organization organization = Organization.builder().build();
        try {
            organization = organizationFeignClient.getOrganization(organizationId);
        } catch (FeignException e) {
            e.printStackTrace();
            throw e;
        }

        log.info("LicenseService.getOrganization Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
        randomlyLong();

        return organization;
    }

    private Organization fallbackOrganization() {
        return Organization.builder()
            .id("00000000-0000-0000-0000-000000000000")
            .contactEmail("foo@example.com")
            .contactName("Foo")
            .contactPhone("123-456-7890")
            .name("Foo")
            .build();
    }

    private void randomlyLong() {
        final int random = (int) (Math.random() * 3) + 1;
        if (random == 2) {
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        license.setComment(config.getExampleProperty());
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
    }

    public void deleteLicense(License license) {
    }
}
