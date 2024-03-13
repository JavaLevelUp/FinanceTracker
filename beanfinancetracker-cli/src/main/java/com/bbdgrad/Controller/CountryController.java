package com.bbdgrad.controller;

import com.bbdgrad.model.Country;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

import static com.bbdgrad.Main.prop;

public class CountryController {

    public static void createCountry() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter country name: ");
        String name = scanner.nextLine();

        Country country = new Country(name);
        String jsonBody = new Gson().toJson(country);
        country = null;

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest post = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/country"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .build();
            jsonBody = null;

            HttpResponse<String> response = httpClient.send(post, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully added country");
            } else if (response.statusCode() == 409) {
                System.out.println("Country already exists");
            }
            else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCountry() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter country id: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter country name: ");
            String name = scanner.nextLine();

            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest post = HttpRequest.newBuilder()
                        .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/country/update/" + id + "?name=" + name))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                        .build();

                HttpResponse<String> response = httpClient.send(post, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Successfully updated country");
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

    public static void deleteCountry() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Are you sure? (y): ");
            String input = scanner.nextLine();
            if (!input.equals("y")) {
                return;
            }

            System.out.print("Enter country id: ");
            String id = scanner.nextLine();

            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest post = HttpRequest.newBuilder()
                        .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/country/delete/" + id))
                        .DELETE()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                        .build();

                HttpResponse<String> response = httpClient.send(post, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Successfully deleted country");
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

    public static void getAllCountries() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/country"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listOfCountries = new TypeToken<ArrayList<Country>>() {}.getType();
                ArrayList<Country> countryList = gson.fromJson(response.body(), listOfCountries);
                for (Country c : countryList) {
                    System.out.println(c);
                }
            }
            else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getCountry() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter country id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/country/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + prop.getProperty("ACCESS_TOKEN"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Country country = gson.fromJson(response.body(), Country.class);
                System.out.println(country);
            }
            else if (response.statusCode() == 404) {
                System.out.println("Country not found");
            }
            else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
