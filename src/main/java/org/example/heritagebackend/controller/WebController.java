package org.example.heritagebackend.controller;

import jakarta.annotation.Resource;
import org.example.heritagebackend.common.Result;
import org.example.heritagebackend.entity.Account;
import org.example.heritagebackend.entity.Employee;
import org.example.heritagebackend.exception.CustomException;
import org.example.heritagebackend.service.AdminService;
import org.example.heritagebackend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @Resource
    private EmployeeService employeeService;
    @Resource
    private AdminService adminService;

    @GetMapping("/hello")
    public Result hello() {
        return Result.success("Hello World");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account result = null;
        if ("ADMIN".equals(account.getRole())) {
            result = adminService.login(account);
        } else if ("EMP".equals(account.getRole())) {
            result = employeeService.login(account);
        } else {
            throw new CustomException("500", "非法输入");
        }
        return Result.success(result);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Employee employee) {
        employeeService.register(employee);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if ("EMP".equals(account.getRole())) {
            employeeService.updatePassword(account);
        } else {
            throw new CustomException("500", "非法输入");
        }
        return Result.success();
    }
}
