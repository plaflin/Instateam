package com.teamtreehouse.instateam.DAO;

import com.teamtreehouse.instateam.model.Role;

import java.util.List;

// Interface for database CRUD operations
public interface RoleDAO {
    List<Role> findAll();
    Role findById(Long id);
    void save(Role role);
    void delete(Role role);
}
