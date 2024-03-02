package com.traveloveapi.service.feedback;

import com.traveloveapi.DTO.feedback.FeedbackDTO;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.feedback.FeedbackEntity;
import com.traveloveapi.repository.FeedbackRepository;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    final private FeedbackRepository feedbackRepository;
    final private S3FileService s3FileService;
    final private MediaRepository mediaRepository;
    final private UserRepository userRepository;

    public FeedbackEntity createFeedback(String ref_id, int rating, String content, String bill_id, MultipartFile[] files) {
        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setId(UUID.randomUUID().toString());
        feedback.setRef_id(ref_id);
        feedback.setUser_id(SecurityContext.getUserID());
        feedback.setRating(rating);
        feedback.setContent(content);
        feedback.setBill_id(bill_id);
        feedback.setCreate_at(new Timestamp(System.currentTimeMillis()));
        ArrayList<MediaEntity> media_list = new ArrayList<>();
        int k=0;
        for (MultipartFile file: files) {
            MediaEntity media = new MediaEntity();
            String id = UUID.randomUUID().toString();
            media.setId(id);
            media.setType("FEEDBACK");
            media.setRef_id(feedback.getId());
            media.setSeq(k++);
            media.setPath(s3FileService.uploadFile(file, "public/service/" + ref_id + "/feedback/", id));
            media_list.add(media);
            mediaRepository.save(media);
        }
        feedbackRepository.save(feedback);
        return feedback;
    }

    public FeedbackDTO getFeedback(String id) {
        FeedbackEntity feedback = feedbackRepository.find(id);
        ArrayList<MediaEntity> media = mediaRepository.find(feedback.getId(), "FEEDBACK");
        UserEntity user = userRepository.find(feedback.getUser_id());
        return new FeedbackDTO(feedback, media, user);
    }
}
