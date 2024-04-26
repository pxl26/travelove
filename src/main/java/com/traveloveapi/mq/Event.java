package com.traveloveapi.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String factor_name;
    private String type;
    private String url;
    private Timestamp create_at;
}
