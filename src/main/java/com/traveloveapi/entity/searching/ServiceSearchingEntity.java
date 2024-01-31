package com.traveloveapi.entity.searching;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Data
@Indexed
@Table(name = "service_searching")
public class ServiceSearchingEntity {
    @Id
    private String service_id;

    @FullTextField
    private String title;

    private String thumbnail;

    private float min_price;

    private String city_name;
}
