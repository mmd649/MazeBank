public class Savings extends Account{

    private final double interestRate = 0.025;

    public Savings(String firstName, String lastName, String accountType, double accountBalance) {
        super(firstName, lastName, accountType, accountBalance);
    }
}
