package com.teamtreehouse.instateam.converter;

import com.teamtreehouse.instateam.DAO.RoleDAO;
import com.teamtreehouse.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// Class that converts string input from thymeleaf templates into usable java objects.
// Thanks Livia!
@Component
public class StringRoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public Role convert(String id) {
        return roleDAO.findById(Long.valueOf(id));
    }
}
