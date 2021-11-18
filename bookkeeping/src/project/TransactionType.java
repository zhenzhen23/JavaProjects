package project;

/**
 * Java Project
 *
 * @author Shahzad Khan Coauthor Weijie Zheng Student ID: 000028928
 */
public enum TransactionType {
    SALARY("Salary"),
    GIFT_IN("Gift - Income"),
    OTHERS_IN("Others"),
    GIFT_OUT("Gift to purchase"),
    BILL("Bill to pay"),
    FOOD("Food to purchase"),
    ENTERTAINMENT("Entertainment purchase"),
    TRANSPORTATION("Transportation purchase"),
    OTHERS_OUT("Others - Expense");

    String type;

    /**
     * default constructor
     */
    private TransactionType() {

    }

    /**
     * @param type the actual type of transaction.
     */
    private TransactionType(String type) {
        this.type = type;
    }

    /**
     * @return the type of transaction.
     */
    public String getType() {
        return type;
    }
}
