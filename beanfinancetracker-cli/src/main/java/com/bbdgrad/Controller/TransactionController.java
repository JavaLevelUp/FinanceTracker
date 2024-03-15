package com.bbdgrad.controller;

import com.bbdgrad.Main;
import com.bbdgrad.model.Batch;
import com.bbdgrad.model.BeanAccount;
import com.bbdgrad.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.bbdgrad.Main.prop;

public class TransactionController {

    public static void createTransaction() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account id: ");
            int accountId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter batch id (optional): ");
            String batchId = scanner.nextLine();
            if (!batchId.isBlank()) {
                batchId = "" + Integer.parseInt(batchId);
            }
            System.out.print("Enter category id: ");
            int categoryId = Integer.parseInt(scanner.nextLine());
            System.out.print("Is this transaction outgoing? (y/n): ");
            String input = scanner.nextLine();
            boolean isOutgoing;
            isOutgoing = input.equals("y");
            System.out.print("Enter transaction amount: ");
            BigDecimal amount = BigDecimal.valueOf(Float.parseFloat(scanner.nextLine()));
            System.out.print("Enter transaction date (yyyy-MM-dd HH:mm): ");
            String transaction_date = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(transaction_date, formatter);

            Transaction transaction = new Transaction(accountId, batchId, categoryId, isOutgoing, amount, transaction_date);
            String jsonBody = new Gson().toJson(transaction);
            transaction = null;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/transaction"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .build();
            jsonBody = null;

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully added transaction");
            } else if (response.statusCode() == 404) {
                System.out.println("Invalid data, please confirm the ids entered.");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input");
        }
    }

    public static void getAllTransactions() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter user id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/transaction/getTransactions/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listOfTransactions = new TypeToken<ArrayList<Transaction>>() {
                }.getType();
                ArrayList<Transaction> transactionList = gson.fromJson(response.body(), listOfTransactions);
                for (Transaction t : transactionList) {
                    System.out.println(t);
                }
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode() + "\n" + response.body());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getTransaction() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter transaction id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/transaction/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Transaction transaction = gson.fromJson(response.body(), Transaction.class);
                System.out.println(transaction);
            } else if (response.statusCode() == 404) {
                System.out.println("Transaction not found");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
