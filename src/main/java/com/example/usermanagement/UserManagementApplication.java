package com.example.usermanagement;

import com.example.usermanagement.dto.PageDTO;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.SectionRepository;
import com.example.usermanagement.repository.UserRepository;

import com.example.usermanagement.security.services.UserService;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;

import java.util.List;


@SpringBootApplication

public class UserManagementApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserManagementApplication.class, args);
//        UserRepository userRepository = context.getBean(UserRepository.class);
//        System.out.println(userRepository.findAll().get(0).getRole().getName());
////        System.out.println(userRepository.findByFullNameContaining("Khoi",PageRequest.of(0,5)).getContent());
////        System.out.println(userRepository.findByFullNameContainingAndAddressContainingAndDescriptionContaining("","Hoa Lac","", PageRequest.of(0,5)).getContent());
//        RoleRepository roleRepository = context.getBean(RoleRepository.class);
////        System.out.println(roleRepository.findAll()+"");
//        SectionRepository sectonRepository = context.getBean(SectionRepository.class);
//        System.out.println(sectonRepository.findAllByParentId(1l));
        UserService userService = context.getBean(UserService.class);
        List<UserDTO> listUser = userService.getUserByAdvancedSearch("","",6,"","03/06/2021",1,10).getContent();
        for(UserDTO user : listUser){
            System.out.println(user.getFullName());
        }
//        System.out.println(sectonRepository.findAll().size());
    }

}
