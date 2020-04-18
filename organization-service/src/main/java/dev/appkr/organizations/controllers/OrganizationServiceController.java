package dev.appkr.organizations.controllers;

import dev.appkr.organizations.model.Organization;
import dev.appkr.organizations.services.OrganizationService;
import dev.appkr.organizations.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/organizations")
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceController {

    private final OrganizationService orgService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        log.info("OrganizationServiceController.getOrganization traceId: {}", MDC.get(UserContext.TRACE_ID));
        return orgService.getOrg(organizationId);
    }
}
