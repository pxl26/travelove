package com.traveloveapi.service;

import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import com.traveloveapi.DTO.service_package.PackageGroupDTO;
import com.traveloveapi.DTO.service_package.bill.CreateBillPersonType;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.entity.service_package.bill_detail_option.BillDetailOptionEntity;
import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import com.traveloveapi.entity.service_package.limit.PackageLimitEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.entity.service_package.person_type.PackagePersonTypeEntity;
import com.traveloveapi.entity.service_package.special_date.SpecialDateEntity;
import com.traveloveapi.repository.service_package.*;
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

    public BillDTO createNewBill(BillRequest data) {
        String id = UUID.randomUUID().toString();
        String user_id = SecurityContext.getUserID();

        BillEntity bill = new BillEntity();
        bill.setId(id);
        bill.setUser_id(user_id);
        bill.setService_id(data.getService_id());
        bill.setTotal(getTotalForBill(data.getService_id(),data.getDate(), data.getPerson_types(), data.getOptions()));
        bill.setDate(data.getDate());
        bill.setCreate_at(new Timestamp(System.currentTimeMillis()));
        int num_ticket = 0;
        for (CreateBillPersonType ele: data.getPerson_types())
            num_ticket+=ele.getQuantity();
        bill.setQuantity(num_ticket);
        billRepository.save(bill);

        for (GroupOptionDTO option : data.getOptions()) {
            BillDetailOptionEntity bill_option = new BillDetailOptionEntity();
            bill_option.setBill_id(id);
            bill_option.setGroup_number(option.getGroup_number());
            bill_option.setOption_number(option.getOption_number());
            billDetailOptionRepository.save(bill_option);
        }

        for (CreateBillPersonType person : data.getPerson_types()) {
            BillDetailPersonTypeEntity entity = new BillDetailPersonTypeEntity();
            entity.setBill_id(id);
            entity.setType(person.getType());
            entity.setQuantity(person.getQuantity());
            billDetailPersonTypeRepository.save(entity);
        }

        BillDTO rs = new BillDTO();
        rs.setId(id);
        rs.setUser_id(user_id);
        rs.setCreate_at(bill.getCreate_at());
        rs.setTotal(bill.getTotal());
        rs.setService_id(bill.getService_id());
        rs.setOptions(data.getOptions());
        rs.setPerson_types(data.getPerson_types());

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
