package com.traveloveapi.service.tour;

import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.constrain.SensorAction;
import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.constrain.ServiceType;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.ServiceDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.ServiceDetailRepository;
import com.traveloveapi.repository.ServiceRepository;
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
    final private ServiceDetailRepository tourRepository;
    final private UserRepository userRepository;
    final private MediaRepository mediaRepository;
    final private FileService fileService;
    final private UserService userService;

    public ServiceDetailDTO createNewService(ServiceType type,String title, String description, String highlight, String note, MultipartFile[] files) {
        UserEntity owner = userService.verifyIsOwner();
        ServiceEntity service = new ServiceEntity();
        ServiceDetailEntity tour = new ServiceDetailEntity();

        String id = UUID.randomUUID().toString();

        service.setService_owner(owner.getId());
        service.setSold(0);
        service.setRating(0);
        service.setStatus(ServiceStatus.PENDING);
        service.setType(type);

        service.setTitle(title);
        service.setId(id);
        tour.setId(id);
        tour.setDescription(description);
        tour.setNote(note);
        tour.setHighlight(highlight);


        ArrayList<MediaEntity> media = new ArrayList<>();
        for (MultipartFile file : files) {
            media.add(fileService.saveMedia(file, "", id, "SERVICE_MEDIA"));
        }
        service.setThumbnail(media.get(0).getPath());
        serviceRepository.save(service);
        tourRepository.save(tour);

        return new ServiceDetailDTO(service, tour, media);
    }

    public ServiceDetailDTO getTour(String id) {
        ServiceEntity service = serviceRepository.find(id);
        ServiceDetailEntity tour = tourRepository.find(id);
        ArrayList<MediaEntity> media = mediaRepository.find(id, "SERVICE_MEDIA");
        return new ServiceDetailDTO(service, tour, media);
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
