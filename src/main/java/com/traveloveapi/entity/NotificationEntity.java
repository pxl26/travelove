package com.traveloveapi.entity;

import com.traveloveapi.constrain.voucher.NotificationType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "notification")
public class NotificationEntity {
    @Id
    private String id;

    private String consumer_id;

    private String content;

    private String image;

    private String url;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Timestamp create_at;

    private Timestamp read_at;
}
