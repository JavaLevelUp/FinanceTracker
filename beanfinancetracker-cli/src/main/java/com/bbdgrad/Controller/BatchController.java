package com.bbdgrad.controller;

import com.bbdgrad.model.Batch;
import com.bbdgrad.model.Bean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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

public class BatchController {


    public static void createBatch() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter batch date (yyyy-MM-dd HH:mm): ");
            String batch_date = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(batch_date, formatter);
            System.out.print("Enter batch weight: ");
            float weight = Float.parseFloat(scanner.nextLine());
            System.out.print("Enter bean id: ");
            int beanId = Integer.parseInt(scanner.nextLine());

            Batch batch = new Batch(batch_date, weight, beanId);
            String jsonBody = new Gson().toJson(batch);
            batch = null;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/batch"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .build();
            jsonBody = null;

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully added batch");
            } else if (response.statusCode() == 400) {
                System.out.println("Date format error");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input");
        }
    }

    public static void updateBatch() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter batch id: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("Leave options blank to skip");
            System.out.print("Enter batch date (yyyy-MM-dd HH:mm): ");
            String batch_date = scanner.nextLine();
            System.out.print("Enter batch weight: ");
            String weight = scanner.nextLine();
            System.out.print("Enter bean id: ");
            String beanId = scanner.nextLine();

            if (!batch_date.isBlank()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(batch_date, formatter);
                batch_date = ("?batch_date=" + batch_date).replace(" ", "%20");
            }
            if (!weight.isBlank() && !batch_date.isBlank()) {
                float weightValue = Float.parseFloat(weight);
                weight = "&weight=" + weightValue;
            } else if (!weight.isBlank()) {
                float weightValue = Float.parseFloat(weight);
                weight = "?weight=" + weightValue;
            }
            if (!beanId.isBlank() && (!weight.isBlank() || !batch_date.isBlank())) {
                int beanIdValue = Integer.parseInt(beanId);
                beanId = "?bean_id=" + beanIdValue;
            } else if (!beanId.isBlank()) {
                float weightValue = Float.parseFloat(beanId);
                weight = "?weight=" + weightValue;
            }

            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/batch/update/" + id + batch_date + weight + beanId))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Successfully updated batch");
                } else if (response.statusCode() == 404) {
                    System.out.println("Batch not found");
                } else if (response.statusCode() == 400) {
                    System.out.println("Date format error");
                } else {
                    System.out.println("API request failed. Status code: " + response.statusCode());
                }

            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    public static void deleteBatch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure? (y): ");
        String input = scanner.nextLine();
        if (!input.equals("y")) {
            return;
        }

        System.out.print("Enter batch id: ");
        String id = scanner.nextLine();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/batch/delete/" + id))
                    .DELETE()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully deleted batch");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllBatches() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/batch"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listOfBatches = new TypeToken<ArrayList<Batch>>() {
                }.getType();
                ArrayList<Batch> batchList = gson.fromJson(response.body(), listOfBatches);
                for (Batch b : batchList) {
                    System.out.println(b);
                }
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getBatch() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter batch id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/batch/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Batch batch = gson.fromJson(response.body(), Batch.class);
                System.out.println(batch);
            } else if (response.statusCode() == 404) {
                System.out.println("Batch not found");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
