
package com.teamtreehouse.instateam.converter;

import com.teamtreehouse.instateam.DAO.CollaboratorDAO;
import com.teamtreehouse.instateam.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// Class that converts string input from thymeleaf templates into usable java objects.
// Thanks Livia!
@Component
public class StringCollaboratorConverter implements Converter<String, Collaborator> {

    @Autowired
    private CollaboratorDAO collaboratorDAO;

    @Override
    public Collaborator convert(String id) {
        return collaboratorDAO.findById(Long.valueOf(id));
    }
}

