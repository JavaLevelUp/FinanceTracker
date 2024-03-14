package com.bbdgrad.controller;

import com.bbdgrad.model.Bean;
import com.bbdgrad.model.Category;
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
import java.util.ArrayList;
import java.util.Scanner;

import static com.bbdgrad.Main.prop;

public class BeanController {


    public static void createBean() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter bean name: ");
            String name = scanner.nextLine();
            System.out.print("Enter country id: ");
            int id = Integer.parseInt(scanner.nextLine());

            Bean bean = new Bean(name, id);
            String jsonBody = new Gson().toJson(bean);
            bean = null;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/bean"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .build();
            jsonBody = null;

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully added bean");
            } else if (response.statusCode() == 409) {
                System.out.println("Bean already exists");
            }
            else if (response.statusCode() == 404) {
                System.out.println("Country not found");
            }
            else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    public static void updateBean() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter bean id: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter updated bean name (): ");
            String name = scanner.nextLine();
            System.out.print("Enter country id: ");
            String countryId = scanner.nextLine();

            if (!name.isBlank()) {
                name = "?name=" + name;
            }
            if (!countryId.isBlank()) {
                countryId = "?countryId=" + Integer.parseInt(countryId);
            }

            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/bean/update/" + id + name + countryId))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Successfully updated bean");
                }
                else if (response.statusCode() == 404) {
                    System.out.println("Country not found");
                }
                else {
                    System.out.println("API request failed. Status code: " + response.statusCode());
                }

            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    public static void deleteBean() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Are you sure? (y): ");
            String input = scanner.nextLine();
            if (!input.equals("y")) {
                return;
            }

            System.out.print("Enter bean id: ");
            String id = scanner.nextLine();

            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/bean/delete/" + id))
                        .DELETE()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Successfully deleted bean");
                }
                else {
                    System.out.println("API request failed. Status code: " + response.statusCode());
                }

            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    public static void getAllBeans() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/bean"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listOfBeans = new TypeToken<ArrayList<Bean>>() {}.getType();
                ArrayList<Bean> beanList = gson.fromJson(response.body(), listOfBeans);
                for (Bean b : beanList) {
                    System.out.println(b);
                }
            }
            else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getBean() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter bean id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/bean/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(response.body(), Bean.class);
                System.out.println(bean);
            }
            else if (response.statusCode() == 404) {
                System.out.println("Bean not found");
            }
            else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
