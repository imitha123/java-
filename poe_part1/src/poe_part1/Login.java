package poe_part1;

public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    private String loginUsername;
    private String loginPassword;

    // Constructor
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Set username
    public void setUsername(String username) {
        this.username = username;
    }

    // Set password
    public void setPassword(String password) {
        this.password = password;
    }

    // Set cell phone number
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Check username
    public boolean checkUserName() {

        return username.contains("_") && username.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity() {

    String passwordRegex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

    return password != null && password.matches(passwordRegex);
}
    // Check South African cell phone number
    public boolean checkCellPhoneNumber() {

        String cellPhoneRegex = "^\\+27[0-9]{9}$";

        return cellPhoneNumber.matches(cellPhoneRegex);
    }

    // Register user
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is incorrectly formatted.";
        }

        if (!checkPasswordComplexity()) {
            return "Password does not meet the complexity requirements.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number is incorrectly formatted.";
        }

        return "User has been successfully registered.";
    }

    // Store login details
    // This method was not mentioned on the assignment
    public void setLoginDetails(String loginUsername, String loginPassword) {

        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    // Check login details
    public boolean loginUser() {

        return loginUsername.equals(username)
                && loginPassword.equals(password);
    }

    // Return login status
    public String returnLoginStatus() {

        if (loginUser()) {
            return "Welcome, " + firstName + " " + lastName
                    + ", it is great to see you again.";
        }

        return "Username or password is incorrect, please try again.";
    }
}