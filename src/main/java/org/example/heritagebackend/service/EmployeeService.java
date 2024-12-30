package org.example.heritagebackend.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.heritagebackend.entity.Account;
import org.example.heritagebackend.entity.Admin;
import org.example.heritagebackend.entity.Employee;
import org.example.heritagebackend.exception.CustomException;
import org.example.heritagebackend.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    public List<Employee> selectAll(Employee employee) {
        return employeeMapper.selectAll(employee);
    }

    public Employee selectById(Integer id) {
        return employeeMapper.selectById(id);
    }

    public PageInfo<Employee> selectPage(Employee employee, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> employees = employeeMapper.selectAll(employee);
        return PageInfo.of(employees);
    }

    public void add(Employee employee) {
        String username = employee.getUsername();
        Employee dbEmployee = employeeMapper.selectByUsername(username);
        if (dbEmployee != null) {
            throw new CustomException("500", "账号已存在");
        }
        if (StrUtil.isBlank(employee.getPassword())) {
            employee.setPassword("123");
        }
        if (StrUtil.isBlank(employee.getName())) {
            employee.setName(employee.getUsername());
        }
        employee.setRole("EMP");
        employeeMapper.add(employee);
    }

    public void update(Employee employee) {
        employeeMapper.updateById(employee);
    }

    public void deleteById(Integer id) {
        employeeMapper.deleteById(id);
    }

    public Employee login(Account account) {
        String username = account.getUsername();
        Employee dbEmployee = employeeMapper.selectByUsername(username);
        if (dbEmployee == null) {
            throw new CustomException("500", "账号不存在");
        }
        String password = account.getPassword();
        if (!password.equals(dbEmployee.getPassword())) {
            throw new CustomException("500", "账号或密码错误");
        }
        return dbEmployee;
    }

    public void register(Employee employee) {
        this.add(employee);
    }

    public void updatePassword(Account account) {
        Integer id = account.getId();
        Employee dbEmployee = employeeMapper.selectById(id);
        if (!dbEmployee.getPassword().equals(account.getPassword())) {
            throw new CustomException("500", "原密码错误");
        }
        dbEmployee.setPassword(account.getNewPassword());
        employeeMapper.updateById(dbEmployee);
    }
}
