package com.bbdgrad.controller;

import com.bbdgrad.Main;
import com.bbdgrad.model.Batch;
import com.bbdgrad.model.BeanAccount;
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


public class BeanAccountController {

    public static void createBeanAccount() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter user id: ");
            int userId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();
            System.out.print("Enter account balance: ");
            BigDecimal accountBalance = BigDecimal.valueOf(Float.parseFloat(scanner.nextLine()));

            BeanAccount beanAccount = new BeanAccount(userId, accountName, accountBalance);
            String jsonBody = new Gson().toJson(beanAccount);
            beanAccount = null;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/beanAccount"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .build();
            jsonBody = null;

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully added account");
            } else if (response.statusCode() == 404) {
                System.out.println("User not found");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    public static void updateBeanAccount() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter bean account id: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("Leave options blank to skip");
            System.out.print("Enter user id: ");
            String userId = scanner.nextLine();
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();
            System.out.print("Enter account balance: ");
            String accountBalance = scanner.nextLine();

            if (!userId.isBlank()) {
                int userIdValue = Integer.parseInt(userId);
                userId = "?user_id=" + userIdValue;
            }
            if (!accountName.isBlank() && !userId.isBlank()) {
                accountName = "&account_name=" + accountName;
            }
            else if (!accountName.isBlank()) {
                accountName = "?account_name=" + accountName;
            }
            if (!accountBalance.isBlank() && (!accountName.isBlank() || !userId.isBlank())) {
                BigDecimal accountBalanceValue = BigDecimal.valueOf(Float.parseFloat(accountBalance));
                accountBalance = "&account_balance=" + accountBalanceValue;
            }
            else if (!accountBalance.isBlank()) {
                BigDecimal accountBalanceValue = BigDecimal.valueOf(Float.parseFloat(accountBalance));
                accountBalance = "?account_balance=" + accountBalanceValue;
            }

            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(Main.BASE_URL + "/api/v1/beanAccount/update/" + id + userId + accountName + accountBalance))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Successfully updated account");
                } else if (response.statusCode() == 404 && response.body().equals("BeanAccount does not exist")) {
                    System.out.println("BeanAccount not found");
                } else if (response.statusCode() == 404 && response.body().equals("User does not exist")) {
                    System.out.println("User not found");
                } else {
                    System.out.println("API request failed. Status code: " + response.statusCode());
                }

            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e);
        }
    }

    public static void deleteBeanAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure? (y): ");
        String input = scanner.nextLine();
        if (!input.equals("y")) {
            return;
        }

        System.out.print("Enter account id: ");
        String id = scanner.nextLine();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/beanAccount/delete/" + id))
                    .DELETE()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully deleted account");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllBeanAccounts() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/beanAccount"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listOfAccounts = new TypeToken<ArrayList<BeanAccount>>() {
                }.getType();
                ArrayList<BeanAccount> accountList = gson.fromJson(response.body(), listOfAccounts);
                for (BeanAccount b : accountList) {
                    System.out.println(b);
                }
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getBeanAccount() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/beanAccount/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                BeanAccount beanAccount = gson.fromJson(response.body(), BeanAccount.class);
                System.out.println(beanAccount);
            } else if (response.statusCode() == 404) {
                System.out.println("Account not found");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
