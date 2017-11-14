package com.teamtreehouse.instateam.DAO;

import com.teamtreehouse.instateam.model.Collaborator;

import java.util.List;

// Interface for database CRUD operations
public interface CollaboratorDAO {
    List<Collaborator> findAll();
    Collaborator findById(Long id);
    void save(Collaborator collaborator);
    void delete(Collaborator collaborator);
}
