package com.example.usermanagement.security.services;

import com.example.usermanagement.dto.PageDTO;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.model.ERole;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.Section;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.SectionRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.ultil.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EntityManager entityManager;

    public void save(UserDTO userDTO) throws Exception {
        this.userRepository.save(this.toEntity(userDTO));
    }

    public void delete(Long id) throws Exception {
        this.userRepository.deleteById(id);
    }

    public User toEntity(UserDTO userDTO) throws Exception {
        User user;
        if (userDTO.getId() == null) {
            // 09/06/2021
            user = new User();
        } else {
            user = userRepository.findById(userDTO.getId()).orElse(null);
        }

        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setAddress(userDTO.getAddress());
        user.setDescription(userDTO.getDescription());
        if (userDTO.getSectionId() != null) {
            user.setSection(sectionRepository.findById(userDTO.getSectionId()).orElse(null));
        }
        if (userDTO.getRoleId() != null) {
            user.setRole(roleRepository.findById(userDTO.getRoleId()).orElse(null));
        }
        if (userDTO.getUpdaterId() != null) {
            user.setUpdater(userRepository.findById(userDTO.getUpdaterId()).orElse(null));
        }
        if (userDTO.getCreatorId() != null) {
            user.setCreator(userRepository.findById(userDTO.getCreatorId()).orElse(null));
        }
        user.setUpdateDate(new Date());
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFullName(user.getFullName());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setDescription(user.getDescription());
        userDTO.setAddress(user.getAddress());
        userDTO.setCreateDate(user.getCreateDate());
        if (user.getCreator() != null) {
            userDTO.setCreatorId(user.getCreator().getId());
            userDTO.setCreatorName(user.getCreator().getFullName());
        }
        if (user.getUpdater() != null) {
            userDTO.setUpdaterId(user.getUpdater().getId());
            userDTO.setUpdaterName(user.getUpdater().getFullName());
        }
        if (user.getSection() != null) {
            userDTO.setSectionId(user.getSection().getId());
            userDTO.setSectionName(user.getSection().getName());
        }
        if (user.getRole() != null) {
            userDTO.setRoleId(user.getRole().getId());
            userDTO.setRoleName(user.getRole().getName().toString());
        }
        return userDTO;
    }

    public PageDTO<UserDTO> getUserByKeyword(String keyword, Integer pageIndex, Integer pageSize) {
        if (keyword == null) {
            keyword = " ";
        }
        // get role user hard code
        List<UserDTO> listUserDto = userRepository.findByFullNameContainingAndRole(keyword, roleRepository.getById(2l)).get()
                .stream().map(this::toDTO).collect(Collectors.toList());
        PageDTO<UserDTO> result = new PageDTO<UserDTO>(listUserDto, pageIndex - 1, pageSize);
//        System.out.println("pageIndex "+pageIndex+" pageSize "+pageSize);
        return result;
    }

    public PageDTO<UserDTO> getUserByKeyword(String keyword, Integer pageIndex, Integer pageSize, int type) {

        List<UserDTO> listUserDto;
        if (keyword == null) {
            keyword = " ";
        }
        // get role user hard code
        switch (type) {
            case 2:
                listUserDto = userRepository.findByAddressContainingAndRole(keyword, roleRepository.getById(2l)).get()
                        .stream().map(this::toDTO).collect(Collectors.toList());
                break;
            case 3:
                listUserDto = userRepository.findByDescriptionContainingAndRole(keyword, roleRepository.getById(2l)).get()
                        .stream().map(this::toDTO).collect(Collectors.toList());
                break;
            default:
                listUserDto = userRepository.findByFullNameContainingAndRole(keyword, roleRepository.getById(2l)).get()
                        .stream().map(this::toDTO).collect(Collectors.toList());
                break;
        }
        PageDTO<UserDTO> result = new PageDTO<UserDTO>(listUserDto, pageIndex - 1, pageSize);
//        System.out.println("pageIndex "+pageIndex+" pageSize "+pageSize);
        return result;
    }


    public PageDTO<UserDTO> getUserByAdvancedSearch(String fullName, String address, Integer sectionId, String fromDate, String toDate,
                                                    Integer pageIndex, Integer pageSize){

        StringBuilder sql = new StringBuilder("select * from User u where");
        if (!fullName.equals("")) {
            sql.append(" full_name like concat('%', :fullName, '%') ");
        }
        if(!address.equals("")){
            sql.append(" and address like concat('%', :address , '%') ");
        }
        if(!fromDate.equals("")){
            sql.append(" and date_of_birth >= :fromDate ");
        }
        if(!toDate.equals("")){
            sql.append(" and date_of_birth <= :toDate");
        }
        if(sectionId != null){
            sql.append(" and section_id = :sectionId ");
        }
        sql.append(" and role_id = 2");
        Query query = entityManager.
                createNativeQuery(sql.toString().trim().replace("where and","where"), User.class);
        if (!fullName.equals("")) {
            query.setParameter("fullName", fullName);
        }
        if(!address.equals("")) {
            query.setParameter("address", address);
        }
        if(!fromDate.equals("") && Validator.isDate(fromDate)) {
            System.out.println(fromDate);
            System.out.println("wtf "+Validator.stringToDate(fromDate));
            query.setParameter("fromDate", Validator.stringToDate(fromDate));
        }
        if(!toDate.equals("") && Validator.isDate(toDate)) {
            System.out.println(toDate);
            System.out.println("wtf "+Validator.stringToDate(toDate));
            query.setParameter("toDate", Validator.stringToDate(toDate));
        }

        if(sectionId != null){
            query.setParameter("sectionId",sectionId);
        }
        List<UserDTO> listUserDto = ((List<User>)query.getResultList())
                .stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println("testzz"+listUserDto);
        System.out.println("pageIndex:"+pageIndex);
        System.out.println("pageSize: "+pageSize);
        PageDTO<UserDTO> result = new PageDTO<UserDTO>(listUserDto,pageIndex -1, pageSize) ;
        return result;
    }

    public PageDTO<UserDTO> getUserBySection(Long sectionId, Integer pageIndex, Integer pageSize) {
        Section section = sectionRepository.findById(sectionId).orElse(null);
        List<UserDTO> listUserDto = userRepository.findBySectionAndRole(section, roleRepository.getById(2l)).get()
                .stream().map(this::toDTO).collect(Collectors.toList());
        PageDTO<UserDTO> result = new PageDTO<UserDTO>(listUserDto, pageIndex - 1, pageSize);
//        System.out.println("pageIndex "+pageIndex+" pageSize "+pageSize);
        return result;
    }
}
