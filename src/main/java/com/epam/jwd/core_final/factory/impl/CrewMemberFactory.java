package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.IdDispenser;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    private final IdDispenser idDispenser = EntityIdDispenser.getInstance();
    private static CrewMemberFactory instance;

    private CrewMemberFactory() {
    }

    public static CrewMemberFactory getInstance() {
        if (instance == null) {
            instance = new CrewMemberFactory();
        }
        return instance;
    }

    @Override
    public CrewMember create(Object... args) {
        return new CrewMember(idDispenser.getNextId(),
                (String) args[0],
                (Role) args[1],
                (Rank) args[2]);
    }
}
