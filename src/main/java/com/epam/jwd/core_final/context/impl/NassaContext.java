package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.CrewMemberAssignmentException;
import com.epam.jwd.core_final.exception.CrewMemberCreationException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.PropertiesLoadingException;
import com.epam.jwd.core_final.exception.SpaceshipAssignmentException;
import com.epam.jwd.core_final.exception.SpaceshipCreationException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.ConcreteSpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewMemberService;
import com.epam.jwd.core_final.util.EntityReader;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();
    private ApplicationProperties applicationProperties;
    private static NassaContext instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(NassaContext.class);

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        if (instance == null) {
            instance = new NassaContext();
        }
        return instance;
    }

    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> entityList = new ArrayList<>();
        if (tClass == CrewMember.class) {
            entityList = (ArrayList<T>) crewMembers;
        }
        if (tClass == Spaceship.class) {
            entityList = (ArrayList<T>) spaceships;
        }
        if (tClass == FlightMission.class) {
            entityList = (ArrayList<T>) flightMissions;
        }
        return entityList;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        try {
            PropertyReaderUtil.loadProperties();
            applicationProperties = PropertyReaderUtil.populateApplicationProperties();
            LOGGER.info("Properties read successfully");

            EntityReader.getInstance().readCrewMembersFromFile(applicationProperties);
            LOGGER.info("Crew members read successfully");

            EntityReader.getInstance().readSpaceshipsFromFile(applicationProperties);
            LOGGER.info("Spaceships read successfully");
        } catch (PropertiesLoadingException e) {
            throw new InvalidStateException(e.getMessage());
        } catch (CrewMemberCreationException | SpaceshipCreationException e) {
            LOGGER.error(e.toString());
        }
    }

    public MissionResult simulateFlightMission(FlightMission flightMission)
            throws SpaceshipAssignmentException, CrewMemberAssignmentException {
        if (flightMission.getMissionResult() == MissionResult.FAILED) {
            return MissionResult.FAILED;
        }

        fetchSpaceshipForMission(flightMission);
        if (generateMissionResult()) {
            flightMission.setMissionResult(MissionResult.COMPLETED);
        } else {
            failMissionAndCrew(flightMission);
        }
        return flightMission.getMissionResult();
    }

    private boolean generateMissionResult() {
        double successRate = Math.random() * 100;
        final int minimalSuccessRate = 50;
        return successRate > minimalSuccessRate;
    }

    private void failMissionAndCrew(FlightMission flightMission) {
        flightMission.getAssignedSpaceship().setReadyForNextMissions(false);
        for (CrewMember crewMember : flightMission.getAssignedCrew()) {
            crewMember.setReadyForNextMissions(false);
        }
        flightMission.setMissionResult(MissionResult.FAILED);
    }

    private void fetchSpaceshipForMission(FlightMission flightMission)
            throws SpaceshipAssignmentException, CrewMemberAssignmentException {
        if (spaceships.isEmpty()) {
            throw new SpaceshipAssignmentException("No spaceships for mission");
        }
        SpaceshipService spaceshipService = ConcreteSpaceshipService.getInstance();
        Spaceship spaceship = chooseRandomSpaceship();
        spaceshipService.assignSpaceshipOnMission(spaceship);
        flightMission.setAssignedCrew(completeTheCrew(spaceship));
        flightMission.setAssignedSpaceship(spaceship);
    }

    private Spaceship chooseRandomSpaceship() {
        int spaceshipNumber = (int) (Math.random() * spaceships.size());
        return ((List<Spaceship>) spaceships).get(spaceshipNumber);
    }

    private List<CrewMember> completeTheCrew(Spaceship spaceship) throws CrewMemberAssignmentException {
        List<CrewMember> crewMembers = new ArrayList<>();
        int crewMembersNeed = 0;
        Map<Role, Short> crew = spaceship.getCrew();
        for (Role role : crew.keySet()) {
            for (int i = 0; i < crew.get(role); i++) {
                crewMembersNeed++;
                CrewMemberCriteria criteria = CrewMemberCriteria.builder().withRole(role).build();
                CrewService crewService = CrewMemberService.getInstance();
                Optional<CrewMember> crewMember = crewService.findCrewMemberByCriteria(criteria);
                if (crewMember.isPresent()) {
                    crewService.assignCrewMemberOnMission(crewMember.get());
                    crewMembers.add(crewMember.get());
                }
            }
        }
        if (crewMembersNeed == crewMembers.size()) {
            return crewMembers;
        } else {
            throw new CrewMemberAssignmentException("No crew members for such spaceship");
        }
    }
}
