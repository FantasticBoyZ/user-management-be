package com.example.usermanagement.controller;

import com.example.usermanagement.model.ERole;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.SectionRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.security.services.SectionService;
import com.example.usermanagement.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    UserService userService;

    @Autowired
    SectionService sectionService;


    @GetMapping("/section/all")
    public ResponseEntity<?> getAllSection() {
        System.out.println(sectionService.getAllSection());
        return ResponseEntity.ok(sectionService.getAllSection());
    }

    @GetMapping(value = "/section/user", params = {"sectionId", "pageIndex", "pageSize"})
    public ResponseEntity<?> getUserBySection(@RequestParam(value = "sectionId") Long sectionId,
                                              @RequestParam(value = "pageIndex") Integer pageIndex,
                                              @RequestParam(value = "pageSize") Integer pageSize) {
//        return ResponseEntity.ok(userRepository.findByFullNameContaining(keyword,PageRequest.of(pageIndex,pageSize)));
        return ResponseEntity.ok(userService.getUserBySection(sectionId, pageIndex, pageSize));
    }

    @GetMapping("/usermanagement/{userId}")
    public User getUser(@PathVariable(name = "userId") Long userId) {
        return userRepository.findById(userId).get();
    }

    @GetMapping("/usermanagement/all")

    public String getAllAccess() {
        return "Hello Everybody";
    }

//    @GetMapping(value = "/usermanagement/user/search", params = {"keyword", "pageIndex", "pageSize"})
//    public ResponseEntity<?> getUserByPage(@RequestParam(value = "keyword", required = false) String keyword,
//                                           @RequestParam(value = "pageIndex") Integer pageIndex,
//                                           @RequestParam(value = "pageSize") Integer pageSize) {
////        return ResponseEntity.ok(userRepository.findByFullNameContaining(keyword,PageRequest.of(pageIndex,pageSize)));
//        return ResponseEntity.ok(userService.getUserByKeyword(keyword, pageIndex, pageSize));
//    }

    @GetMapping(value = "/usermanagement/user/search", params = {"keyword", "pageIndex", "pageSize","type"})
    public ResponseEntity<?> getUserByPageAndType(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageIndex") Integer pageIndex,
                                                  @RequestParam(value = "pageSize") Integer pageSize,
                                                  @RequestParam(value = "type") Integer type) {
//        return ResponseEntity.ok(userRepository.findByFullNameContaining(keyword,PageRequest.of(pageIndex,pageSize)));
        return ResponseEntity.ok(userService.getUserByKeyword(keyword, pageIndex, pageSize, type));
    }

    @GetMapping(value = "/usermanagement/user/advancedsearch")
    public ResponseEntity<?> getUserByAdvancedSearch(@RequestParam(value = "fullName",defaultValue = "") String fullName,
                                                     @RequestParam(value = "address",defaultValue = "") String address,
                                                     @RequestParam(value = "fromDate",defaultValue = "") String fromDate,
                                                     @RequestParam(value = "toDate", defaultValue = "") String toDate,
                                                     @RequestParam(value = "pageIndex", defaultValue = "") Integer pageIndex,
                                                     @RequestParam(value = "pageSize", defaultValue = "") Integer pageSize,
                                                     @RequestParam(value = "sectionId",defaultValue = "") Integer sectionId){

        return ResponseEntity.ok(userService.getUserByAdvancedSearch(fullName,address,sectionId
                ,fromDate,toDate,pageIndex,pageSize));
    }

    @GetMapping("/usermanagement/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUserAccess() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.ROLE_USER, PageRequest.of(0, 15)).getContent());
    }

    @GetMapping("/usermanagement/role")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRole() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

//    @PostMapping("/update/{userId}")
//    public void updateUser(@PathVariable(name = "userId") Long userId){
//
//        if(!userRepository.findById(userId).isPresent()) {
//            this.user = new User();
//            this.user.setCreateDate(new Date());
//        }
//        System.out.println(this.user);
//        this.user.setFullName("Khoi"+ userId);
//        this.user.setUsername("user "+userId);
//        this.user.setPassword("123");
//        this.user.setCreator(userRepository.findById(1l).get());
//        this.user.setAddress("Ha Noi");
//        this.user.setDateOfBirth(new Date());
//        this.user.setRole(roleRepository.getById(2l));
//        this.userRepository.save(user);
//    }
}
