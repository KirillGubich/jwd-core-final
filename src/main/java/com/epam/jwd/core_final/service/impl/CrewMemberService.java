package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.CrewMemberAssignmentException;
import com.epam.jwd.core_final.exception.CrewMemberCreationException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.CrewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewMemberService extends BaseEntityService<CrewMember> implements CrewService {

    private static CrewMemberService instance;

    protected CrewMemberService() {
        super(CrewMember.class);
    }

    public static CrewMemberService getInstance() {
        if (instance == null) {
            instance = new CrewMemberService();
        }
        return instance;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return new ArrayList<>(super.findAll());
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        if (criteria == null) {
            return new ArrayList<>();
        }

        return findAllCrewMembers().stream()
                .filter(Objects::nonNull)
                .filter(crewMember -> checkCoincidenceId(crewMember, (CrewMemberCriteria) criteria)
                        && checkCoincidenceName(crewMember, (CrewMemberCriteria) criteria)
                        && checkCoincidenceRank(crewMember, (CrewMemberCriteria) criteria)
                        && checkCoincidenceRole(crewMember, (CrewMemberCriteria) criteria)
                        && checkCoincidenceIsReadyForNextMissions(crewMember, (CrewMemberCriteria) criteria))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) throws CrewMemberUpdateException {
        if (crewMember == null) {
            throw new CrewMemberUpdateException("Crew member can't be update with such params");
        }

        CrewMemberCriteria criteria = CrewMemberCriteria.builder().withName(crewMember.getName()).build();
        Optional<CrewMember> crewMemberOptional = findCrewMemberByCriteria(criteria);
        if (crewMemberOptional.isPresent()) {
            crewMemberOptional.get().setRank(crewMember.getRank());
        } else {
            throw new CrewMemberUpdateException("Can't found such crew member");
        }
        return crewMemberOptional.get();
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws CrewMemberAssignmentException {
        if (crewMember == null) {
            throw new CrewMemberAssignmentException("Crew member can't be assigned on mission");
        }
        if (!crewMember.getReadyForNextMissions()) {
            throw new CrewMemberAssignmentException("Crew member is not ready for next missions");
        }
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws CrewMemberCreationException {
        if (crewMember == null) {
            throw new CrewMemberCreationException("can't be created");
        }
        CrewMemberCriteria nameCriteria = CrewMemberCriteria.builder()
                .withName(crewMember.getName()).build();
        if (findCrewMemberByCriteria(nameCriteria).isPresent()) {
            throw new CrewMemberCreationException("trying to create a duplicate", crewMember.getName());
        }

        findAll().add(crewMember);
        return crewMember;
    }

    private boolean checkCoincidenceRank(CrewMember crewMember, CrewMemberCriteria criteria) {
        return criteria.getRank() == null || criteria.getRank().equals(crewMember.getRank());
    }

    private boolean checkCoincidenceRole(CrewMember crewMember, CrewMemberCriteria criteria) {
        return criteria.getRole() == null || criteria.getRole().equals(crewMember.getRole());
    }

    private boolean checkCoincidenceIsReadyForNextMissions(CrewMember crewMember, CrewMemberCriteria criteria) {
        return criteria.getReadyForNextMissions() == null
                || criteria.getReadyForNextMissions().equals(crewMember.getReadyForNextMissions());
    }
}
