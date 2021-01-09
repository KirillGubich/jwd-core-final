package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.CrewMemberAssignmentException;
import com.epam.jwd.core_final.exception.CrewMemberCreationException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.CrewService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrewMemberServiceTest {

    private static CrewService crewMemberService;
    private static CrewMember crewMember;

    @BeforeClass
    public static void beforeClass() {
        crewMemberService = CrewMemberService.getInstance();
        String name = "Test_Name";
        Role role = Role.MISSION_SPECIALIST;
        Rank rank = Rank.TRAINEE;
        crewMember = CrewMemberFactory.getInstance().create(name, role, rank);
    }

    @Before
    public void setUp() {
        crewMember.setReadyForNextMissions(true);
    }

    @Test
    public void getInstance_receiveLinkOnObject_whenRequireIt() {
        assertEquals(CrewMemberService.class, crewMemberService.getClass());
    }

    @Test
    public void findAllCrewMembersByCriteria_emptyList_whenCriteriaIsNull() {
        List<CrewMember> crewMembers = crewMemberService.findAllCrewMembersByCriteria(null);
        assertTrue(crewMembers.isEmpty());
    }

    @Test
    public void findCrewMemberByCriteria_EmptyOptional_whenCriteriaIsNull() {
        Optional<CrewMember> crewMember = crewMemberService.findCrewMemberByCriteria(null);
        assertFalse(crewMember.isPresent());
    }

    @Test(expected = CrewMemberUpdateException.class)
    public void updateCrewMemberDetails_throwException_whenCrewMemberIsNull() {
        crewMemberService.updateCrewMemberDetails(null);
    }

    @Test(expected = CrewMemberAssignmentException.class)
    public void assignCrewMemberOnMission_throwException_whenParamIsNull() {
        crewMemberService.assignCrewMemberOnMission(null);
    }

    @Test(expected = CrewMemberAssignmentException.class)
    public void assignCrewMemberOnMission_throwException_whenIsNotReadyForNextMissions() {
        crewMember.setReadyForNextMissions(false);
        crewMemberService.assignCrewMemberOnMission(crewMember);
    }

    @Test(expected = CrewMemberCreationException.class)
    public void createCrewMember_throwException_whenParamIsNull() {
        crewMemberService.createCrewMember(null);
    }
}