package com.ltrlabs.client.ui;

import com.ltrlabs.client.ui.support.ConsoleColors;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component
public class UI {

    private static final Scanner sc = new Scanner(System.in);
    private static final ConsoleColors MESSAGE_COLOR = ConsoleColors.BLUE_BOLD_BRIGHT;
    private static final ConsoleColors ERROR_MESSAGE_COLOR = ConsoleColors.RED_BOLD_BRIGHT;
    private static final ConsoleColors PROMPT_COLOR = ConsoleColors.GREEN_BOLD_BRIGHT;
    private static final String PROMPT = "$=>:";

    public String getUserInput(String promptMessage) {
        displayMessage(promptMessage, MESSAGE_COLOR);
        System.out.print(PROMPT_COLOR + PROMPT + ConsoleColors.RESET);
        String input;
        try {
            input = sc.nextLine();
        } catch (NoSuchElementException e) {
            input = null;
        }
        displayLineBrake(1);
        return input;
    }

    public String getUserInput(Predicate<String> inputValidator, Supplier<String> promptMessage, Supplier<String> errorMessage) {
        String input;
        boolean askUser;
        do {
            System.out.println(MESSAGE_COLOR + promptMessage.get() + ConsoleColors.RESET);
            System.out.print(PROMPT_COLOR + PROMPT + ConsoleColors.RESET);
            try {
                input = sc.nextLine();
            } catch (NoSuchElementException e) {
                input = null;
            }
            askUser = !inputValidator.test(input);
            if (askUser) System.out.println(ERROR_MESSAGE_COLOR + errorMessage.get() + ConsoleColors.RESET);
        } while (askUser);
        displayLineBrake(1);
        return input;
    }

    public void displayMessage(String message, ConsoleColors color) {
        System.out.println(color + message + ConsoleColors.RESET);
        displayLineBrake(1);
    }

    public void displayLineBrake(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println();
        }
    }
}
