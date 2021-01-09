package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.IdDispenser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class CrewMemberFactoryTest {

    private static CrewMemberFactory crewMemberFactory;
    private static final IdDispenser idDispenser = Mockito.mock(IdDispenser.class);

    @BeforeClass
    public static void beforeClass() {
        crewMemberFactory = CrewMemberFactory.getInstance();
    }

    @Test
    public void getInstance_receiveLinkOnObject_whenRequireIt() {
        assertEquals(CrewMemberFactory.class, crewMemberFactory.getClass());
    }

    @Test
    public void create_newCrewMember_whenTryToCreateIt() {
        String name = "Test_Name";
        Role role = Role.MISSION_SPECIALIST;
        Rank rank = Rank.TRAINEE;
        Mockito.when(idDispenser.getNextId()).thenReturn(1L);
        CrewMember crewMember = crewMemberFactory.create(name, role, rank);
        assertEquals(CrewMember.class, crewMember.getClass());
    }
}