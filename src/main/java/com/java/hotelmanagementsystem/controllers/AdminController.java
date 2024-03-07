package com.java.hotelmanagementsystem.controllers;

import com.java.hotelmanagementsystem.models.dto.User.RoleRequest;
import com.java.hotelmanagementsystem.models.dto.User.TokenTypeRequest;
import com.java.hotelmanagementsystem.responses.Response;
import com.java.hotelmanagementsystem.responses.SuccessResponse;
import com.java.hotelmanagementsystem.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/addRole")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> addRole(@RequestBody RoleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(adminService.addRole(request.getRole())));
    }

    @PostMapping("/addTokenType")
    public ResponseEntity<Response> addTokenType(@RequestBody TokenTypeRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(adminService.addTokenType(request.getTokenType())));
    }

}
