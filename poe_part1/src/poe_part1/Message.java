package poe_part1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String status;

    private Scanner scanner;

    private static int totalMessagesSent = 0;

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final Set<String> usedMessageIDs = new HashSet<>();

    private static final Random random = new Random();

    // Constructor
    public Message(
            int messageNumber,
            String recipient,
            String message,
            Scanner scanner) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.scanner = scanner;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.status = "Pending";
    }

    // Generate a unique 10-digit message ID
    private String generateMessageID() {

        String generatedID;

        do {
            long number = 1000000000L
                    + (long) (random.nextDouble() * 9000000000L);

            generatedID = String.valueOf(number);

        } while (usedMessageIDs.contains(generatedID));

        usedMessageIDs.add(generatedID);

        return generatedID;
    }

    // Check that the message ID is no more than 10 characters
    public boolean checkMessageID() {

        return messageID != null
                && messageID.length() <= 10;
    }

    // Check the recipient's cellphone number
    
public String checkRecipientCell() {
    if (recipient != null && recipient.matches("^\\+27[0-9]{9}$")) {
        return "Cell phone number successfully captured.";
    }

    return "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
}


    // Create the message hash
    public String createMessageHash() {

        String firstTwoDigits = messageID.substring(0, 2);

        String firstWord = "";
        String lastWord = "";

        String trimmedMessage = message.trim();

        if (!trimmedMessage.isEmpty()) {

            String[] words = trimmedMessage.split("\\s+");

            firstWord = words[0];
            lastWord = words[words.length - 1];

            // Remove punctuation from the first and last words
            firstWord = firstWord.replaceAll(
                    "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");

            lastWord = lastWord.replaceAll(
                    "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
        }

        return firstTwoDigits
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord;
    }

    // Allow the user to send, disregard or store the message
 
public String sendMessages() {
    System.out.println();
    System.out.println("What would you like to do with this message?");
    System.out.println("1. Send Message");
    System.out.println("2. Disregard Message");
    System.out.println("3. Store Message to Send Later");
    System.out.print("Enter your choice: ");

    String choice = scanner.nextLine().trim();

    switch (choice) {
        case "1":
            status = "Sent";
            sentMessages.add(this);
            totalMessagesSent++;
            storeMessage();
            return "Message successfully sent.";

        case "2":
            System.out.println("Press 0 to delete the message.");
            System.out.print("Enter your choice: ");

            String deleteChoice = scanner.nextLine().trim();

            if (deleteChoice.equals("0")) {
                status = "Disregarded";
                return "Message deleted successfully.";
            } else {
                return "Message was not deleted.";
            }

        case "3":
            status = "Stored for Later";
            storedMessages.add(this);
            storeMessage();
            return "Message successfully stored to send later.";

        default:
            return "Invalid option. Please choose 1, 2 or 3.";
    }

}

public String checkMessageLength() {
    if (message.length() <= 250) {
        return "Message ready to send";
    }

    int excessCharacters = message.length() - 250;

    return "Message exceeds 250 characters by "
            + excessCharacters
            + ", please reduce message length.";
}



    // Return the details of messages sent during this session
    public String printMessages() {

        if (sentMessages.isEmpty()) {

            return "No messages have been sent yet.";
        }

        StringBuilder result = new StringBuilder();

        for (Message sentMessage : sentMessages) {

            result.append("\nMessage ID: ")
                    .append(sentMessage.messageID)
                    .append("\n");

            result.append("Message Hash: ")
                    .append(sentMessage.messageHash)
                    .append("\n");

            result.append("Recipient: ")
                    .append(sentMessage.recipient)
                    .append("\n");

            result.append("Message: ")
                    .append(sentMessage.message)
                    .append("\n");

            result.append("------------------------------\n");
        }

        return result.toString();
    }

    // Return the total number of messages sent
    public int returnTotalMessages() {

        return totalMessagesSent;
    }

    // Store sent and deferred messages in a JSON file
    public void storeMessage() {

        try (FileWriter writer = new FileWriter("messages.json")) {

            writer.write("[\n");

            List<Message> allMessages = new ArrayList<>();

            allMessages.addAll(sentMessages);
            allMessages.addAll(storedMessages);

            for (int i = 0; i < allMessages.size(); i++) {

                Message current = allMessages.get(i);

                writer.write("  {\n");

                writer.write("    \"messageID\": \""
                        + escapeJson(current.messageID) + "\",\n");

                writer.write("    \"messageNumber\": "
                        + current.messageNumber + ",\n");

                writer.write("    \"messageHash\": \""
                        + escapeJson(current.messageHash) + "\",\n");

                writer.write("    \"recipient\": \""
                        + escapeJson(current.recipient) + "\",\n");

                writer.write("    \"message\": \""
                        + escapeJson(current.message) + "\",\n");

                writer.write("    \"status\": \""
                        + escapeJson(current.status) + "\"\n");

                writer.write("  }");

                if (i < allMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]\n");

        } catch (IOException e) {

            System.out.println(
                    "Error saving messages to JSON: " + e.getMessage());
        }
    }

    // Escape characters that have special meaning in JSON
    private String escapeJson(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getStatus() {
        return status;
    }

}