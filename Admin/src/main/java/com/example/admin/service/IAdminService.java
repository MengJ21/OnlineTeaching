package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Admin;
import com.example.commons.entity.Student;

public interface IAdminService extends IService<Admin> {
    Student login(String adminId, String password);
}
