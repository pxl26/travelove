package com.traveloveapi.service.feedback;

import com.traveloveapi.DTO.feedback.FeedbackDTO;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.feedback.FeedbackEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.FeedbackRepository;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.redis.RedisService;
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
    final private ServiceRepository serviceRepository;
    final private RedisService redisService;

    public FeedbackEntity createFeedback(int rating, String content, String bill_id, MultipartFile[] files) {
        BillEntity bill = billRepository.find(bill_id);
        if (bill.getFeedback_id()!=null)
            throw new CustomException("Have feedback yet", 400);
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
        feedback.setHas_media(files!=null);
        ArrayList<MediaEntity> media_list = new ArrayList<>();
        int k=0;
        if (files!=null)
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
        ServiceEntity tour = serviceRepository.find(feedback.getRef_id());
        tour.setRating((tour.getRating()*tour.getVote_quantity() + feedback.getRating())/(tour.getVote_quantity()+1));
        tour.setVote_quantity(tour.getVote_quantity()+1);

        bill.setFeedback_id(feedback.getId());

        feedbackRepository.save(feedback);
        billRepository.update(bill);
        serviceRepository.update(tour);

        //-----EVICT TOUR DATA FROM REDIS-------

        redisService.getConnection().del("tour_detail:"+tour.getId());
        redisService.getConnection().del("tour_card:"+tour.getId());
        return feedback;
    }

    public FeedbackDTO getFeedback(String id) {
        FeedbackEntity feedback = feedbackRepository.find(id);
        ArrayList<MediaEntity> media = mediaRepository.find(feedback.getId(), "FEEDBACK");
        UserEntity user = userRepository.find(feedback.getUser_id());
        return new FeedbackDTO(feedback, media, user, billRepository.getOptionInBill(feedback.getBill_id()));
    }

    public FeedbackDTO getFeedback(FeedbackEntity feedback) {
        ArrayList<MediaEntity> media = mediaRepository.find(feedback.getId(), "FEEDBACK");
        UserEntity user = userRepository.find(feedback.getUser_id());

        return new FeedbackDTO(feedback, media, user, billRepository.getOptionInBill(feedback.getBill_id()));
    }
    public ArrayList<FeedbackDTO> getByTour(String tour_id, Integer from, Integer to, int page) {
        ArrayList<FeedbackEntity> feedback_list = feedbackRepository.getByTour(tour_id, from, to, page);
        if (feedback_list.isEmpty())
            return new ArrayList<>();
        ArrayList<FeedbackDTO> rs = new ArrayList<>();
        for (FeedbackEntity ele: feedback_list)
            rs.add(getFeedback(ele));
        return rs;
    }

    public ArrayList<FeedbackDTO> getFeedbackHasMedia(String tour_id, int page) {
        ArrayList<FeedbackEntity> feedback_list = feedbackRepository.getHasMedia(tour_id, page);
        if (feedback_list.isEmpty())
            return new ArrayList<>();
        ArrayList<FeedbackDTO> rs = new ArrayList<>();
        for (FeedbackEntity ele: feedback_list)
            rs.add(getFeedback(ele));
        return rs;
    }

    public FeedbackEntity deleteFeedback(String id) {
        FeedbackEntity feedback = feedbackRepository.find(id);
        if (feedback == null)
            throw new CustomException("Feedback not found", 400);
        if (!feedback.getUser_id().equals(SecurityContext.getUserID())&& !userService.isAdmin())
            throw new ForbiddenException();
        feedbackRepository.delete(feedback);
        mediaRepository.delete(feedback.getRef_id(), "FEEDBACK");
        return feedback;
    }
}
