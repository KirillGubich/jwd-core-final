package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.FlightMissionCreationException;
import com.epam.jwd.core_final.exception.FlightMissionUpdateException;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.MissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightMissionService extends BaseEntityService<FlightMission> implements MissionService {

    private static FlightMissionService instance;

    protected FlightMissionService() {
        super(FlightMission.class);
    }

    public static FlightMissionService getInstance() {
        if (instance == null) {
            instance = new FlightMissionService();
        }
        return instance;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return new ArrayList<>(super.findAll());
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        if (criteria == null) {
            return new ArrayList<>();
        }

        return findAllMissions().stream()
                .filter(Objects::nonNull)
                .filter(flightMission -> checkCoincidenceId(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceName(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceStartDate(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceEarliestStartDate(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceLatestStartDate(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceEndDate(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceEarliestEndDate(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceLatestEndDate(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceDistance(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceMinDistance(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceMaxDistance(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceAssignedCrew(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceAssignedSpaceship(flightMission, (FlightMissionCriteria) criteria)
                        && checkCoincidenceMissionResult(flightMission, (FlightMissionCriteria) criteria))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return findAllMissionsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) throws FlightMissionUpdateException {
        if (flightMission == null) {
            throw new FlightMissionUpdateException("Flight mission can't be update with such params");
        }

        FlightMissionCriteria criteria = FlightMissionCriteria.builder().withName(flightMission.getName()).build();
        Optional<FlightMission> flightMissionOptional = findMissionByCriteria(criteria);
        if (flightMissionOptional.isPresent()) {
            flightMissionOptional.get().setDistance(flightMission.getDistance());
            flightMissionOptional.get().setStartDate(flightMission.getStartDate());
            flightMissionOptional.get().setEndDate(flightMission.getEndDate());
        } else {
            throw new FlightMissionUpdateException("Can't found such flight mission");
        }
        return flightMissionOptional.get();
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws FlightMissionCreationException {
        if (flightMission == null) {
            throw new FlightMissionCreationException("can't be created");
        }

        FlightMissionCriteria flightMissionCriteria = FlightMissionCriteria.builder()
                .withName(flightMission.getName()).build();
        Optional<FlightMission> flightMissionOptional = findMissionByCriteria(flightMissionCriteria);
        if (flightMissionOptional.isPresent()) {
            throw new FlightMissionCreationException("trying to create a duplicate", flightMission.getName());
        }
        findAll().add(flightMission);
        return flightMission;
    }

    private boolean checkCoincidenceStartDate(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getStartDate() == null || criteria.getStartDate().equals(flightMission.getStartDate());
    }

    private boolean checkCoincidenceEarliestStartDate(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getEarliestStartDate() == null
                || criteria.getEarliestStartDate().compareTo(flightMission.getStartDate()) < 0;
    }

    private boolean checkCoincidenceLatestStartDate(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getLatestStartDate() == null
                || criteria.getLatestStartDate().compareTo(flightMission.getStartDate()) > 0;
    }

    private boolean checkCoincidenceEndDate(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getEndDate() == null || criteria.getEndDate().equals(flightMission.getEndDate());
    }

    private boolean checkCoincidenceEarliestEndDate(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getEarliestEndDate() == null
                || criteria.getEarliestEndDate().compareTo(flightMission.getEndDate()) < 0;
    }

    private boolean checkCoincidenceLatestEndDate(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getLatestEndDate() == null
                || criteria.getLatestEndDate().compareTo(flightMission.getEndDate()) > 0;
    }

    private boolean checkCoincidenceDistance(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getDistance() == null || criteria.getDistance().equals(flightMission.getDistance());
    }

    private boolean checkCoincidenceMinDistance(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getMinDistance() == null || criteria.getMinDistance() < flightMission.getDistance();
    }

    private boolean checkCoincidenceMaxDistance(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getMaxDistance() == null || criteria.getMaxDistance() > flightMission.getDistance();
    }

    private boolean checkCoincidenceAssignedSpaceship(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getAssignedSpaceship() == null
                || criteria.getAssignedSpaceship().equals(flightMission.getAssignedSpaceship());
    }

    private boolean checkCoincidenceAssignedCrew(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getAssignedCrew() == null
                || criteria.getAssignedCrew().equals(flightMission.getAssignedCrew());
    }

    private boolean checkCoincidenceMissionResult(FlightMission flightMission, FlightMissionCriteria criteria) {
        return criteria.getMissionResult() == null
                || criteria.getMissionResult().equals(flightMission.getMissionResult());
    }
}
