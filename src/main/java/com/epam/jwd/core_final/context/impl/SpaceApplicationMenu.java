package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.ApplicationStatus;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.CrewMemberAssignmentException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import com.epam.jwd.core_final.exception.FlightMissionUpdateException;
import com.epam.jwd.core_final.exception.SpaceshipAssignmentException;
import com.epam.jwd.core_final.exception.SpaceshipUpdateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.impl.ConcreteSpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewMemberService;
import com.epam.jwd.core_final.service.impl.FlightMissionService;
import com.epam.jwd.core_final.util.UserInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class SpaceApplicationMenu implements ApplicationMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceApplicationMenu.class);
    private static SpaceApplicationMenu instance;
    private final NassaContext nassaContext = NassaContext.getInstance();

    private SpaceApplicationMenu() {
    }

    public static SpaceApplicationMenu getInstance() {
        if (instance == null) {
            instance = new SpaceApplicationMenu();
        }
        return instance;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return nassaContext;
    }

    @Override
    public ApplicationStatus handleUserInput(String userInput) {
        ApplicationStatus applicationStatus = ApplicationStatus.IN_PROGRESS;

        switch (userInput) {
            case "1": {
                printCrewMembers(nassaContext.retrieveBaseEntityList(CrewMember.class));
                break;
            }
            case "2": {
                updateCrewMember();
                break;
            }
            case "3": {
                printSpaceships(nassaContext.retrieveBaseEntityList(Spaceship.class));
                break;
            }
            case "4": {
                updateSpaceship();
                break;
            }
            case "5": {
                createMission();
                break;
            }
            case "6": {
                updateMission();
                break;
            }
            case "7": {
                runMission();
                break;
            }
            case "8": {
                saveMissionsToFile(nassaContext.retrieveBaseEntityList(FlightMission.class));
                break;
            }
            case "0": {
                applicationStatus = ApplicationStatus.COMPLETED;
                break;
            }
            default:
                System.out.printf("%s \n\n", "Unknown command. Please try again");
        }
        return applicationStatus;
    }

    private void printCrewMembers(Collection<CrewMember> crewMembers) {
        System.out.println("Crew members: ");
        for (CrewMember crewMember : crewMembers) {
            System.out.println(crewMember);
        }
        System.out.println();
    }

    private void updateCrewMember() {
        System.out.println("Enter name of crew member you want to update ");
        String name = UserInput.getStringFromConsole();
        System.out.println("Enter rank id you want to assign: ");
        try {
            Rank rank = Rank.resolveRankById(UserInput.getIntFromConsole());
            CrewMember updCrewMember = CrewMemberFactory.getInstance().create(name, null, rank);
            CrewMemberService.getInstance().updateCrewMemberDetails(updCrewMember);
            System.out.printf("%s \n\n", "Crew member has been successfully updated");
        } catch (UnknownEntityException | CrewMemberUpdateException e) {
            System.out.printf("%s \n\n", e.getMessage());
        }
    }

    private void updateSpaceship() {
        System.out.println("Enter name of spaceship you want to update ");
        String name = UserInput.getStringFromConsole();
        System.out.println("Enter distance you want to assign: ");
        Long updDistance = UserInput.getLongFromConsole();
        try {
            Spaceship updSpaceship = SpaceshipFactory.getInstance().create(name, null, updDistance);
            ConcreteSpaceshipService.getInstance().updateSpaceshipDetails(updSpaceship);
            System.out.printf("%s \n\n", "Spaceship has been successfully updated");
        } catch (SpaceshipUpdateException e) {
            System.out.printf("%s \n\n", e.getMessage());
        }
    }

    private void updateMission() {
        System.out.println("Enter name of flight mission you want to update ");
        String name = UserInput.getStringFromConsole();
        System.out.println("Enter start date you want to assign: ");
        LocalDate startDate = UserInput.getLocalDateFromConsole();
        System.out.println("Enter end date you want to assign: ");
        LocalDate endDate = UserInput.getLocalDateFromConsole();
        System.out.println("Enter distance you want to assign: ");
        Long distance = UserInput.getLongFromConsole();
        try {
            FlightMission updMission = FlightMissionFactory.getInstance().create(name, startDate, endDate, distance);
            FlightMissionService.getInstance().updateMissionDetails(updMission);
            System.out.printf("%s \n\n", "FLight mission has been successfully updated");
        } catch (FlightMissionUpdateException e) {
            System.out.printf("%s \n\n", e.getMessage());
        }
    }

    private void printSpaceships(Collection<Spaceship> spaceships) {
        System.out.printf("\n %s \n", "Spaceships: ");
        for (Spaceship spaceship : spaceships) {
            System.out.println(spaceship);
        }
        System.out.println();
    }

    private void printFlightMissions(Collection<FlightMission> flightMissions) {
        System.out.printf("\n %s \n", "Flight missions");
        for (FlightMission flightMission : flightMissions) {
            System.out.println(flightMission);
        }
        System.out.println();
    }

    private void runMission() {
        Collection<FlightMission> flightMissions = nassaContext.retrieveBaseEntityList(FlightMission.class);
        if (flightMissions.isEmpty()) {
            System.out.printf("%s \n\n", "No missions found");
            return;
        }
        FlightMission flightMission = chooseMission(flightMissions);
        if (flightMission.getMissionResult() != MissionResult.PLANNED) {
            System.out.printf("%s \n\n", "This mission can't be completed. (Check mission status)");
            return;
        }
        if (!confirmMission(flightMission)) {
            return;
        }
        MissionResult missionResult = MissionResult.PLANNED;
        try {
            Thread.sleep(2000);
            missionResult = nassaContext.simulateFlightMission(flightMission);
            System.out.println("Takeoff of the spaceship...");
            Thread.sleep(1000);
            System.out.println("Mission in progress ...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            LOGGER.error("Sleep error");
        } catch (SpaceshipAssignmentException e) {
            System.out.printf("%s \n\n", "Failed to pick up spaceship. Mission aborted.");
            flightMission.setMissionResult(MissionResult.CANCELLED);
            return;
        } catch (CrewMemberAssignmentException e) {
            System.out.printf("%s \n\n", "Failed to pick up crew. Mission aborted.");
            flightMission.setMissionResult(MissionResult.CANCELLED);
            return;
        }
        if (missionResult == MissionResult.COMPLETED) {
            System.out.println("Congratulations! Mission Complete");
        } else {
            System.out.println("Mission failed. Spaceship is destroyed. The crew was killed.");
        }
        System.out.println();
    }

    private boolean confirmMission(FlightMission flightMission) {
        System.out.println("Are you sure you want to start the mission? (Yes/No)");
        String userInput = UserInput.getStringFromConsole();
        while (!userInput.equals("Yes") && !userInput.equals("No")) {
            System.out.println("Incorrect data. Try again. ");
            userInput = UserInput.getStringFromConsole();
        }

        if (userInput.equals("Yes")) {
            System.out.println("Search for spaceship and crew...");
            flightMission.setMissionResult(MissionResult.IN_PROGRESS);
            return true;
        } else {
            System.out.printf("%s \n\n", "Mission canceled");
            flightMission.setMissionResult(MissionResult.CANCELLED);
            return false;
        }
    }

    private FlightMission chooseMission(Collection<FlightMission> flightMissions) {
        printFlightMissions(flightMissions);
        System.out.print("Choose mission (by ID): ");
        Long id = UserInput.getLongFromConsole();
        FlightMissionCriteria criteria = FlightMissionCriteria.builder().withId(id).build();
        MissionService missionService = FlightMissionService.getInstance();
        Optional<FlightMission> flightMission = missionService.findMissionByCriteria(criteria);
        while (!flightMission.isPresent()) {
            System.out.println("Incorrect data. Try again. ");
            id = UserInput.getLongFromConsole();
            criteria = FlightMissionCriteria.builder().withId(id).build();
            flightMission = missionService.findMissionByCriteria(criteria);
        }

        return flightMission.get();
    }

    private void saveMissionsToFile(Collection<FlightMission> flightMissions) {
        if (flightMissions.isEmpty()) {
            System.out.printf("%s \n\n", "No missions to safe");
            return;
        }
        System.out.println("1 - save all missions");
        System.out.println("2 - save selected mission");
        String userInput = UserInput.getStringFromConsole();
        while (!userInput.equals("1") && !userInput.equals("2")) {
            System.out.println("Incorrect data. Try again. ");
            userInput = UserInput.getStringFromConsole();
        }
        if (userInput.equals("1")) {
            outputMissions(flightMissions);
        } else {
            FlightMission selectedMission = chooseMission(flightMissions);
            Collection<FlightMission> missionList = new ArrayList<>();
            missionList.add(selectedMission);
            outputMissions(missionList);
        }
        System.out.printf("%s \n\n", "Saving was successful");
    }

    private void outputMissions(Collection<FlightMission> missions) {
        ObjectMapper mapper = new ObjectMapper();
        ApplicationProperties applicationProperties = nassaContext.getApplicationProperties();
        File missionsFile = Paths.get(applicationProperties.getOutputRootDir(),
                applicationProperties.getMissionsFileName()).toFile();
        try {
            mapper.writeValue(missionsFile, missions);
        } catch (IOException e) {
            System.out.println("Can't output missions in json file. Check application properties");
            LOGGER.error("Missions output failed");
        }
    }

    private void createMission() {
        System.out.println("Enter name: ");
        String name = UserInput.getStringFromConsole();
        System.out.println("Enter start date: ");
        LocalDate startDate = UserInput.getLocalDateFromConsole();
        System.out.println("Enter end date: ");
        LocalDate endDate = UserInput.getLocalDateFromConsole();
        System.out.println("Enter distance: ");
        Long distance = UserInput.getLongFromConsole();

        FlightMission mission = FlightMissionFactory.getInstance().create(name, startDate, endDate, distance);
        try {
            FlightMissionService.getInstance().createMission(mission);
        } catch (FlightMissionUpdateException e) {
            System.out.println(e.getMessage());
        }
        System.out.printf("%s \n\n", "Mission has been successfully created");
    }
}
