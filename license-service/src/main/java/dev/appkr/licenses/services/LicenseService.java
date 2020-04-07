package dev.appkr.licenses.services;

import dev.appkr.licenses.clients.OrganizationFeignClient;
import dev.appkr.licenses.config.ServiceConfig;
import dev.appkr.licenses.model.License;
import dev.appkr.licenses.model.Organization;
import dev.appkr.licenses.repository.LicenseRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;
    private final ServiceConfig config;

    public License getLicense(String organizationId, String licenseId) {
        final Optional<License> optional = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        License license = optional.orElseThrow(NoSuchElementException::new);

        license.setComment(config.getExampleProperty());

        Organization organization = Organization.builder().build();
        try {
            organization = organizationFeignClient.getOrganization(organizationId);
        } catch (FeignException e) {
            e.printStackTrace();
            throw e;
        }

        license.setOrganization(organization);

        return license;
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
