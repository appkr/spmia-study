package dev.appkr.licenses.services;

import dev.appkr.licenses.config.ServiceConfig;
import dev.appkr.licenses.model.License;
import dev.appkr.licenses.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;

    public License getLicense(String organizationId, String licenseId) {
        final Optional<License> optional = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        final License license = optional.orElseThrow(NoSuchElementException::new);
        license.setComment(config.getExampleProperty());
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
