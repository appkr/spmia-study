package dev.appkr.licenses.repository;

import dev.appkr.licenses.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LicenseRepository extends CrudRepository<License, String> {

    Optional<License> findByOrganizationIdAndLicenseId(
        @Param("organizationId") String organizationId,
        @Param("licenseId") String licenseId);
}
