package com.traveloveapi.service.feedback;

import com.traveloveapi.DTO.feedback.FeedbackDTO;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.feedback.FeedbackEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.FeedbackRepository;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.repository.service_package.BillRepository;
import com.traveloveapi.service.BillService;
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
    final private BillRepository billRepository;
    final private UserService userService;

    public FeedbackEntity createFeedback(int rating, String content, String bill_id, MultipartFile[] files) {
        BillEntity bill = billRepository.find(bill_id);
        if (bill.getStatus()!= BillStatus.PAID)
            throw new CustomException("Bill have not paid yet", 400);
        if (!bill.getUser_id().equals(SecurityContext.getUserID()) && !userService.isAdmin())
            throw new ForbiddenException();
        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setId(UUID.randomUUID().toString());
        feedback.setRef_id(bill.getService_id());
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
            media.setPath(s3FileService.uploadFile(file, "public/service/" + feedback.getRef_id() + "/feedback/", id));
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

    public FeedbackDTO getFeedback(FeedbackEntity feedback) {
        ArrayList<MediaEntity> media = mediaRepository.find(feedback.getId(), "FEEDBACK");
        UserEntity user = userRepository.find(feedback.getUser_id());
        return new FeedbackDTO(feedback, media, user);
    }
    public ArrayList<FeedbackDTO> getByTour(String tour_id) {
        ArrayList<FeedbackEntity> feedback_list = feedbackRepository.getByTour(tour_id);
        if (feedback_list.isEmpty())
            return new ArrayList<>();
        ArrayList<FeedbackDTO> rs = new ArrayList<>();
        for (FeedbackEntity ele: feedback_list)
            rs.add(getFeedback(ele));
        return rs;
    }
}
