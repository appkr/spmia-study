package dev.appkr.licenses.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "licenses")
public class License {

    @Id
    @Column(name = "license_id", nullable = false, length = 36)
    private String licenseId;

    @Column(name = "organization_id", nullable = false, length = 36)
    private String organizationId;

    @Transient
    private Organization organization;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;

    @Column(name = "license_allocated", nullable = false)
    private Integer licenseAllocated;

    @Column(name="comment")
    private String comment;
}
