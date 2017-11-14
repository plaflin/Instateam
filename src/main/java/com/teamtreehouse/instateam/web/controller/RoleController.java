package com.teamtreehouse.instateam.web.controller;

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
import java.util.List;

// Class controls the routes and operations for the role part of this project.
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CollaboratorService collaboratorService;

    // Function handles the route to show the list of roles
    @RequestMapping("/roles")
    public String listRoles(Model model) {

        List<Role> roleList = roleService.findAll();

        model.addAttribute("roles", roleList);

        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new Role());
        }

        return "role/roles";
    }

    // Function handles the route to add a new role to the database
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid input", FlashMessage.Status.FAILURE));
            return "redirect:/roles";
        }
        roleService.save(role);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Successfully added role.", FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }

    // Function handles the route to show the role that the user wants to update
    @RequestMapping("/roles/{id}/edit")
    public String editRole(@PathVariable Long id, Model model) {

        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("action", String.format("/roles/%s/edit", id));
        model.addAttribute("delete", String.format("/roles/%s/delete", id));

        return "role/detail";
    }

    // Function handles the route to update the role in the database
    @RequestMapping(value = "/roles/{id}/edit", method = RequestMethod.POST)
    public String changeRole(@Valid Role role, BindingResult result,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Could not update.", FlashMessage.Status.FAILURE));
            return "redirect:/roles";
        }

        roleService.save(role);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Successfully updated.", FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }

    // Function handles the route to delete unwanted roles from the database
    @RequestMapping(value = "/roles/{id}/delete")
    public String deleteRole(@PathVariable Long id) {
        Role role = roleService.findById(id);

        // TODO: SG Add delete

        return "redirect:/roles";
    }

}
