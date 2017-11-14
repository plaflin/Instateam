package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.DAO.RoleDAO;
import com.teamtreehouse.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Implementation of service level CRUD operations
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<Role> findAll() {
        return roleDAO.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleDAO.findById(id);
    }

    @Override
    public void save(Role role) {
        roleDAO.save(role);
    }

    @Override
    public void delete(Role role) {
        roleDAO.delete(role);
    }

}
