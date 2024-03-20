package com.traveloveapi.entity.service_package.option_special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OptionSpecialId implements Serializable {
    private String service_id;
    private int group_number;
    private int option_number;
}
