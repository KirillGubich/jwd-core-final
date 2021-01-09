package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.SpaceshipAssignmentException;
import com.epam.jwd.core_final.exception.SpaceshipCreationException;
import com.epam.jwd.core_final.exception.SpaceshipUpdateException;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConcreteSpaceshipService extends BaseEntityService<Spaceship> implements SpaceshipService {

    private static ConcreteSpaceshipService instance;

    public ConcreteSpaceshipService() {
        super(Spaceship.class);
    }

    public static ConcreteSpaceshipService getInstance() {
        if (instance == null) {
            instance = new ConcreteSpaceshipService();
        }
        return instance;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return new ArrayList<>(super.findAll());
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        if (criteria == null) {
            return new ArrayList<>();
        }

        return findAllSpaceships().stream()
                .filter(Objects::nonNull)
                .filter(spaceship -> checkCoincidenceId(spaceship, (SpaceshipCriteria) criteria)
                        && checkCoincidenceName(spaceship, (SpaceshipCriteria) criteria)
                        && checkCoincidenceCrew(spaceship, (SpaceshipCriteria) criteria)
                        && checkCoincidenceFlightDistance(spaceship, (SpaceshipCriteria) criteria)
                        && checkCoincidenceMinFlightDistance(spaceship, (SpaceshipCriteria) criteria)
                        && checkCoincidenceMaxFlightDistance(spaceship, (SpaceshipCriteria) criteria)
                        && checkCoincidenceIsReadyForNextMissions(spaceship, (SpaceshipCriteria) criteria))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return findAllSpaceshipsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) throws SpaceshipUpdateException {
        if (spaceship == null) {
            throw new SpaceshipUpdateException("Spaceship can't be update with such params");
        }

        SpaceshipCriteria criteria = SpaceshipCriteria.builder().withName(spaceship.getName()).build();
        Optional<Spaceship> spaceshipOptional = findSpaceshipByCriteria(criteria);
        if (spaceshipOptional.isPresent()) {
            spaceshipOptional.get().setFlightDistance(spaceship.getFlightDistance());
        } else {
            throw new SpaceshipUpdateException("Can't found such spaceship");
        }
        return spaceshipOptional.get();
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws SpaceshipAssignmentException {
        if (spaceship == null) {
            throw new SpaceshipAssignmentException("Spaceship can't be assigned on mission");
        }
        if (!spaceship.getReadyForNextMissions()) {
            throw new SpaceshipAssignmentException("Spaceship isn't ready for next missions");
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws SpaceshipCreationException {
        if (spaceship == null) {
            throw new SpaceshipCreationException("Spaceship can't be created");
        }

        findAll().add(spaceship);
        return spaceship;
    }

    private boolean checkCoincidenceCrew(Spaceship spaceship, SpaceshipCriteria criteria) {
        return criteria.getCrew() == null || criteria.getCrew().equals(spaceship.getCrew());
    }

    private boolean checkCoincidenceFlightDistance(Spaceship spaceship, SpaceshipCriteria criteria) {
        return criteria.getFlightDistance() == null || criteria.getFlightDistance().equals(spaceship.getFlightDistance());
    }

    private boolean checkCoincidenceMinFlightDistance(Spaceship spaceship, SpaceshipCriteria criteria) {
        return criteria.getMinFlightDistance() == null || spaceship.getFlightDistance() > criteria.getMinFlightDistance();
    }

    private boolean checkCoincidenceMaxFlightDistance(Spaceship spaceship, SpaceshipCriteria criteria) {
        return criteria.getMinFlightDistance() == null || spaceship.getFlightDistance() < criteria.getMaxFlightDistance();
    }

    private boolean checkCoincidenceIsReadyForNextMissions(Spaceship spaceship, SpaceshipCriteria criteria) {
        return criteria.getReadyForNextMissions() == null
                || criteria.getReadyForNextMissions().equals(spaceship.getReadyForNextMissions());
    }
}
