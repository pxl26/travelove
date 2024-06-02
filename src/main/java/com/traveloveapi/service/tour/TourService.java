package com.traveloveapi.service.tour;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.location.CityDTO;
import com.traveloveapi.DTO.service.ServiceCard;
import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.DTO.service_package.*;
import com.traveloveapi.DTO.service_package.create_package.CreatePackagePersonType;
import com.traveloveapi.DTO.service_package.create_package.CreateSpecialOption;
import com.traveloveapi.constrain.*;
import com.traveloveapi.entity.*;
import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.entity.service_package.disable_option.DisableOptionEntity;
import com.traveloveapi.entity.service_package.option_special.OptionSpecialEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.entity.service_package.person_type.PackagePersonTypeEntity;
import com.traveloveapi.entity.service_package.special_date.SpecialDateEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.ServiceDetailRepository;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.location.CityRepository;
import com.traveloveapi.repository.searching.SearchingRepository;
import com.traveloveapi.repository.service_package.*;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.currency.CurrencyService;
import com.traveloveapi.service.location.CityService;
import com.traveloveapi.service.redis.RedisService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.service.wish_list.WishListService;
import com.traveloveapi.utility.SearchingSupporter;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourService {
    final private ServiceRepository serviceRepository;
    final private ServiceDetailRepository tourRepository;
    final private MediaRepository mediaRepository;
    final private UserService userService;

    final private SpecialDateRepository specialDateRepository;
    final private OptionSpecialRepository optionSpecialRepository;
    final private PackageDisableOptionRepository packageDisableOptionRepository;
    final private PackagePersonTypeRepository packagePersonTypeRepository;
    final private PackageGroupRepository packageGroupRepository;
    final private PackageOptionRepository packageOptionRepository;
    final private SearchingRepository searchingRepository;
    final private S3FileService s3FileService;
    final private CityService cityService;
    final private WishListService wishListService;
    final private CityRepository cityRepository;
    final private RedisService redisService;
    final private CurrencyService currencyService;

    public ServiceDetailDTO createNewService(ServiceType type, String title, String description, String highlight, String note, String currency, String primary_language, MultipartFile[] files,String[] gallery_description, String city_id, String location, String address) throws IOException, InterruptedException {
        UserEntity owner = userService.verifyIsTourOwner();
        ServiceEntity service = new ServiceEntity();
        ServiceDetailEntity tour = new ServiceDetailEntity();

        String id = UUID.randomUUID().toString();

        service.setService_owner(owner.getId());
        service.setSold(0);
        service.setRating(0);
        service.setVote_quantity(0);
        service.setStatus(ServiceStatus.PENDING);
        service.setType(type);
        service.setCity_id(city_id);
        service.setTitle(title);
        service.setId(id);
        tour.setId(id);
        tour.setDescription(description);
        tour.setNote(note);
        tour.setHighlight(highlight);
        tour.setCurrency(currency);
        tour.setPrimary_language(primary_language);
        tour.setAddress(address);
        tour.setLocation(location);

        ArrayList<MediaEntity> media = saveTourGallery(id,files, gallery_description);
        service.setThumbnail(media.get(0).getPath());
        serviceRepository.save(service);
        tourRepository.save(tour);

        //---------- CREATE SEARCHING RECORD
        //makeSearchable(service);

        return new ServiceDetailDTO(service, tour, media,false, currency, (double)service.getMin_price());
    }

    public ServiceDetailDTO getTour(String id, String currency) {
        ServiceDetailDTO rs;
        boolean isPrivilege = false;
        if (SecurityContext.isAnonymous()) {
            String key = "tour_detail:"+currency+':'+id;
            ObjectMapper mapper = new ObjectMapper();
            String value = redisService.getConnection().get(key);
            try {
                if (value != null)
                    return mapper.readValue(value, ServiceDetailDTO.class);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        else if (userService.isAdmin() || userService.verifyIsOwner(id, SecurityContext.getUserID()))
        {
            isPrivilege = true;
            String key = "tour_detail:" + id + ":privilege";
            ObjectMapper mapper = new ObjectMapper();
            String value = redisService.getConnection().get(key);
            try {
                if (value != null)
                    return mapper.readValue(value, ServiceDetailDTO.class);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        else
        {
            String key = "tour_detail:"+currency+':'+id;
            ObjectMapper mapper = new ObjectMapper();
            String value = redisService.getConnection().get(key);
            try {
                if (value != null)
                    return mapper.readValue(value, ServiceDetailDTO.class);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        //--------------------------------------------
        ServiceEntity service;
        if (SecurityContext.isAnonymous())
            service = serviceRepository.find(id);
        else
            service = userService.isAdmin() ? serviceRepository.findAdmin(id) : serviceRepository.find(id);
        if (service==null&& userService.verifyIsOwner(id, SecurityContext.getUserID()))
            service = serviceRepository.findAdmin(id);
        System.out.println("Hello: " + id);
        ServiceDetailEntity tour = tourRepository.find(id);
        ArrayList<MediaEntity> media = mediaRepository.find(id, "GALLERY-MEDIA");

        rs = new ServiceDetailDTO(service, tour, media, !SecurityContext.isAnonymous() && wishListService.isWish(SecurityContext.getUserID(), id), currency,  currencyService.convert(tour.getCurrency(), currency, (double)service.getMin_price()));
        try {
            redisService.getConnection().set("tour_detail:" + (isPrivilege ? ":privilege:" : ":") +currency + ':' + id , new ObjectMapper().writeValueAsString(rs));
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return rs;
    }

    public ArrayList<ServiceDetailDTO> getPendingTour(String service_owner, String currency) {
        boolean isAdmin = userService.isAdmin();
        if ((!isAdmin)&&service_owner.isEmpty())
            if (!service_owner.equals(SecurityContext.getUserID()))
                    throw new ForbiddenException();
        ArrayList<ServiceEntity> entity_list = !service_owner.isEmpty() ? serviceRepository.findByStatus(ServiceStatus.PENDING, service_owner) : serviceRepository.findByStatus(ServiceStatus.PENDING);
        if (entity_list.isEmpty())
            return new ArrayList<>();
        ArrayList<ServiceDetailDTO> rs = new ArrayList<>();
        for (ServiceEntity entity: entity_list) {
            System.out.println(entity.getTitle());
            rs.add(getTour(entity.getId(), currency));
        }
        return rs;
    }

    public ServiceDetailDTO editTour(String service_id,ServiceType type,String title, String description, String highlight, String note, String currency, String primary_language, Float min_price, String city_id) {
        ServiceEntity entity = serviceRepository.find(service_id);
        ServiceDetailEntity detail = tourRepository.find(service_id);
        SearchingEntity searching = searchingRepository.find(service_id);
        if (!SecurityContext.getUserID().equals(entity.getService_owner()))
            throw new ForbiddenException();
        if (type!=null)
            entity.setType(type);
        if(title!=null) {
            entity.setTitle(title);
            searching.setTitle(title);
            searching.setData(SearchingSupporter.sanitize(title));
        }
        if (min_price!=null) {
            entity.setMin_price(min_price);
            searching.setMin_price(min_price);
        }
        if (highlight!=null)
            detail.setHighlight(highlight);
        if (description!=null)
            detail.setDescription(description);
        if (note!=null)
            detail.setNote(note);
        if (currency!=null)
            detail.setCurrency(currency);
        if (primary_language!=null)
            detail.setPrimary_language(primary_language);
        if (city_id!=null)
            entity.setCity_id(city_id);

        serviceRepository.update(entity);
        tourRepository.update(detail);
        searchingRepository.update(searching);
        redisService.getConnection().del("tour_detail:"+service_id);
        redisService.getConnection().del("tour_detail:"+service_id+":privilege");
        return new ServiceDetailDTO(entity, detail, mediaRepository.find(service_id, "GALLERY-MEDIA"), !SecurityContext.isAnonymous() && wishListService.isWish(SecurityContext.getUserID(), service_id), currency, currencyService.convert(detail.getCurrency(), currency, (double)entity.getMin_price()));
    }


    public PackageInfoDTO getPackageInfo(String service_id) {
        PackageInfoDTO result = new PackageInfoDTO();
        result.setService_id(service_id);

        result.setPackage_group(new ArrayList<>());
        result.setDisable_list(new ArrayList<>());
        result.setPerson_type(new ArrayList<>());
        result.setSpecial_date(new ArrayList<>());
        result.setSpecial_option(new ArrayList<>());

        //----set special date
        ArrayList<SpecialDateEntity> specialDateEntities = specialDateRepository.findByService(service_id);
        for (SpecialDateEntity entity: specialDateEntities) {
            SpecialDateDTO dto = new SpecialDateDTO(entity.getType(), entity.getSeq());
            result.getSpecial_date().add(dto);
        }
        //------set person type
        ArrayList<PackagePersonTypeEntity> packagePersonTypeEntities = packagePersonTypeRepository.find(service_id);
        for (PackagePersonTypeEntity entity: packagePersonTypeEntities) {
            CreatePackagePersonType dto = new CreatePackagePersonType(entity.getName(), entity.getBonus_price());
            result.getPerson_type().add(dto);
        }
        //------set special option
        ArrayList<OptionSpecialEntity> optionSpecialEntities = optionSpecialRepository.findByService(service_id);
        for (OptionSpecialEntity entity: optionSpecialEntities) {
            CreateSpecialOption dto = new CreateSpecialOption(entity.getGroup_number(), entity.getOption_number(),entity.is_disable());
            result.getSpecial_option().add(dto);
        }
        //-----set disable option
        ArrayList<DisableOptionEntity> disableList = packageDisableOptionRepository.findByService(service_id);
        for (DisableOptionEntity entity: disableList) {
            GroupOptionDTO option_1 = new GroupOptionDTO(null,entity.getGroup_1(), null,entity.getOption_1());
            GroupOptionDTO option_2 = new GroupOptionDTO(null, entity.getGroup_2(), null,entity.getOption_2());
            ArrayList<GroupOptionDTO> option_pair = new ArrayList<>();
            option_pair.add(option_1);
            option_pair.add(option_2);
            result.getDisable_list().add(option_pair);
        }
        //-----set all option
        ArrayList<PackageGroupEntity> group_list = packageGroupRepository.find(service_id);
        ArrayList<PackageOptionEntity> option_list = packageOptionRepository.findByService(service_id);
        for (PackageGroupEntity entity: group_list) {
            PackageGroupDTO dto = new PackageGroupDTO();
            dto.setTitle(entity.getTitle());
            dto.setGroup_number(entity.getGroup_number());
            dto.setPackage_option(new ArrayList<>());
            for (PackageOptionEntity option: option_list)
                if (option.getGroup_number()==entity.getGroup_number())
                    dto.getPackage_option().add(new OptionDTO(entity.getGroup_number(), option.getOption_number(), option.getName(), option.getPrice(), option.getPrice_special()));
            result.getPackage_group().add(dto);
        }
        return result;
    }

    public ArrayList<ServiceCard> getTourByTitle(String query, OrderType type, SortBy sortBy, int page, int page_size, String currency) {
        ArrayList<ServiceEntity> list = serviceRepository.findByTitle(query, type, sortBy, page, page_size);
        ArrayList<ServiceCard> rs = new ArrayList<>();
        for (ServiceEntity ele: list)
            rs.add(createCard(ele.getId(), currency));
        return rs;
    }

    public ArrayList<ServiceCard> getTourByCity(String city_id, OrderType type, SortBy sortBy, int page, int page_size, String currency) {
        ArrayList<ServiceEntity> list = serviceRepository.findByCity(city_id, type, sortBy, page, page_size);
        ArrayList<ServiceCard> rs = new ArrayList<>();
        for (ServiceEntity ele: list)
            rs.add(createCard(ele.getId(), currency));
        return rs;
    }

    public ArrayList<ServiceCard> getTourByCountry(String country_name, OrderType type, SortBy sortBy, int page, int page_size, String currency) {
        ArrayList<ServiceEntity> list = serviceRepository.findByCountry(country_name, type, sortBy, page, page_size);
        ArrayList<ServiceCard> rs = new ArrayList<>();
        for (ServiceEntity ele: list)
            rs.add(createCard(ele.getId(), currency));
        return rs;
    }

    public ServiceCard createCard(String service_id, String currency) {
        ObjectMapper mapper = new ObjectMapper();
        String cached_value = redisService.getConnection().get("tour_card:"+currency+':'+service_id);
        try {
            if (cached_value!=null)
                return mapper.readValue(cached_value, ServiceCard.class);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        ServiceEntity service = serviceRepository.findAdmin(service_id);
        ServiceDetailEntity detail = tourRepository.find(service_id);
        CityEntity city = cityRepository.findById(service.getCity_id());
        ServiceCard rs = new ServiceCard();
        rs.setService_id(service_id);
        rs.setSold(service.getSold());
        rs.setTitle(service.getTitle());
        rs.setRating(service.getRating());
        rs.setThumbnail(service.getThumbnail());
        rs.setCity(city.getName());
        rs.setCountry(city.getCountry_name());
        rs.setStatus(service.getStatus());

        if(currency==null) {
            rs.setCurrency(detail.getCurrency());
            rs.setMin_price((double)service.getMin_price());
        }
        else {
            rs.setCurrency(currency);
            rs.setMin_price(currencyService.convert(detail.getCurrency(), currency, (double) service.getMin_price()));
        }
        try {
            redisService.getConnection().set("tour_card:"+currency+':'+service_id, mapper.writeValueAsString(rs));
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return rs;
    }
    public ServiceEntity changeStatus(SensorAction action, String tour_id) {
        userService.verifyIsAdmin();
        ServiceEntity service = serviceRepository.findAdmin(tour_id);
        if (action==SensorAction.ACCEPT) {
            service.setStatus(ServiceStatus.VERIFIED);
            makeSearchable(service);
        }
        else
            service.setStatus(ServiceStatus.DECLINED);
        serviceRepository.update(service);
        return service;
    }

    private void makeSearchable(ServiceEntity service) {
        SearchingEntity entity = new SearchingEntity();
        entity.setRef_id(service.getId());
        entity.setTitle(service.getTitle());
        entity.setData(SearchingSupporter.sanitize(service.getTitle()));
        entity.setThumbnail(service.getThumbnail());
        entity.setType(SearchingType.TOUR);
        entity.setCity_name(cityService.getCityName(service.getCity_id()));
        entity.setMin_price(service.getMin_price());
        searchingRepository.save(entity);
    }

    private ArrayList<MediaEntity> saveTourGallery(String tour_id, MultipartFile[] file_list, String[] description) {
        ArrayList<MediaEntity> rs = new ArrayList<>();
        int k = 0;
        for (MultipartFile file: file_list) {
            MediaEntity media = new MediaEntity();
            media.setId(UUID.randomUUID().toString());
            media.setType("GALLERY-MEDIA");
            media.setSeq(k);
            media.setRef_id(tour_id);
            media.setDescription(description[k++]);
            media.setPath(s3FileService.uploadFile(file, "public/service/"+tour_id+'/',UUID.randomUUID().toString()));
            mediaRepository.save(media);
            rs.add(media);
        }
        //s3FileService.multipleFileUpload("public/service/"+tour_id, list);
        return rs;
    }

    public ArrayList<ServiceCard> getTourByOwner(String owner_id, String currency) {
        ArrayList<ServiceCard> rs = new ArrayList<>();
        List<ServiceEntity> tour;
        if (owner_id==null)
            tour = serviceRepository.findByOwner(SecurityContext.getUserID());
        else if (!userService.isAdmin() && !SecurityContext.getUserID().equals(owner_id))
            throw new ForbiddenException();
        else tour = serviceRepository.findByOwner(SecurityContext.getUserID());
        for (ServiceEntity ele: tour)
            rs.add(this.createCard(ele.getId(), currency));
        return rs;
    }
}
