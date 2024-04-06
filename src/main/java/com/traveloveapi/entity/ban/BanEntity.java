package com.traveloveapi.entity.ban;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "ban")
@Data
public class BanEntity {
    @Id
    private String id;

    private String owner_id;

    private String reason;

    private String note;

    private String unban_note;

    private Timestamp ban_at;

    private Timestamp unban_at;
}
