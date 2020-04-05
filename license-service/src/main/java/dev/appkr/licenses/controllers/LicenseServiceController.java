package dev.appkr.licenses.controllers;

import dev.appkr.licenses.model.License;
import dev.appkr.licenses.services.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
public class LicenseServiceController {

    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(organizationId, licenseId);
    }

    @PostMapping("/{licenseId}")
    public String createLicense(@RequestBody License license) {
        licenseService.createLicense(license);
        return "Success";
    }

    @PutMapping("/{licenseId}")
    public String updateLicense(@PathVariable("licenseId") String licenseId) {
        return "This is the put";
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String deleteLicense(@PathVariable("licenseId") String licenseId) {
        return "This is the delete";
    }
}
