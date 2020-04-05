package dev.appkr.licenses.services;

import dev.appkr.licenses.model.License;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

    public License getLicense(String licenseId) {
        return License.builder().build();
    }

    public void createLicense(License license) {

    }

    public void updateLicense(License license) {

    }

    public void deleteLicense(License license) {

    }
}
