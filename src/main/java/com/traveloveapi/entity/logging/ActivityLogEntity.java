package com.traveloveapi.entity.logging;

import com.traveloveapi.constrain.UserAction;
import jakarta.persistence.*;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Entity
@Table(name = "activity_log")
@Data
public class ActivityLogEntity {
    @Id
    private String id;
    private String user_id;
//    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Enumerated(EnumType.STRING)
    private UserAction action;
    private String ref_action;
    private String note;
}
