package com.traveloveapi.DTO.feedback;

import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.feedback.FeedbackEntity;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
public class FeedbackDTO {
    private String id;
    private String user_id;
    private String user_full_name;
    private String avatar;
    private String service_id;
    private String bill_id;
    private int rating;
    private String content;
    private ArrayList<String> media;
    private ArrayList<String> option_list;
    private Timestamp create_at;

    public FeedbackDTO(FeedbackEntity feedback, ArrayList<MediaEntity> media_list, UserEntity user, ArrayList<String> options) {
        id = feedback.getId();
        user_id = feedback.getUser_id();
        service_id = feedback.getRef_id();
        bill_id = feedback.getBill_id();
        rating = feedback.getRating();
        content = feedback.getContent();
        create_at = feedback.getCreate_at();

        user_full_name = user.getFull_name();
        avatar = user.getAvatar();

        media = new ArrayList<>();
        for (MediaEntity ele: media_list)
            media.add(ele.getPath());

        option_list = options;
    }
}
