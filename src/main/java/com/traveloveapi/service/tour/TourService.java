package com.traveloveapi.service.tour;

import com.traveloveapi.DTO.service.TourDetailDTO;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.TourRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TourService {
    final private ServiceRepository serviceRepository;
    final private TourRepository tourRepository;
    final private UserRepository userRepository;

    public TourDetailDTO createNewTour(String title, String description, String highlight, String note, MultipartFile[] files) {
        return null;
    }

    private UserEntity verifyIsOwner() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        if (user.getRole()!= Role.TOUR_OWNER)
            throw new ForbiddenException();
        return user;
    }
}
