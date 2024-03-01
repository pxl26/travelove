package com.traveloveapi.entity.searching;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.SearchingType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Data
@Indexed
@Table(name = "searching")
public class SearchingEntity {
    @Id
    private String ref_id;

    @FullTextField
    private String data;

    private String title;

    private String thumbnail;

    private float min_price;

    private String city_name;

    private String country_name;

    private Currency currency;

    @Enumerated(EnumType.STRING)
    private SearchingType type;
}
