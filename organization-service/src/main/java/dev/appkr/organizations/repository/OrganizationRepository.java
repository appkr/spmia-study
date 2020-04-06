package dev.appkr.organizations.repository;

import dev.appkr.organizations.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
