package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.DAO.CollaboratorDAO;
import com.teamtreehouse.instateam.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Implementation of service level CRUD operations
@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    @Autowired
    private CollaboratorDAO collaboratorDAO;

    @Override
    public List<Collaborator> findAll() {
        return collaboratorDAO.findAll();
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorDAO.findById(id);
    }

    @Override
    public void save(Collaborator collaborator) {
        collaboratorDAO.save(collaborator);
    }

    @Override
    public void delete(Collaborator collaborator) {
        collaboratorDAO.delete(collaborator);
    }
}
