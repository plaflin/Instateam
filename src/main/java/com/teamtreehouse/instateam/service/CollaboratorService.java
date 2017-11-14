package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.model.Collaborator;
import java.util.List;

// Service layer interface for database CRUD operations
public interface CollaboratorService {
    List<Collaborator> findAll();
    Collaborator findById(Long id);
    void save(Collaborator collaborator);
    void delete(Collaborator collaborator);
}
