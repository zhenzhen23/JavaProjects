package project;

import java.text.SimpleDateFormat;

/**
 * Java Project
 *
 * @author Shahzad Khan Coauthor Weijie Zheng Student ID: 000028928
 */
public class Transaction {

    private double amount;
    private String note;
    private String date;
    private TransactionType trans;

    /**
     * Default constructor.
     */
    public Transaction() {
        amount = 0.0;
        note = "";
        date = "";
        trans = TransactionType.GIFT_IN;
    }

    public Transaction(TransactionType trans, double amount, String date) {
        this.trans = trans;
        this.amount = amount;
        this.date = date;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the transaction
     */
    public TransactionType getTrans() {
        return trans;
    }

    /**
     * @param trans the transaction to set
     */
    public void setTrans(TransactionType trans) {
        this.trans = trans;
    }

    @Override
    public String toString() {
        return trans + "   $" + amount + "   " + date;
    }

}
