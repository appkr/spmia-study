package dev.appkr.licenses.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Organization {

    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;

    @Builder
    public Organization(String id, String name, String contactName, String contactEmail, String contactPhone) {
        this.id = id;
        this.name = name;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}
