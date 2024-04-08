package com.traveloveapi.entity.report;

import com.traveloveapi.constrain.Platform;
import com.traveloveapi.constrain.ReportStatus;
import com.traveloveapi.constrain.ReportType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "report")
public class ReportEntity {
    @Id
    private String id;

    private String reporter_id;

    private Timestamp create_at;

    private Timestamp update_at;

    private ReportStatus status;

    private String content;

    private String tour_id;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private ReportType type;
}
