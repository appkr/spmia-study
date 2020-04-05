package dev.appkr.licenses.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class License {

    private String id;
    private String organizationId;
    private String productName;
    private String licenseType;

    @Builder
    public License(String id, String organizationId, String productName, String licenseType) {
        this.id = id;
        this.organizationId = organizationId;
        this.productName = productName;
        this.licenseType = licenseType;
    }
}
