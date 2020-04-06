package dev.appkr.organizations.services;

import dev.appkr.organizations.model.Organization;
import dev.appkr.organizations.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository orgRepository;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId).orElseThrow(NoSuchElementException::new);
    }
}
