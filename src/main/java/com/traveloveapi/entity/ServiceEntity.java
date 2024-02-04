package com.traveloveapi.entity;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.constrain.ServiceType;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.hibernate.annotations.Filter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.HighlightProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Data
@Entity
@Table(name="service")
public class ServiceEntity {
    @Id
    private String id;

    private String title;

    private String service_owner;

    private float rating;

    private int vote_quantity;

    private int sold;

    private String thumbnail;

    private String city_id;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    private float min_price;
}
