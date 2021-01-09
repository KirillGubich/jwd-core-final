package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.context.impl.NassaContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class UserInput {

    private UserInput() {
    }

    public static String getStringFromConsole() {
        Scanner consoleInput = new Scanner(System.in);
        System.out.print(">> ");
        return consoleInput.nextLine();
    }

    public static int getIntFromConsole() {
        Scanner consoleInput = new Scanner(System.in);
        int value;
        while (!consoleInput.hasNextInt()) {
            consoleInput.next();
            System.out.println("Incorrect data. Try again. ");
            System.out.print(">> ");
        }
        value = consoleInput.nextInt();
        return value;
    }

    public static Long getLongFromConsole() {
        Scanner consoleInput = new Scanner(System.in);
        long value;
        while (!consoleInput.hasNextLong()) {
            consoleInput.next();
            System.out.println("Incorrect data. Try again. ");
            System.out.print(">> ");
        }
        value = consoleInput.nextLong();
        return value;
    }

    public static LocalDate getLocalDateFromConsole() {
        Scanner consoleInput = new Scanner(System.in);
        String customFormat = NassaContext.getInstance().getApplicationProperties().getDateTimeFormat();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
        String userInput = consoleInput.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(userInput, formatter);
        } catch (DateTimeParseException e) {
            date = LocalDate.now();
        }
        return date;
    }
}
