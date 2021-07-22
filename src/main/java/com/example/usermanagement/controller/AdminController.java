package com.example.usermanagement.controller;

import com.example.usermanagement.dto.SectionDTO;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.security.services.SectionService;
import com.example.usermanagement.security.services.UserService;
import com.example.usermanagement.ultil.UserExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    SectionService sectionService;

    List<UserDTO> listUserDto;
    @PostMapping("/admin/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportUserTable(HttpServletResponse response, @RequestBody List<UserDTO> list) throws IOException {
        System.out.println("vao dayy");
//        listUserDto = list;
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        System.out.println(response.getHeader(headerKey));
        UserExcelExporter excelExporter = new UserExcelExporter(list);
        excelExporter.export(response);

    }
//    @GetMapping("/admin/export")
//    public void exportData(HttpServletResponse response) throws IOException{
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        UserExcelExporter excelExporter = new UserExcelExporter(listUserDto);
//        excelExporter.export(response);
//    }

    @PutMapping("/admin/update")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(@RequestBody UserDTO userDTO) throws Exception {

        this.userService.save(userDTO);
    }
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void createUser(@RequestBody UserDTO userDTO) throws  Exception{
        this.userService.save(userDTO);
    }
    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable(name = "id") Long id) throws Exception{
        this.userService.delete(id);
    }

    @PutMapping("/admin/section/update")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateSection(@RequestBody SectionDTO sectionDTO){
        this.sectionService.saveSection(sectionDTO);
    }

    @PostMapping("/admin/section/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void createSection(@RequestBody SectionDTO sectionDTO){
        this.sectionService.saveSection(sectionDTO);
    }

    @DeleteMapping("/admin/section/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSection(@PathVariable(name = "id") Long id){
        this.sectionService.deleteSectionById(id);
    }
}
