public class Account {

    private String firstName;
    private String lastName;
    private String accountType;
    private double accountBalance;

    public Account(String firstName, String lastName, String accountType, double accountBalance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }


    //Getters function below.
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

}
