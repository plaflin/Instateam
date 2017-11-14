package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Class controls the routes and operations for the project part of this project which is the main concern.
@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    // Function handles the route to show the home page
    @RequestMapping("/")
    public String projectIndex(Model model) {

        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);

        return "index";
    }

    // Function handles the route to show a the user the screen to make a new project
    @RequestMapping("/new_project")
    public String newProject(Model model) {

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", new Project());
        }
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("button", "Save");
        model.addAttribute("action", "/new_project");
        model.addAttribute("cancel", "/");

        return "project/edit";
    }

    // Function handles the route to add a new project to the database
    @RequestMapping(value = "/new_project", method = RequestMethod.POST)
    public String saveProject(@Valid Project project,
                              BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Fill out the necessary fields.", FlashMessage.Status.FAILURE));
            return "redirect:/new_project";
        }

        project.setDateCreated(Date.from(Instant.now()));
        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Successfully created new project", FlashMessage.Status.SUCCESS));

        return "redirect:/";
    }

    // Function handles the route to show a project's detail page
    @RequestMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {

        Project project = projectService.findById(id);
        Map<Role, Collaborator> rolesAssignment = getRoleCollaboratorMap(project);

        model.addAttribute("project", project);
        model.addAttribute("rolesAssignment", rolesAssignment);

        return "project/detail";
    }

    // Function handles the route to show a collaborator that someone might want to update
    @RequestMapping("/projects/{id}/editProject")
    public String selectedProject(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", projectService.findById(id));
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", String.format("/projects/%s", id));
        model.addAttribute("button", "Update");
        model.addAttribute("cancel", String.format("/projects/%s", id));

        return "project/edit";
    }

    // Function handles the route to update a project
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.POST)
    public String editProject(@Valid Project project,
                              @PathVariable Long id, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Please provide the right information", FlashMessage.Status.FAILURE));
            return String.format("redirect:/projects/%s", id);
        }

        projectService.save(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project successfully updated.", FlashMessage.Status.SUCCESS));

        return "redirect:/projects/{id}";
    }

    // Function handles the route to show the collaborators assigned to a project
    @RequestMapping("/projects/{id}/editCollaborators")
    public String editCollaborators(Model model, @PathVariable Long id) {

        Project project = projectService.findById(id);

        model.addAttribute("project", project);
        model.addAttribute("cancel", String.format("/projects/%s", id));

        return "project/collabs";
    }

    // Function handles the route to assign the collaborators to the project in memory
    @RequestMapping(value = "/projects/{id}/editCollaborators", method = RequestMethod.POST)
    public String assignCollaborators(@PathVariable Long id, Project project,
                                      BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("Please insert proper name. For collaborator.");
            return String.format("/projects/%s/editCollaborators", id);
        }

        Project projectForSaving = projectService.findById(id);
        projectForSaving.setCollaborators(project.getCollaborators());

        projectService.save(projectForSaving);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborators successfully assigned.", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/projects/{id}", project.getId());
    }

    // This function maps roles to collaborators so you can see the roles next to their names when assigning them.
    public Map<Role, Collaborator> getRoleCollaboratorMap(Project project) {
        List<Role> rolesNeeded = project.getRolesNeeded();
        List<Collaborator> collaborators = project.getCollaborators();

        Map<Role, Collaborator> roleCollaborator = new LinkedHashMap<>();

        for (Role roleNeeded : rolesNeeded) {
            roleCollaborator.put(roleNeeded,
                    collaborators.stream()
                            .filter(collaborator -> collaborator.getRole().getId().equals(roleNeeded.getId()))
                            .findFirst()
                            .orElseGet(() -> {
                                Collaborator unassigned = new Collaborator();
                                unassigned.setName("Unassigned");
                                return unassigned;
                            }));
        }
        return roleCollaborator;
    }

}
