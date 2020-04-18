package dev.appkr.licenses.controllers;

import dev.appkr.licenses.model.License;
import dev.appkr.licenses.services.LicenseService;
import dev.appkr.licenses.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
@Slf4j
public class LicenseServiceController {

    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {
        log.info("LicenseServiceController.getLicense traceId: {}",
                MDC.get(UserContext.TRACE_ID));

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
