package poe_part1;

import java.util.Scanner;

public class Poe_part1 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== USER REGISTRATION =====");

        // First name
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        // Last name
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Create user
        Login user = new Login(firstName, lastName);

        // Username
        while (true) {

            System.out.print("Enter your username: ");

            String username = scanner.nextLine();

            user.setUsername(username);

            if (user.checkUserName()) {

                System.out.println("Username successfully accepted.");
                break;

            } else {

                System.out.println(
                        "Username is incorrectly formatted. "
                        + "It must contain an underscore and be "
                        + "no more than 5 characters.");
            }
        }

        // Password
        while (true) {

            System.out.print("Enter your password: ");

            String password = scanner.nextLine();

            user.setPassword(password);

            if (user.checkPasswordComplexity()) {

                System.out.println("Password successfully captured.");
                break;

            } else {

                System.out.println(
                        "Password does not meet the complexity requirements.");

                System.out.println(
                        "Password must be at least 8 characters long "
                        + "and contain an uppercase letter, a lowercase "
                        + "letter, a number and a special character.");
            }
        }

        // Cellphone number
        while (true) {

            System.out.print(
                    "Enter your South African cell phone number: ");

            String cellPhoneNumber = scanner.nextLine();

            user.setCellPhoneNumber(cellPhoneNumber);

            if (user.checkCellPhoneNumber()) {

                System.out.println("Cell number successfully captured.");
                break;

            } else {

                System.out.println(
                        "Cell phone number is incorrectly formatted.");

                System.out.println(
                        "Use the international format, for example "
                        + "+27821234567");
            }
        }

        // Register user
        System.out.println();
        System.out.println(user.registerUser());

        // Login
        System.out.println();
        System.out.println("===== USER LOGIN =====");

        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        user.setLoginDetails(loginUsername, loginPassword);

        System.out.println();
        System.out.println(user.returnLoginStatus());

        // Only continue if login succeeds
        if (!user.loginUser()) {

            scanner.close();
            return;
        }

        // Welcome to Quick Chat
        System.out.println();
        System.out.println("================================");
        System.out.println("Welcome to Quick Chat!");
        System.out.println("================================");

        // Ask how many messages the user wants to enter
        int messageLimit;

        while (true) {

            System.out.print(
                    "How many messages would you like to enter? ");

            String input = scanner.nextLine();

            try {

                messageLimit = Integer.parseInt(input);

                if (messageLimit >= 0) {
                    break;
                }

                System.out.println(
                        "Please enter zero or a positive number.");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid whole number.");
            }
        }

        int messagesEntered = 0;

        Message messageManager = null;

        boolean running = true;

        // Main menu
        while (running) {

            System.out.println();
            System.out.println("===== QUICK CHAT MENU =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":

                    if (messagesEntered >= messageLimit) {

                        System.out.println(
                                "You have reached the number of "
                                + "messages you selected.");

                        break;
                    }

                    System.out.println();
                    System.out.println("===== CREATE MESSAGE =====");

                    // Recipient
                    String recipient;

                    while (true) {

                        System.out.print(
                                "Enter the recipient's cellphone number: ");

                        recipient = scanner.nextLine();

                        Message validationMessage = new Message(
                                messagesEntered + 1,
                                recipient,
                                "",
                                scanner);

                        String validationResult =
                                validationMessage.checkRecipientCell();

                        if (validationResult.startsWith(
                                "Cell number successfully")) {

                            break;

                        } else {

                            System.out.println(validationResult);
                        }
                    }

                    // Message text
                    String messageText;

                    while (true) {

                        System.out.print("Enter your message: ");

                        messageText = scanner.nextLine();

                        if (messageText.length() < 250) {

                            System.out.println("Message sent");

                            break;

                        } else {

                            System.out.println(
                                    "Please enter a message of less "
                                    + "than 250 characters.");
                        }
                    }

                    // Create message
                    Message newMessage = new Message(
                            messagesEntered + 1,
                            recipient,
                            messageText,
                            scanner);

                    if (!newMessage.checkMessageID()) {

                        System.out.println(
                                "Invalid message ID. Please try again.");

                        break;
                    }

                    System.out.println();
                    System.out.println(
                            "Message ID: " + newMessage.getMessageID());

                    System.out.println(
                            "Message Hash: " + newMessage.getMessageHash());

                    // Let the user choose what to do
                    String messageResult;

                    while (true) {

                        messageResult = newMessage.sendMessages();

                        System.out.println(messageResult);

                        if (messageResult.equals(
                                "Invalid option. Please choose 1, 2 or 3.")) {

                            continue;
                        }

                        break;
                    }

                    messagesEntered++;

                    // Display details only when sent
                    if (newMessage.getStatus().equals("Sent")) {

                        System.out.println();
                        System.out.println("===== MESSAGE DETAILS =====");

                        System.out.println(
                                "Message ID: "
                                + newMessage.getMessageID());

                        System.out.println(
                                "Message Hash: "
                                + newMessage.getMessageHash());

                        System.out.println(
                                "Recipient: "
                                + newMessage.getRecipient());

                        System.out.println(
                                "Message: "
                                + newMessage.getMessage());
                    }

                    // Keep a reference for displaying sent messages
                    messageManager = newMessage;

                    break;

                case "2":

                    System.out.println("Coming Soon");

                    break;

                case "3":

                    running = false;

                    break;

                default:

                    System.out.println(
                            "Invalid option. Please choose 1, 2 or 3.");
            }
        }

        // Final total
        System.out.println();
        System.out.println("===== QUICK CHAT SUMMARY =====");

        if (messageManager != null) {

            System.out.println(
                    "Total messages sent: "
                    + messageManager.returnTotalMessages());

        } else {

            System.out.println("Total messages sent: 0");
        }

        System.out.println("Thank you for using Quick Chat!");

        scanner.close();
    }
}