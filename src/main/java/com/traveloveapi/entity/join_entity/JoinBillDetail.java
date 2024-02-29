package com.traveloveapi.entity.join_entity;

import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import lombok.Data;

@Data
public class JoinBillDetail {
    private BillEntity bill;
    private ServiceEntity tour;
    private BillDetailPersonTypeEntity person;
    private PackageOptionEntity option;
    private PackageGroupEntity group;
}
