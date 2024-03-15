package com.bbdgrad.controller;

import com.bbdgrad.Main;
import com.bbdgrad.model.Batch;
import com.bbdgrad.model.User;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bbdgrad.Main.prop;

public class UserController {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void createUser() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter user name: ");
            String name = scanner.nextLine();
            System.out.print("Enter user email: ");
            String email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Invalid email");
                return;
            }

            User user = new User(name, email);
            String jsonBody = new Gson().toJson(user);
            user = null;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/user"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .build();
            jsonBody = null;

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully added user");
            } else if (response.statusCode() == 409) {
                System.out.println("Email taken");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    public static void updateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Leave options blank to skip");
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email");
            return;
        }

        if (!name.isBlank()) {
            name = ("?name=" + name).replace(" ", "%20");
        }
        if (!email.isBlank() && !name.isBlank()) {
            email = "&email=" + email;
        } else if (!email.isBlank()) {
            email = "?email=" + email;
        }

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/user/update/" + id + name + email))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully updated user");
            } else if (response.statusCode() == 404) {
                System.out.println("User not found");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure? (y): ");
        String input = scanner.nextLine();
        if (!input.equals("y")) {
            return;
        }

        System.out.print("Enter user id: ");
        String id = scanner.nextLine();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/user/delete/" + id))
                    .DELETE()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Successfully deleted user");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllUsers() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/user"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listOfUsers = new TypeToken<ArrayList<User>>() {}.getType();
                ArrayList<User> userList = gson.fromJson(response.body(), listOfUsers);
                for (User u : userList) {
                    System.out.println(u);
                }
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getUser() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter user id: ");
            int id = Integer.parseInt(scanner.nextLine());

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(Main.BASE_URL + "/api/v1/user/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Main.ACCESS_TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                User user = gson.fromJson(response.body(), User.class);
                System.out.println(user);
            } else if (response.statusCode() == 404) {
                System.out.println("User not found");
            } else {
                System.out.println("API request failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
