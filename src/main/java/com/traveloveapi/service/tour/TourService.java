package com.traveloveapi.service.tour;

import com.traveloveapi.DTO.service.TourDetailDTO;
import com.traveloveapi.constrain.SensorAction;
import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.constrain.ServiceType;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.TourEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.TourRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.file.FileService;
import com.traveloveapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourService {
    final private ServiceRepository serviceRepository;
    final private TourRepository tourRepository;
    final private UserRepository userRepository;
    final private MediaRepository mediaRepository;
    final private FileService fileService;
    final private UserService userService;

    public TourDetailDTO createNewTour(String title, String description, String highlight, String note, MultipartFile[] files) {
        UserEntity owner = userService.verifyIsOwner();
        ServiceEntity service = new ServiceEntity();
        TourEntity tour = new TourEntity();

        String id = UUID.randomUUID().toString();

        service.setService_owner(owner.getId());
        service.setSold(0);
        service.setRating(0);
        service.setStatus(ServiceStatus.PENDING);
        service.setType(ServiceType.TOUR);

        service.setTitle(title);
        service.setId(id);
        tour.setId(id);
        tour.setDescription(description);
        tour.setNote(note);
        tour.setHighlight(highlight);


        ArrayList<MediaEntity> media = new ArrayList<>();
        for (MultipartFile file : files) {
            media.add(fileService.saveMedia(file, "", id, "TOUR_MEDIA"));
        }
        service.setThumbnail(media.get(0).getPath());
        serviceRepository.save(service);
        tourRepository.save(tour);

        return new TourDetailDTO(service, tour, media);
    }

    public TourDetailDTO getTour(String id) {
        ServiceEntity service = serviceRepository.find(id);
        TourEntity tour = tourRepository.find(id);
        ArrayList<MediaEntity> media = mediaRepository.find(id, "TOUR_MEDIA");
        return new TourDetailDTO(service, tour, media);
    }

    public ServiceEntity changeStatus(SensorAction action, String tour_id) {
        userService.verifyIsAdmin();
        ServiceEntity entity = serviceRepository.find(tour_id);
        if (action==SensorAction.ACCEPT)
            entity.setStatus(ServiceStatus.VERIFIED);
        else
            entity.setStatus(ServiceStatus.DECLINED);
        serviceRepository.save(entity);
        return entity;
    }

}
