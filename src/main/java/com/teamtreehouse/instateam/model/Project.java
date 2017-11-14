package com.teamtreehouse.instateam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// POJO class to hold the information for what makes up a project
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 140)
    private String projectName;

    @NotNull
    @Size(min = 1, max =280)
    private String projectDescription;

    private String projectStatus;

    // Many To Many annotation to show that many projects can need the same roles
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> rolesNeeded = new ArrayList<>();

    // Many to Many annotation to show that many projects share collaborators
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Collaborator> collaborators = new ArrayList<>();

    @Column(updatable = false)
    private Date dateCreated;


    public Project() {}

    public void removeCollaborator(Collaborator collaborator) {
        collaborators.remove(collaborator);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
