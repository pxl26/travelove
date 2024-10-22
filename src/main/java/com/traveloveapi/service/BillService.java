package com.traveloveapi.service;

import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import com.traveloveapi.DTO.service_package.bill.BillDetailDTO;
import com.traveloveapi.DTO.service_package.bill.BillOption;
import com.traveloveapi.DTO.service_package.bill.CreateBillPersonType;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.ServiceDetailEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.join_entity.JoinBillRow;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherEntity;
import com.traveloveapi.entity.service_package.bill_detail_option.BillDetailOptionEntity;
import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import com.traveloveapi.entity.service_package.limit.PackageLimitEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.entity.service_package.person_type.PackagePersonTypeEntity;
import com.traveloveapi.entity.service_package.special_date.SpecialDateEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.ServiceDetailRepository;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.repository.bill.BillDetailOptionRepository;
import com.traveloveapi.repository.bill.BillDetailPersonTypeRepository;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.repository.service_package.*;
import com.traveloveapi.service.currency.CurrencyService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.service.voucher.VoucherService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService {
    final private BillRepository billRepository;
    final private BillDetailOptionRepository billDetailOptionRepository;
    final private BillDetailPersonTypeRepository billDetailPersonTypeRepository;
    final private SpecialDateRepository specialDateRepository;

    final private PackageOptionRepository packageOptionRepository;
    final private PackageGroupRepository packageGroupRepository;
    final private PackageLimitRepository packageLimitRepository;
    final private PackagePersonTypeRepository packagePersonTypeRepository;
    final private UserRepository userRepository;
    final private UserService userService;
    final private VoucherService voucherService;
    final private CurrencyService currencyService;
    private final ServiceRepository serviceRepository;
    private final ServiceDetailRepository serviceDetailRepository;


    public BillDetailDTO getBillDetail(String bill_id, String currency) {
        BillEntity bill = billRepository.find(bill_id);
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        // ---- AUTH FOR ACCESS: Bill owner + Admin + Tour owner (Own tour)
        if (user.getRole()!= Role.ADMIN&&(user.getRole()==Role.TOUR_OWNER && !userService.verifyIsOwner(bill.getService_id(),user.getId()))&&(user.getRole()==Role.USER&&user.getId()!=SecurityContext.getUserID()))
            throw new ForbiddenException();

        //------------------------------
        ArrayList<JoinBillRow> data = (ArrayList<JoinBillRow>) billRepository.getBillDetail(bill_id);

        BillDetailDTO rs = new BillDetailDTO();
        rs.setBill_id(data.get(0).getBill_id());
        rs.setDate(data.get(0).getDate());
        rs.setTotal(data.get(0).getTotal());
        rs.setQuantity(data.get(0).getQuantity());
        rs.setCreate_at(data.get(0).getCreate_at());
        rs.setUpdate_at(data.get(0).getUpdate_at());
        rs.setStatus(data.get(0).getStatus());
        rs.setUser_id(data.get(0).getUser_id());
        rs.setUser_avatar(data.get(0).getUser_avatar());
        rs.setUser_full_name(data.get(0).getUser_full_name());
        rs.setFeedback_id(data.get(0).getFeedback_id());
        rs.setGateway_url(data.get(0).getGateway_url());
        rs.setPay_method(data.get(0).getPay_method());

        rs.setTour_id(data.get(0).getTour_id());
        rs.setTour_title(data.get(0).getTour_name());
        rs.setTour_rating(data.get(0).getRating());
        rs.setTour_sold(data.get(0).getSold());
        rs.setTour_thumbnail(data.get(0).getTour_thumbnail());
        rs.setOriginCurrency(data.get(0).getBill_currency());
        rs.setUserCurrency(currency);
        if (currency!=null)
            rs.setTotal(currencyService.convert(rs.getOriginCurrency(), currency, rs.getTotal()));

        rs.setPerson_type(new ArrayList<>());
        rs.setOption(new ArrayList<>());
        rs.setVoucher(new ArrayList<>());
        for (JoinBillRow ele: data)
        {
            if (rs.getPerson_type().isEmpty())
            {
                rs.getPerson_type().add(new CreateBillPersonType(ele.getPerson_type(), ele.getPerson_quantity()));
            }
            else
            {
                boolean k=true;
                for (CreateBillPersonType i: rs.getPerson_type())
                    if (i.getType().equals(ele.getPerson_type()))
                    {
                        k=false;
                        break;
                    }
                if (k)
                    rs.getPerson_type().add(new CreateBillPersonType(ele.getPerson_type(), ele.getPerson_quantity()));
            }
            //--------------------------
            if (rs.getOption().isEmpty())
            {
                rs.getOption().add(new BillOption(ele.getGroup_name(), ele.getOption_name()));
            }
            else
            {
                boolean k=true;
                for (BillOption i: rs.getOption())
                    if (i.getGroup().equals(ele.getGroup_name()))
                    {
                        k=false;
                        break;
                    }
                if (k)
                    rs.getOption().add(new BillOption(ele.getGroup_name(), ele.getOption_name()));
            }
            if (ele.getVoucher_id()!=null) {
                if (rs.getVoucher().isEmpty())
                    rs.getVoucher().add(new BillVoucherEntity(ele.getBill_id(), ele.getVoucher_id(), ele.getVoucher_discount_amount()));
                else
                {
                    boolean k=true;
                    for (BillVoucherEntity i: rs.getVoucher())
                        if (i.getVoucher_id().equals(ele.getVoucher_id()))
                        {
                            k=false;
                            break;
                        }
                    if (k)
                        rs.getVoucher().add(new BillVoucherEntity(ele.getBill_id(), ele.getVoucher_id(), ele.getVoucher_discount_amount()));
                }
            }
        }
        return rs;
    }

    public BillDTO createNewBill(BillRequest data, String currency) {
        String id = UUID.randomUUID().toString().replace("-","");
        String user_id = SecurityContext.getUserID();
        BillEntity bill = new BillEntity();
        bill.setId(id);
        bill.setUser_id(user_id);
        bill.setService_id(data.getService_id());
        bill.setDate(data.getDate());
        bill.setCreate_at(new Timestamp(System.currentTimeMillis()));
        bill.setStatus(BillStatus.PENDING);
        bill.setCurrency(currency);
        //--
        Float total = getTotalForBill(data.getService_id(),data.getDate(), data.getPerson_types(), data.getOptions());
        ServiceDetailEntity tour = serviceDetailRepository.find(data.getService_id());
        if (tour.getCurrency().equals(currency)) {
            bill.setTotal(total.doubleValue());
            System.out.println("SAME");
        }
        else
            bill.setTotal(currencyService.convert(tour.getCurrency(), currency,total.doubleValue()));
        //---
        int num_ticket = 0;
        for (CreateBillPersonType ele: data.getPerson_types())
            num_ticket+=ele.getQuantity();
        bill.setQuantity(num_ticket);

        billRepository.save(bill);
        voucherService.applyVouchers(data.getVoucher_key_list(), bill.getId());

        System.out.println("1: " + bill.getTotal());

        for (GroupOptionDTO option : data.getOptions()) {
            BillDetailOptionEntity bill_option = new BillDetailOptionEntity();
            bill_option.setBill_id(id);
            bill_option.setGroup_number(option.getGroup_number());
            bill_option.setOption_number(option.getOption_number());
            billDetailOptionRepository.save(bill_option);
        }
        System.out.println("2: " + bill.getTotal());

        for (CreateBillPersonType person : data.getPerson_types()) {
            BillDetailPersonTypeEntity entity = new BillDetailPersonTypeEntity();
            entity.setBill_id(id);
            entity.setType(person.getType());
            entity.setQuantity(person.getQuantity());
            billDetailPersonTypeRepository.save(entity);
        }

        System.out.println("3: " + bill.getTotal());
        BillDTO rs = new BillDTO();
        rs.setId(id);
        rs.setUser_id(user_id);
        rs.setCreate_at(bill.getCreate_at());
        rs.setTotal(bill.getTotal());
        rs.setService_id(bill.getService_id());
        rs.setOptions(data.getOptions());
        rs.setPerson_types(data.getPerson_types());
        rs.setStatus(BillStatus.PENDING);
        rs.setOriginCurrency(tour.getCurrency());
        rs.setUserCurrency(currency);
        System.out.println("4: " + bill.getTotal());
        System.out.println(rs.getOriginCurrency());
        System.out.println(currency);
        System.out.println(rs.getTotal());

        return rs;
    }


    public int getAvailablePackage(String service_id, Date date, ArrayList<GroupOptionDTO> option_list) {
        boolean isSpecialDate = checkSpecialDate(service_id,date);
        ArrayList<Integer> remain_slot_list = new ArrayList<>();

        ArrayList<BillEntity> bill_on_date = billRepository.findByService(service_id, date);
        ArrayList<BillDetailOptionEntity> option_on_bill = new ArrayList<>();
        for (BillEntity bill : bill_on_date)
            option_on_bill.addAll(billDetailOptionRepository.findByBill(bill.getId()));


        ArrayList<PackageOptionEntity> option_in_service = packageOptionRepository.findByService(service_id);

        System.out.println("So option: " + option_in_service.size());
        System.out.println("So bill: "+ bill_on_date.size());
        System.out.println("Date: "+ date);
        System.out.println(isSpecialDate ? "NGAY DB" : "Khong phai ngay db");

        boolean isSpecialDay = checkSpecialDate(service_id, date);


        //-----CHECK FOR EACH GROUP LIMIT
        ArrayList<PackageGroupEntity> group_list = packageGroupRepository.find(service_id);
        for (PackageGroupEntity entity: group_list) {
            int limit = isSpecialDay ? entity.getLimit_special() : entity.getLimit();
            if (limit == 0)
                continue;
            for (BillDetailOptionEntity ele : option_on_bill) {
                if (ele.getGroup_number() == entity.getGroup_number())
                    limit -= getQuantityOfBill(bill_on_date, ele.getBill_id());
            }
            remain_slot_list.add(limit);
        }
        //------CHECK FOR EACH NODE
        for (GroupOptionDTO entity: option_list) {
            int limit = getNodeLimit(entity.getGroup_number(), entity.getOption_number(), isSpecialDate, option_in_service);
            if (limit==0)
                continue;
            for (BillDetailOptionEntity option :option_on_bill) {
                if (entity.getGroup_number()!=option.getGroup_number() || entity.getOption_number()!=option.getOption_number())
                    continue;
                limit-=getQuantityOfBill(bill_on_date, option.getBill_id());
            }
            remain_slot_list.add(limit);
        }
        //------CHECK FOR BRANCH-------
        ArrayList<PackageLimitEntity> branch_ban_list = packageLimitRepository.findByService(service_id);
        for (PackageLimitEntity rule : branch_ban_list) {
            //Check banded branch
            boolean isHaveOption_1 = false;
            boolean isHaveOption_2 = false;
            for (GroupOptionDTO chosen : option_list) {
                if (chosen.getGroup_number() == rule.getGroup_1() && chosen.getOption_number() == rule.getOption_1()) {
                    isHaveOption_1 = true;
                    continue;
                } else if (chosen.getOption_number() == rule.getGroup_2() && chosen.getOption_number() == rule.getOption_2())
                    isHaveOption_2 = true;
            }
            if (!isHaveOption_1 && !isHaveOption_2)
                continue;
            int limit_on_single_branch = isSpecialDay ? rule.getLimit_special() : rule.getLimit();
            if (limit_on_single_branch==0)
                continue;
            for (BillEntity bill : bill_on_date) {
                ArrayList<BillDetailOptionEntity> options = billDetailOptionRepository.findByBill(bill.getId());
                // Check for existing of branch in bill
                isHaveOption_1 = false;
                isHaveOption_2 = false;
                for (BillDetailOptionEntity option : options) {
                    if (option.getGroup_number() == rule.getGroup_1() && option.getOption_number() == rule.getOption_1()) {
                        isHaveOption_1 = true;
                        continue;
                    } else if (option.getGroup_number() == rule.getGroup_2() && option.getOption_number() == rule.getOption_2())
                        isHaveOption_2 = true;
                }
                if (isHaveOption_1&&isHaveOption_2)
                    limit_on_single_branch-=getQuantityOfBill(bill_on_date, bill.getId());
            }
            if (limit_on_single_branch>0)
                remain_slot_list.add(limit_on_single_branch);
            else
                return 0;
        }
        // Find min limit
        if (remain_slot_list.isEmpty())
            return -1;
        int min = remain_slot_list.get(0);
        for (int ele:remain_slot_list) {
            System.out.println("MANG: " + ele);
            if (ele < min)
                min = ele;
        }
        return min;
    }

    public float getTotalForBill(String service_id, Date date, ArrayList<CreateBillPersonType> person_type, ArrayList<GroupOptionDTO> options) {
        ArrayList<PackageOptionEntity> option_entity_list = packageOptionRepository.findByService(service_id);
        boolean isSpecialDay = checkSpecialDate(service_id,date);
        float base_price = 0;
        for (GroupOptionDTO option: options)
            for (PackageOptionEntity entity: option_entity_list)
                if (option.getGroup_number()==entity.getGroup_number()&&option.getOption_number()==entity.getOption_number())
                {
                    if (isSpecialDay)
                        base_price+=entity.getPrice_special();
                    else
                        base_price+=entity.getPrice();
                    break;
                }

        float total = 0;
        ArrayList<PackagePersonTypeEntity> person_type_list = packagePersonTypeRepository.find(service_id);
        for (CreateBillPersonType person: person_type)
            for (PackagePersonTypeEntity entity: person_type_list)
                if (person.getType().equals(entity.getName())) {
                    total += (entity.getBonus_price() + base_price)*person.getQuantity();
                    break;
                }

        System.out.println("TOTAL: " + total);
        return total;
    }

    private int getNodeLimit(int group, int option, boolean isSpecial, ArrayList<PackageOptionEntity> option_list) {
        for (PackageOptionEntity entity:option_list){
            if (entity.getGroup_number()==group&&entity.getOption_number()==option)
                return isSpecial ? entity.getLimit_special() : entity.getLimit();
        }
        return 0;
    }

    private int getQuantityOfBill(ArrayList<BillEntity> list, String bill_id) {
        for (BillEntity entity : list)
            if (entity.getId().equals(bill_id))
                return entity.getQuantity();
        throw new RuntimeException();
    }

    private boolean checkSpecialDate(String service_id, Date date) {
        int number_in_week = date.toLocalDate().getDayOfWeek().getValue();
        int number_in_month = date.toLocalDate().getDayOfMonth();
        System.out.println("NGAY: " + number_in_week);

        ArrayList<SpecialDateEntity> special_day_list = specialDateRepository.findByService(service_id);
        for (SpecialDateEntity day : special_day_list)
            if (day.getType().equals("WEEK") && day.getSeq() + 1 == number_in_week)
                return true;
            else if (day.getType().equals("MONTH") && day.getSeq() + 1 == number_in_month)
                return true;
        return false;
    }
}
