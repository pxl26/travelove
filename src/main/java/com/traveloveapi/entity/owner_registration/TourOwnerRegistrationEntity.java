package com.traveloveapi.entity.owner_registration;

import com.traveloveapi.constrain.OwnerRegistrationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "owner_registration")
public class TourOwnerRegistrationEntity {
    @Id
    private String id;

    private String name;

    private String email;

    private String phone;

    private String office_address;

    private String referral_code;

    private Timestamp create_at;

    private Timestamp update_at;

    @Enumerated(EnumType.STRING)
    private OwnerRegistrationStatus status;

    private String tax_code;

    private String business_license;
}
