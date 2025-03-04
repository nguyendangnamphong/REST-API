package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/resources")
    public String create() {
        return "Admin created a new resource.";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/resources/{id}")
    public String update(@PathVariable int id) {
        return "Admin updated resource with id " + id;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/resources/{id}")
    public String delete(@PathVariable int id) {
        return "Admin deleted resource with id " + id;
    }
}