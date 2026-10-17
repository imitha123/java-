
package poe_part1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // Test 1: Message length is valid
    @Test
    public void testMessageLengthSuccess() {
        String messageText = "Hi Mike, can you join us for dinner tonight?";

        Message message = new Message(
                1,
                "+27821234567",
                messageText,
                new Scanner("")
        );

        assertEquals(
                "Message ready to send",
                message.checkMessageLength()
        );
    }

    // Test 2: Message length exceeds 240 characters
    @Test
    public void testMessageLengthFailure() {
        String messageText = "A".repeat(260);

        Message message = new Message(
                1,
                "+27821234567",
                messageText,
                new Scanner("")
        );

        assertEquals(
                "Message exceeds 250 characters by 10, please reduce message length.",
                message.checkMessageLength()
        );
    }

    // Test 3: Recipient cellphone number is correctly formatted
    @Test
    public void testRecipientCellSuccess() {
        Message message = new Message(
                1,
                "+27821234567",
                "Hi Mike, can you join us for dinner tonight?",
                new Scanner("")
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    // Test 4: Recipient cellphone number is incorrectly formatted
    @Test
    public void testRecipientCellFailure() {
        Message message = new Message(
                2,
                "08575975889",
                "Hi Keegan, did you receive the payment?",
                new Scanner("")
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    // Test 5: Message ID is generated and contains 10 digits
    @Test
    public void testMessageIDGenerated() {
        Message message = new Message(
                1,
                "+27821234567",
                "Hi Mike, can you join us for dinner tonight?",
                new Scanner("")
        );

        String messageID = message.getMessageID();

        assertNotNull(messageID);
        assertEquals(10, messageID.length());
        assertTrue(messageID.matches("[0-9]{10}"));
        assertTrue(message.checkMessageID());
    }

    // Test 6: Generated message IDs are unique
    @Test
    public void testMessageIDsAreUnique() {
        Set<String> messageIDs = new HashSet<>();

        for (int i = 1; i <= 100; i++) {
            Message message = new Message(
                    i,
                    "+27821234567",
                    "Test message " + i,
                    new Scanner("")
            );

            messageIDs.add(message.getMessageID());
        }

        assertEquals(100, messageIDs.size());
    }

    // Test 7: Message hash is correct for multiple messages
    @Test
    public void testMessageHashCorrect() {
        String[] messageTexts = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hello Sarah, how are you?",
            "Hi Keegan, did you receive the payment?"
        };

        for (int i = 0; i < messageTexts.length; i++) {
            Message message = new Message(
                    i + 1,
                    "+27821234567",
                    messageTexts[i],
                    new Scanner("")
            );

            String messageID = message.getMessageID();
            String expectedPrefix = messageID.substring(0, 2)
                    + ":" + (i + 1) + ":";

            String expectedEnding;

            if (i == 0) {
                expectedEnding = "Hitonight";
            } else if (i == 1) {
                expectedEnding = "Helloyou";
            } else {
                expectedEnding = "Hipayment";
            }

            assertEquals(
                    expectedPrefix + expectedEnding,
                    message.createMessageHash()
            );
        }
    }

    // Test 8: User chooses to send a message
    @Test
    public void testSendMessage() {
        Message message = new Message(
                1,
                "+27821234567",
                "Hi Mike, can you join us for dinner tonight?",
                new Scanner("1\n")
        );

        int totalBefore = message.returnTotalMessages();

        String result = message.sendMessages();

        assertEquals("Message successfully sent.", result);
        assertEquals("Sent", message.getStatus());
        assertEquals(totalBefore + 1, message.returnTotalMessages());
    }

    // Test 9: User chooses to disregard a message
    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                2,
                "08575975889",
                "Hi Keegan, did you receive the payment?",
                new Scanner("2\n0\n")
        );

        int totalBefore = message.returnTotalMessages();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        String result;

        try {
            System.setOut(new PrintStream(output));
            result = message.sendMessages();
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("Press 0 to delete the message."));
        assertEquals("Message deleted successfully.", result);
        assertEquals("Disregarded", message.getStatus());
        assertEquals(totalBefore, message.returnTotalMessages());
    }

    // Test 10: User chooses to store a message for later
    @Test
    public void testStoreMessageForLater() {
        Message message = new Message(
                2,
                "+27821234567",
                "Hi Keegan, did you receive the payment?",
                new Scanner("3\n")
        );

        int totalBefore = message.returnTotalMessages();

        String result = message.sendMessages();

        assertEquals("Message successfully stored to send later.", result);
        assertEquals("Stored for Later", message.getStatus());
        assertEquals(totalBefore, message.returnTotalMessages());
    }

    // Test 11: Total sent messages increases only when a message is sent
    @Test
    public void testReturnTotalMessages() {
        Message sentMessage = new Message(
                1,
                "+27821234567",
                "Hi Mike, can you join us for dinner tonight?",
                new Scanner("1\n")
        );

        Message disregardedMessage = new Message(
                2,
                "08575975889",
                "Hi Keegan, did you receive the payment?",
                new Scanner("2\n0\n")
        );

        int totalBefore = sentMessage.returnTotalMessages();

        sentMessage.sendMessages();
        disregardedMessage.sendMessages();

        assertEquals(
                totalBefore + 1,
                sentMessage.returnTotalMessages()
        );
    }
}