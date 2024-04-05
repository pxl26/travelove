package com.traveloveapi.entity.report;

import com.traveloveapi.constrain.ReportStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
