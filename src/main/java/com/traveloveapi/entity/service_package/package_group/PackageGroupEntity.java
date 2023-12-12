package com.traveloveapi.entity.service_package.package_group;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "package_group")
@IdClass(PackageGroupId.class)
public class PackageGroupEntity {
    @Id
    private String service_id;
    @Id
    private int group_number;
    private String title;
    @Column(name = "`limit`")
    private Integer limit;
    private Integer limit_special;
}
