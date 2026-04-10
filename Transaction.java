import java.sql.Timestamp;

public class Transaction {
    private int transactionID;
    private int userID;
    private double amount;
    private String status;
    private Timestamp date;

    public Transaction(int transactionID, int userID, double amount, String status, Timestamp date) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    public void displayTransaction() {
        System.out.println("Transaction ID: " + transactionID + ", Amount: $" + amount + ", Status: " + status);
    }
}