package com.traveloveapi.entity.feedback;

import com.traveloveapi.entity.MediaEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Data
@Table(name = "feedback")
public class FeedbackEntity {
    @Id
    private String id;

    private String ref_id;

    private String user_id;

    private int rating;

    private  String content;

    private String bill_id;

    private Timestamp create_at;
}
