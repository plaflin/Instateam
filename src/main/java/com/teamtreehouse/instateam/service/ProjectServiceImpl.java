package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.DAO.ProjectDAO;
import com.teamtreehouse.instateam.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Implementation of service level CRUD operations
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    public List<Project> findAll() {
        return projectDAO.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectDAO.findById(id);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void delete(Project project) {
        projectDAO.delete(project);
    }
}
