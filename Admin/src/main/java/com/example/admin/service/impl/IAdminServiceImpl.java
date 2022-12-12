package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Admin;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.service.IAdminService;
import com.example.commons.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    AdminMapper adminMapper;
    @Override
    public Student login(String adminId, String password) {
        return null;
    }
}
