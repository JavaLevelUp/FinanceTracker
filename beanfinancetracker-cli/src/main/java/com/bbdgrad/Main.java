package com.bbdgrad;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.bbdgrad.controller.*;
import com.bbdgrad.model.AccessToken;
import com.bbdgrad.model.Category;
import com.bbdgrad.model.DeviceVerification;
import com.google.gson.Gson;

import java.util.Properties;

public class Main {

    public static final Properties prop = new Properties();
    private static boolean authenticated = false;
    private static boolean exiting = false;

    public static void main(String[] args) {

        try (FileInputStream input = new FileInputStream("src/main/resources/env.properties")) {
            prop.load(input);
            //prop.setProperty("ACCESS_TOKEN", "");
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load environment properties", ex);
        }

        System.out.println("Welcome to Bean Finance Tracker!\n---");

        if (!authenticated) {
            showAuthOptions();
        } else {
            showMainMenu();
        }

    }

    private static void showAuthOptions() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("Select an operation:\n" +
                    "0. Exit\n" +
                    "1. Authenticate\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        authenticate();
                        //showMainMenu();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }


//    private static void login() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter email: ");
//        String email = scanner.next();
//        System.out.print("Enter password: ");
//        String password = scanner.next();
//
//        User loginUser = new User(email, password);
//        String jsonBody = new Gson().toJson(loginUser);
//        loginUser = null;
//
//        try (HttpClient httpClient = HttpClient.newHttpClient()) {
//            HttpRequest post = HttpRequest.newBuilder()
//                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/auth/authenticate"))
//                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
//                    .header("Content-Type", "application/json")
//                    .build();
//            jsonBody = null;
//
//            HttpResponse<String> response = httpClient.send(post, HttpResponse.BodyHandlers.ofString());
//            if (response.statusCode() == 200) {
//                String responseToken = new Gson().fromJson(response.body(), JsonObject.class).get("access_token").getAsString();
//                prop.setProperty("ACCESS_TOKEN", responseToken);
//                authenticated = true;
//                showMainMenu();
//            } else {
//                System.out.println("Login failed, please try again.");
//            }
//
//        } catch (URISyntaxException | IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (NullPointerException e) {
//            System.out.println("Login failed: " + e.getMessage());
//            showAuthOptions();
//        }
//    }

//    private static void register() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter username: ");
//        String username = scanner.next();
//        System.out.print("Enter email: ");
//        String email = scanner.next();
//        System.out.print("Enter password: ");
//        String password = scanner.next();
//        String role = "ADMIN";
//
//        User regUser = new User(username, email, password, role);
//        String jsonBody = new Gson().toJson(regUser);
//        regUser = null;
//
//        try (HttpClient httpClient = HttpClient.newHttpClient()) {
//            HttpRequest post = HttpRequest.newBuilder()
//                    .uri(new URI(prop.getProperty("BASE_URL") + "/api/v1/auth/register"))
//                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
//                    .header("Content-Type", "application/json")
//                    .build();
//            jsonBody = null;
//
//            HttpResponse<String> response = httpClient.send(post, HttpResponse.BodyHandlers.ofString());
//            if (response.statusCode() == 200) {
//                String responseToken = new Gson().fromJson(response.body(), JsonObject.class).get("access_token").getAsString();
//                prop.setProperty("ACCESS_TOKEN", responseToken);
//                authenticated = true;
//                showMainMenu();
//            } else {
//                System.out.println("Registration failed, please try again. Status code: " + response.statusCode());
//            }
//
//        } catch (URISyntaxException | IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (NullPointerException e) {
//            System.out.println("Registration failed: " + e.getMessage());
//            showAuthOptions();
//        }
//    }

    private static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Manage Countries\n" +
                    "2. Manage Categories\n" +
                    "3. Manage Beans\n" +
                    "4. Manage Batches\n" +
                    "5. Manage Users\n" +
                    "6. Manage Accounts\n" +
                    "7. Manage Transactions\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showCountryMenu();
                        break;
                    case '2':
                        showCategoryMenu();
                        break;
                    case '3':
                        showBeanMenu();
                        break;
                    case '4':
                        showBatchMenu();
                        break;
                    case '5':
                        showUserMenu();
                        break;
                    case '6':
                        showAccountMenu();
                        break;
                    case '7':
                        showTransactionMenu();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showTransactionMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create Transaction\n" +
                    "3. Get All Transactions For User\n" +
                    "4. Get Transaction\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        TransactionController.createTransaction();
                        break;
                    case '3':
                        TransactionController.getAllTransactions();
                        break;
                    case '4':
                        TransactionController.getTransaction();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showAccountMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create Account\n" +
                    "3. Update Account\n" +
                    "4. Delete Account\n" +
                    "5. Get All Accounts\n" +
                    "6. Get Account\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        BeanAccountController.createBeanAccount();
                        break;
                    case '3':
                        BeanAccountController.updateBeanAccount();
                        break;
                    case '4':
                        BeanAccountController.deleteBeanAccount();
                        break;
                    case '5':
                        BeanAccountController.getAllBeanAccounts();
                        break;
                    case '6':
                        BeanAccountController.getBeanAccount();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showUserMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create User\n" +
                    "3. Update User\n" +
                    "4. Delete User\n" +
                    "5. Get All Users\n" +
                    "6. Get User\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        UserController.createUser();
                        break;
                    case '3':
                        UserController.updateUser();
                        break;
                    case '4':
                        UserController.deleteUser();
                        break;
                    case '5':
                        UserController.getAllUsers();
                        break;
                    case '6':
                        UserController.getUser();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showBatchMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create Batch\n" +
                    "3. Update Batch\n" +
                    "4. Delete Batch\n" +
                    "5. Get All Batches\n" +
                    "6. Get Batch\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        BatchController.createBatch();
                        break;
                    case '3':
                        BatchController.updateBatch();
                        break;
                    case '4':
                        BatchController.deleteBatch();
                        break;
                    case '5':
                        BatchController.getAllBatches();
                        break;
                    case '6':
                        BatchController.getBatch();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showBeanMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create Bean\n" +
                    "3. Update Bean\n" +
                    "4. Delete Bean\n" +
                    "5. Get All Beans\n" +
                    "6. Get Bean\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        BeanController.createBean();
                        break;
                    case '3':
                        BeanController.updateBean();
                        break;
                    case '4':
                        BeanController.deleteBean();
                        break;
                    case '5':
                        BeanController.getAllBeans();
                        break;
                    case '6':
                        BeanController.getBean();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showCountryMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create Country\n" +
                    "3. Update Country\n" +
                    "4. Delete Country\n" +
                    "5. Get All Countries\n" +
                    "6. Get Country\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        CountryController.createCountry();
                        break;
                    case '3':
                        CountryController.updateCountry();
                        break;
                    case '4':
                        CountryController.deleteCountry();
                        break;
                    case '5':
                        CountryController.getAllCountries();
                        break;
                    case '6':
                        CountryController.getCountry();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void showCategoryMenu() {
        Scanner scanner = new Scanner(System.in);
        char input;

        do {
            System.out.print("\nSelect an operation:\n" +
                    "0. Exit\n" +
                    "1. Back\n" +
                    "2. Create Category\n" +
                    "3. Update Category\n" +
                    "4. Delete Category\n" +
                    "5. Get All Categories\n" +
                    "6. Get Category\n" +
                    ">> ");

            if (scanner.hasNextLine()) {
                input = scanner.nextLine().charAt(0);
                switch (input) {
                    case '0':
                        System.out.println("Thank you for using Bean Finance Tracker.");
                        exiting = true;
                        break;
                    case '1':
                        showMainMenu();
                        return;
                    case '2':
                        CategoryController.createCategory();
                        break;
                    case '3':
                        CategoryController.updateCategory();
                        break;
                    case '4':
                        CategoryController.deleteCategory();
                        break;
                    case '5':
                        CategoryController.getAllCategories();
                        break;
                    case '6':
                        CategoryController.getCategory();
                        break;
                    default:
                        System.out.println("Invalid selection.\n");
                }
            } else {
                System.out.println("Invalid selection.\n");
            }
        } while (!exiting);
    }

    private static void authenticate() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest post = HttpRequest.newBuilder()
                    .uri(new URI("https://github.com/login/device/code?client_id=" + prop.getProperty("CLIENT_ID") + "&scope=read:user"))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(post, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            DeviceVerification deviceVerification = gson.fromJson(response.body(), DeviceVerification.class);

            System.out.println("Please go to " + deviceVerification.verification_uri() + " and enter the code: " + deviceVerification.user_code());

            boolean success = false;
            while (!success) {
                Thread.sleep(5000);
                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI("https://github.com/login/oauth/access_token?client_id=" + prop.getProperty("CLIENT_ID") + "&device_code=" + deviceVerification.device_code() + "&grant_type=urn:ietf:params:oauth:grant-type:device_code"))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .header("Accept", "application/json")
                        .build();

                response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
                AccessToken accessToken = gson.fromJson(response.body(), AccessToken.class);

                if (accessToken.access_token() != null) {
                    success = true;
                    System.out.println("Successfully authenticated!");
                    prop.setProperty("ACCESS_TOKEN", accessToken.access_token());
                    System.out.println(prop.getProperty("ACCESS_TOKEN"));
                    authenticated = true;
                    showMainMenu();
                }
                else {
                    System.out.println("Waiting for authentication...");
                }
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

}