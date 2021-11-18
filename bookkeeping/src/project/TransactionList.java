package project;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Java Project
 *
 * @author Shahzad Khan Coauthor Weijie Zheng Student ID: 000028928
 */
public class TransactionList {

    private ArrayList<Transaction> tranList = new ArrayList<>();
    /**
     * default constructor
     */
    public TransactionList() {

    }

    /**
     * @param index the index for the element in the ArrayList to retrieve
     * @return the transaction at the specific index
     */
    public Transaction get(int index) {
        return tranList.get(index);
    }

    /**
     * @param trans the transaction to add to the list
     */
    public void add(Transaction trans) {
        tranList.add(trans);
    }

    public int length(){
        return tranList.size();
    }
    
    public void set(int i, Transaction tran)
    {
        tranList.set(i, tran);
    }
    public int getIndex(Transaction trans){
        return tranList.indexOf(trans);
    }
    
    /**
     * @param dollar the dollar amount to search the list for
     * @return the list of transactions that have the dollar amount
     */
    public TransactionList search(double dollar) {
        TransactionList searchList = new TransactionList();

        for (int i = 0; i < tranList.size(); i++) {
            if (tranList.get(i).getAmount() == dollar) {
                searchList.add(tranList.get(i));
            }
        }
        return searchList;
    }

    /**
     *
     * @param lower the lower range of the dollar amount to search the list for
     * @param upper the upper range to search the list for
     * @return the list of transactions that have the dollar amount
     */
    public TransactionList search(double lower, double upper) {
        TransactionList searchList = new TransactionList();

        for (int i = 0; i < tranList.size(); i++) {
            if (tranList.get(i).getAmount() >= lower && tranList.get(i).getAmount() <= upper) {
                searchList.add(tranList.get(i));
            }
        }
        return searchList;
    }

    /**
     * @param trans the transaction to remove from the list
     */
    public void delete(Transaction trans) {
        tranList.remove(trans);
    }
    /*    
    /**
     * @param 
     * @return 
     *
    public void sort() {
        ArrayList<Transaction> sortList = new ArrayList<>();
        for (int i=0; i<inList.size(); i++) {
            
        }
        for (int i=0; i<outList.size(); i++) {
            
        }
    }
     */

  

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionList other = (TransactionList) obj;
        if (!Objects.equals(this.tranList, other.tranList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.tranList);
        return hash;
    }

    @Override
    public String toString() {
        return "TransactionList{" + "tranList=" + tranList + '}';
    }
}
