package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Main {
    private static final int SIMULATIONS = 1000;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        HashMap<Integer, String> results = new HashMap<>();
        int winSwitch = 0;
        int winStay = 0;

        for (int i = 0; i < SIMULATIONS; i++) {
            boolean isWinSwitch = playGame(true);
            boolean isWinStay = playGame(false);

            if (isWinSwitch) {
                winSwitch++;
                results.put(i, "The player won with the choice!");
            } else if (isWinStay) {
                winStay++;
                results.put(i, "The player won without a choice!");
            } else {
                results.put(i, "Loss!");
            }
        }

        writeResultsToFile(winSwitch, winStay, SIMULATIONS);


    }

    private static boolean playGame(boolean switchChoice) {
        int carPosition = RANDOM.nextInt(3);
        int playerChoice = RANDOM.nextInt(3);
        int door;

        do {
            door = RANDOM.nextInt(3);
        } while (door == carPosition || door == playerChoice);

        if (switchChoice) {
            for (int i = 0; i < 3; i++) {
                if (i != playerChoice || i != door) {
                    playerChoice = i;
                    break;
                }
            }
        }
        return playerChoice == carPosition;
    }

    private static void writeResultsToFile(int winsSwitch, int winsStay, int simulations) {
        String fileName = "monty_hall_results.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("=== Статистика по результатам игры ===\n");
            writer.write("Победы при смене выбора: " + winsSwitch + "\n");
            writer.write("Победы при оставлении выбора: " + winsStay + "\n");
            writer.write("Процент побед при смене: " + (winsSwitch * 100.0 / simulations) + "%\n");
            writer.write("Процент побед при оставлении: " + (winsStay * 100.0 / simulations) + "%\n");

            System.out.println("Результаты записаны в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}