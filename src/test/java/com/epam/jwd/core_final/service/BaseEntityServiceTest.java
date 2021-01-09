package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.CrewMemberService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseEntityServiceTest {
    private static CrewMember crewMember;

    @BeforeClass
    public static void beforeClass() throws Exception {
        String name = "Test_Name";
        Role role = Role.MISSION_SPECIALIST;
        Rank rank = Rank.TRAINEE;
        crewMember = CrewMemberFactory.getInstance().create(name, role, rank);
    }

    @Test
    public void checkCoincidenceId() {
        Criteria<CrewMember> criteria = CrewMemberCriteria.builder().withId(0L).build();
        boolean actual = CrewMemberService.getInstance().checkCoincidenceId(crewMember, criteria);
        assertFalse(actual);
    }

    @Test
    public void checkCoincidenceName() {
        Criteria<CrewMember> criteria = CrewMemberCriteria.builder().withName("Not_Test_Name").build();
        boolean actual = CrewMemberService.getInstance().checkCoincidenceName(crewMember, criteria);
        assertFalse(actual);
    }
}