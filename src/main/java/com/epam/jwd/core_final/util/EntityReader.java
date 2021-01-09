package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.CrewMemberCreationException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.SpaceshipCreationException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.ConcreteSpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewMemberService;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class EntityReader {

    private static EntityReader instance;
    private final CrewService crewService = CrewMemberService.getInstance();
    private final SpaceshipService spaceshipService = ConcreteSpaceshipService.getInstance();

    private EntityReader() {
    }

    public static EntityReader getInstance() {
        if (instance == null) {
            instance = new EntityReader();
        }
        return instance;
    }

    public void readCrewMembersFromFile(ApplicationProperties applicationProperties) throws InvalidStateException,
            CrewMemberCreationException {
        File crewMembersFile = Paths.get(applicationProperties.getInputRootDir(),
                applicationProperties.getCrewFileName()).toFile();
        try (Scanner scanner = new Scanner(crewMembersFile)) {
            scanner.nextLine();
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                crewService.createCrewMember(parseCrewMember(scanner.next()));
            }
        } catch (FileNotFoundException e) {
            throw new InvalidStateException("CrewFile not found");
        }
    }

    public void readSpaceshipsFromFile(ApplicationProperties applicationProperties) throws InvalidStateException {
        File spaceshipsFile = Paths.get(applicationProperties.getInputRootDir(),
                applicationProperties.getSpaceshipsFileName()).toFile();
        try (Scanner scanner = new Scanner(spaceshipsFile)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.startsWith("#")) {
                    spaceshipService.createSpaceship(EntityReader.getInstance().parseSpaceship(line));
                }
            }
        } catch (FileNotFoundException e) {
            throw new InvalidStateException("SpaceshipsFile not found");
        }
    }

    private CrewMember parseCrewMember(String crewMemberInfo) throws InvalidStateException, CrewMemberCreationException {
        Scanner scanner = new Scanner(crewMemberInfo);
        scanner.useDelimiter("\\s*,\\s*");
        try {
            Role role = Role.resolveRoleById(scanner.nextInt());
            String name = scanner.next();
            Rank rank = Rank.resolveRankById(scanner.nextInt());
            return CrewMemberFactory.getInstance().create(name, role, rank);
        }  catch (InputMismatchException | UnknownEntityException e) {
            throw new InvalidStateException("CrewMember input mismatch");
        }
    }

    private Spaceship parseSpaceship(String spaceshipInfo) throws InvalidStateException, SpaceshipCreationException {
        Scanner scanner = new Scanner(spaceshipInfo);
        scanner.useDelimiter(";");
        try {
            String name = scanner.next();
            Long distance = scanner.nextLong();
            Map<Role, Short> crew = parseCrew(scanner.next());
            return SpaceshipFactory.getInstance().create(name, crew, distance);
        } catch (InputMismatchException | UnknownEntityException e) {
            throw new InvalidStateException("Spaceship input mismatch");
        }
    }

    private Map<Role, Short> parseCrew(String crewInfo) throws InvalidStateException {
        Scanner scanner = createCrewScanner(crewInfo);
        Map<Role, Short> crew = new HashMap<>();
        final int crewCount = 4;

        for (int i = 0; i < crewCount; i++) {
            try {
                Role role = Role.resolveRoleById(scanner.nextInt());
                Short roleCount = scanner.nextShort();
                crew.put(role, roleCount);
            } catch (InputMismatchException | UnknownEntityException e) {
                throw new InvalidStateException("Crew input mismatch");
            }
        }
        return crew;
    }

    private Scanner createCrewScanner(String crewInfo) {
        crewInfo = crewInfo.replace("{", "");
        crewInfo = crewInfo.replace("}", "");
        crewInfo = crewInfo.replace(":", ",");
        Scanner scanner = new Scanner(crewInfo);
        scanner.useDelimiter("\\s*,\\s*");
        return scanner;
    }
}
