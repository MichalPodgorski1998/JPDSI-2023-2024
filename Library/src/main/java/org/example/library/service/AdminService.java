package org.example.library.service;

import org.example.library.dto.AdminDto;
import org.example.library.model.Admin;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);
}
