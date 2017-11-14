package com.teamtreehouse.instateam.DAO;

import com.teamtreehouse.instateam.model.Project;

import java.util.List;

// Interface for database CRUD operations
public interface ProjectDAO {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project project);
    void delete(Project project);
}
