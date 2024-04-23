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

    private String company_name;

    private String email;

    private String phone;

    private String address;

    private Timestamp create_at;

    private Timestamp update_at;

    @Enumerated(EnumType.STRING)
    private OwnerRegistrationStatus status;

    private String tax_code;

    private String business_registration;

    private String job_title;

    private String contact_name;

    private String insurance_policy;

    private String contact_language;
}
