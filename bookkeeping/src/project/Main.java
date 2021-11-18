package project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author Weijie Zheng
 */
public class Main extends Application {

    
    Label lblBalance = new Label("Balance");
    Label lblIncome = new Label("Income");
    Label lblExpense = new Label("Expense");

    double income = 0;
    double expense = 0;
    double balance = income - expense;

    Label lblBalanceAmount = new Label(Double.toString(income));
    Label lblIncomeAmount = new Label(Double.toString(expense));
    Label lblExpenseAmount = new Label(Double.toString(balance));

    TextField tfSearch = new TextField();

    ListView<Transaction> lvIncome = new ListView();
    ListView<Transaction> lvExpense = new ListView();

    BorderPane root;
    int pane;

    RadioButton rbSalary;
    RadioButton rbGiftIn;
    RadioButton rbOthersIn;

    RadioButton rbGiftOut;
    RadioButton rbBill;
    RadioButton rbFood;
    RadioButton rbEntertainment;
    RadioButton rbTransportation;
    RadioButton rbOthersOut;

    Transaction tran;
    TransactionList inList = new TransactionList();
    TransactionList outList = new TransactionList();
    TransactionType tranType = TransactionType.SALARY;
    SimpleDateFormat sdf;

    File incomeFile = new File("income.txt");
    File expenseFile = new File("expense.txt");

    TextField tfAmount;

    int noError = 0;

    @Override
    public void start(Stage primaryStage) {
        
        VBox vboxBalance = new VBox(lblBalanceAmount, lblBalance);
        vboxBalance.setAlignment(Pos.CENTER);
        VBox.setMargin(vboxBalance, new Insets(20));
        vboxBalance.setId("balance");

        VBox vboxIncome = new VBox(lblIncomeAmount, lblIncome);
        VBox vboxExpense = new VBox(lblExpenseAmount, lblExpense);
        HBox hbox = new HBox(vboxIncome, vboxExpense);
        hbox.setAlignment(Pos.CENTER);
        HBox.setMargin(vboxIncome, new Insets(10, 50, 10, 0));
        HBox.setMargin(vboxExpense, new Insets(10, 0, 10, 50));
        hbox.setId("balance2");

        Label lblIncomeHeadline = new Label("income");
        Label lblExpenseHeadline = new Label("expense");

        //set search action to button 
        Button btnSearch = new Button("Search");
        btnSearch.setOnAction(e -> {
            //if user did not enter things error window will be show up
            CheckSearch();
            if (noError == 1) {
                if (!tfSearch.getText().equals("")) {
                    Search search = new Search();
                    search.showAndWait();
                    noError = 0;
                }
            } else {
                noError = 0;
            }
        });

        HBox hboxTop = new HBox(tfSearch, btnSearch);
        HBox.setMargin(tfSearch, new Insets(10, 0, 10, 10));
        HBox.setMargin(btnSearch, new Insets(10, 10, 10, 0));

        //set add action to button
        Button btnAdd = new Button("ADD");
        btnAdd.setOnAction(e -> {
            AddStage addStage = new AddStage();
            addStage.showAndWait();
        });
        //set delete action to button
        Button btnDelete = new Button("DELETE");
        btnDelete.setOnAction(e -> {
            try {
                Transaction deleteIncome = lvIncome.
                        getSelectionModel().getSelectedItem();
                for (int i = 0; i < inList.length(); i++) {
                    if (deleteIncome.equals(inList.get(i))) {
                        inList.delete(inList.get(i));
                    }
                }
            } catch (NullPointerException ex) {
                System.out.println("");
            }

            try {
                Transaction deleteExpense = lvExpense.
                        getSelectionModel().getSelectedItem();
                for (int i = 0; i < outList.length(); i++) {
                    if (deleteExpense.equals(outList.get(i))) {
                        outList.delete(outList.get(i));
                    }
                }
            } catch (NullPointerException ex) {
                System.out.println("");
            }
            SaveIncome();
            SaveExpense();
            CountBalance();
        });
        //set edit action to button
        Button btnEdit = new Button("EDIT");
        btnEdit.setOnAction(e -> {
            try {
                EditStage editStage = new EditStage();
                editStage.showAndWait();
            } catch (NullPointerException ex) {
                System.out.println("");
            }
        });

        HBox btnType = new HBox(btnAdd, btnDelete, btnEdit);
        btnType.setAlignment(Pos.CENTER);
        HBox.setMargin(btnAdd, new Insets(0, 50, 0, 0));
        HBox.setMargin(btnEdit, new Insets(0, 0, 0, 50));

        readIncomeFile(incomeFile, inList);
        readExpenseFile(expenseFile, outList);
        CountBalance();

        VBox vboxMain = new VBox(hboxTop, vboxBalance, hbox, lblIncomeHeadline,
                lvIncome, lblExpenseHeadline, lvExpense, btnType);
        VBox.setMargin(lblIncomeHeadline, new Insets(10, 10, 0, 10));
        VBox.setMargin(lvIncome, new Insets(0, 10, 10, 10));
        VBox.setMargin(lblExpenseHeadline, new Insets(0, 10, 0, 10));
        VBox.setMargin(lvExpense, new Insets(0, 10, 10, 10));
        VBox.setMargin(btnType, new Insets(0, 10, 10, 10));

        Scene scene = new Scene(vboxMain, 400, 600);
        scene.getStylesheets().add("/CSS/MainCss.css");

        primaryStage.setTitle("Bookkeeping");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * add stage, use to add trans to tranList
     */
    class AddStage extends Stage {

        public AddStage() {
            super();

            Button btnIncome = new Button("Income");
            btnIncome.setOnAction(e -> {
                root.setCenter(Income());
                pane = 1;
            });
            Button btnExpense = new Button("expense");
            btnExpense.setOnAction(e -> {
                root.setCenter(Expense());
                pane = 2;
            });

            tfAmount = new TextField();
            tfAmount.setFont(Font.font("Verdana", 30));

            root = new BorderPane();
            root.setCenter(Income());
            pane = 1;

            Button btnOk = new Button("OK");
            btnOk.setOnAction(e -> {

                CheckAmount();

                if (noError == 1) {

                    tran = new Transaction();
                    sdf = new SimpleDateFormat("MM-dd-yyyy");

                    tran.setAmount(Double.valueOf(tfAmount.getText()));
                    tran.setDate(sdf.format(new Date()));
                    if (pane == 1) {
                        if (rbSalary.isSelected()) {
                            tran.setTrans(tranType.SALARY);
                        } else if (rbGiftIn.isSelected()) {
                            tran.setTrans(tranType.GIFT_IN);
                        } else if (rbOthersIn.isSelected()) {
                            tran.setTrans(tranType.OTHERS_IN);
                        }
                        inList.add(tran);
                        noError = 0;
                    } else {
                        if (rbGiftOut.isSelected()) {
                            tran.setTrans(tranType.GIFT_OUT);
                        } else if (rbBill.isSelected()) {
                            tran.setTrans(tranType.BILL);
                        } else if (rbFood.isSelected()) {
                            tran.setTrans(tranType.FOOD);
                        } else if (rbEntertainment.isSelected()) {
                            tran.setTrans(tranType.ENTERTAINMENT);
                        } else if (rbTransportation.isSelected()) {
                            tran.setTrans(tranType.TRANSPORTATION);
                        } else if (rbOthersOut.isSelected()) {
                            tran.setTrans(tranType.OTHERS_OUT);
                        }
                        outList.add(tran);
                        noError = 0;
                    }

                    CountBalance();

                    SaveIncome();
                    SaveExpense();

                    close();
                } else {
                    noError = 0;
                }
            });
            Button btnCancel = new Button("CANCEL");
            btnCancel.setOnAction(e -> close());

            HBox btnType = new HBox(btnIncome, btnExpense);
            btnType.setAlignment(Pos.CENTER);
            HBox.setMargin(btnIncome, new Insets(30, 10, 10, 10));
            HBox.setMargin(btnExpense, new Insets(30, 10, 10, 10));

            HBox btnConfirm = new HBox(btnOk, btnCancel);
            HBox.setMargin(btnOk, new Insets(10));
            HBox.setMargin(btnCancel, new Insets(10));
            btnConfirm.setAlignment(Pos.CENTER);

            VBox main = new VBox(btnType, tfAmount, root, btnConfirm);
            main.setAlignment(Pos.TOP_CENTER);
            VBox.setMargin(tfAmount, new Insets(20, 50, 20, 50));

            Scene scene = new Scene(main, 300, 300);

            setTitle("Add");

            setScene(scene);
            initModality(Modality.APPLICATION_MODAL);
        }
    }

    /**
     * edit stage use to edit trans
     */
    class EditStage extends Stage {

        public EditStage() {
            super();

            Button btnIncome = new Button("Income");
            btnIncome.setOnAction(e -> {
                root.setCenter(Income());
                pane = 1;
            });
            Button btnExpense = new Button("expense");
            btnExpense.setOnAction(e -> {
                root.setCenter(Expense());
                pane = 2;
            });

            tfAmount = new TextField();
            tfAmount.setFont(Font.font("Verdana", 30));

            root = new BorderPane();
            root.setCenter(Income());
            pane = 1;

            Button btnOk = new Button("OK");
            btnOk.setOnAction(e -> {

                CheckAmount();

                if (noError == 1) {
                    tran = new Transaction();
                    sdf = new SimpleDateFormat("MM-dd-yyyy");

                    tran.setAmount(Double.valueOf(tfAmount.getText()));
                    if (pane == 1) {
                        try {
                            tran.setDate(lvIncome.getSelectionModel().
                                    getSelectedItem().getDate());
                        } catch (NullPointerException ex) {
                            TypeError();
                        }
                        if (rbSalary.isSelected()) {
                            tran.setTrans(tranType.SALARY);
                        } else if (rbGiftIn.isSelected()) {
                            tran.setTrans(tranType.GIFT_IN);
                        } else if (rbOthersIn.isSelected()) {
                            tran.setTrans(tranType.OTHERS_IN);
                        }
                        try {
                            inList.set(inList.getIndex(lvIncome.
                                    getSelectionModel().
                                    getSelectedItem()), tran);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            TypeError();
                        }
                        noError = 0;
                    } else {
                        try {
                            tran.setDate(lvExpense.getSelectionModel().
                                    getSelectedItem().getDate());
                        } catch (NullPointerException ex) {
                            TypeError();
                        }
                        if (rbGiftOut.isSelected()) {
                            tran.setTrans(tranType.GIFT_OUT);
                        } else if (rbBill.isSelected()) {
                            tran.setTrans(tranType.BILL);
                        } else if (rbFood.isSelected()) {
                            tran.setTrans(tranType.FOOD);
                        } else if (rbEntertainment.isSelected()) {
                            tran.setTrans(tranType.ENTERTAINMENT);
                        } else if (rbTransportation.isSelected()) {
                            tran.setTrans(tranType.TRANSPORTATION);
                        } else if (rbOthersOut.isSelected()) {
                            tran.setTrans(tranType.OTHERS_OUT);
                        }
                        try {
                            outList.set(outList.getIndex(lvExpense.
                                    getSelectionModel().
                                    getSelectedItem()), tran);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            TypeError();
                        }
                        noError = 0;
                    }

                    CountBalance();

                    SaveIncome();
                    SaveExpense();

                    close();
                } else {
                    noError = 0;
                }
            });
            Button btnCancel = new Button("CANCEL");
            btnCancel.setOnAction(e -> close());

            HBox btnType = new HBox(btnIncome, btnExpense);
            btnType.setAlignment(Pos.CENTER);
            HBox.setMargin(btnIncome, new Insets(30, 10, 10, 10));
            HBox.setMargin(btnExpense, new Insets(30, 10, 10, 10));

            HBox btnConfirm = new HBox(btnOk, btnCancel);
            HBox.setMargin(btnOk, new Insets(10));
            HBox.setMargin(btnCancel, new Insets(10));
            btnConfirm.setAlignment(Pos.CENTER);

            VBox main = new VBox(btnType, tfAmount, root, btnConfirm);
            main.setAlignment(Pos.TOP_CENTER);
            VBox.setMargin(tfAmount, new Insets(20, 50, 20, 50));

            Scene scene = new Scene(main, 300, 300);

            setTitle("Edit");

            setScene(scene);
            initModality(Modality.APPLICATION_MODAL);
        }
    }

    /**
     * Search stage use to search trans
     */
    class Search extends Stage {

        double num1;
        double num2;

        public Search() {
            super();
            Label search = new Label("Search for $" + tfSearch.getText() + ":");
            search.setFont(Font.font ("Verdana", 20));
            
            ListView<Transaction> lvSearch = new ListView<>();

            String[] args = tfSearch.getText().trim().split("-");
            if (args.length == 1) {
                num1 = Double.valueOf(args[0]);
                ShowSearch1(lvSearch);
            } else {
                num1 = Double.valueOf(args[0]);
                num2 = Double.valueOf(args[1]);
                ShowSearch2(lvSearch);
            }

            Button btnDelete = new Button("DELETE");
            btnDelete.setOnAction(e -> {
                try {
                    Transaction delete = lvSearch.
                            getSelectionModel().getSelectedItem();
                    for (int i = 0; i < inList.length(); i++) {
                        if (delete.equals(inList.get(i))) {
                            inList.delete(inList.get(i));
                        }
                    }
                    for (int i = 0; i < outList.length(); i++) {
                        if (delete.equals(outList.get(i))) {
                            outList.delete(outList.get(i));
                        }
                    }
                } catch (NullPointerException ex) {
                    System.out.println("");
                }

                if (args.length == 1) {
                    ShowSearch1(lvSearch);
                } else {
                    ShowSearch2(lvSearch);
                }
                SaveIncome();
                SaveExpense();
                CountBalance();
            });
            Button btnBack = new Button("BACK");
            btnBack.setOnAction(e -> close());

            HBox hbBtn = new HBox(btnDelete, btnBack);
            hbBtn.setAlignment(Pos.CENTER);
            HBox.setMargin(btnDelete, new Insets(0,30,10,10));
            HBox.setMargin(btnBack, new Insets(0,10,10,30));
            
            VBox vbSearch = new VBox(search, lvSearch, hbBtn);
            VBox.setMargin(search, new Insets(10));
            VBox.setMargin(lvSearch, new Insets(10));
            
            Scene scene = new Scene(vbSearch, 300, 300);

            setTitle("Search");
            setScene(scene);
            initModality(Modality.APPLICATION_MODAL);
        }

        /**
         * 
         * @param lvSearch arraylist store eligible trans
         * @throws NumberFormatException 
         */
        private void ShowSearch1(ListView<Transaction> lvSearch) throws
                NumberFormatException {

            lvSearch.getItems().clear();

            TransactionList searchInList = inList.search(num1);
            TransactionList searchOutList = outList.search(num1);

            for (int i = 0; i < searchInList.length(); i++) {
                lvSearch.getItems().add(searchInList.get(i));
            }
            for (int i = 0; i < searchOutList.length(); i++) {
                lvSearch.getItems().add(searchOutList.get(i));
            }
        }

        /**
         * 
         * @param lvSearch arraylist store eligible trans
         * @throws NumberFormatException 
         */
        private void ShowSearch2(ListView<Transaction> lvSearch) throws
                NumberFormatException {

            lvSearch.getItems().clear();

            TransactionList searchInList = inList.search(num1, num2);
            TransactionList searchOutList = outList.search(num1, num2);

            for (int i = 0; i < searchInList.length(); i++) {
                lvSearch.getItems().add(searchInList.get(i));
            }
            for (int i = 0; i < searchOutList.length(); i++) {
                lvSearch.getItems().add(searchOutList.get(i));
            }
        }
    }

    /**
     * return HBox that included different type of income
     * @return HBox
     */
    public HBox Income() {

        ToggleGroup tgIncome = new ToggleGroup();
        rbSalary = new RadioButton("salary");
        rbSalary.setToggleGroup(tgIncome);
        rbGiftIn = new RadioButton("gift");
        rbGiftIn.setToggleGroup(tgIncome);
        rbOthersIn = new RadioButton("others");
        rbOthersIn.setToggleGroup(tgIncome);

        rbSalary.setSelected(true);

        HBox Income = new HBox(rbSalary, rbGiftIn, rbOthersIn);
        Income.setAlignment(Pos.CENTER);
        HBox.setMargin(rbSalary, new Insets(10));
        HBox.setMargin(rbGiftIn, new Insets(10));
        HBox.setMargin(rbOthersIn, new Insets(10));
        return Income;
    }

    /**
     * return VBox that included different type of Exoense
     * @return VBox
     */
    public VBox Expense() {

        ToggleGroup tgExpense = new ToggleGroup();
        rbGiftOut = new RadioButton("gift");
        rbGiftOut.setToggleGroup(tgExpense);
        rbBill = new RadioButton("bill");
        rbBill.setToggleGroup(tgExpense);
        rbFood = new RadioButton("food");
        rbFood.setToggleGroup(tgExpense);
        rbEntertainment = new RadioButton("entertainment");
        rbEntertainment.setToggleGroup(tgExpense);
        rbTransportation = new RadioButton("transportation");
        rbTransportation.setToggleGroup(tgExpense);
        rbOthersOut = new RadioButton("others");
        rbOthersOut.setToggleGroup(tgExpense);
        rbBill.setSelected(true);

        HBox expense1 = new HBox(rbBill, rbFood, rbEntertainment);
        expense1.setAlignment(Pos.CENTER);
        HBox.setMargin(rbBill, new Insets(10));
        HBox.setMargin(rbFood, new Insets(10));
        HBox.setMargin(rbEntertainment, new Insets(10));
        
        HBox expense2 = new HBox(rbGiftOut, rbTransportation, rbOthersOut);
        expense2.setAlignment(Pos.CENTER);
        HBox.setMargin(rbGiftOut, new Insets(10));
        HBox.setMargin(rbTransportation, new Insets(10));
        HBox.setMargin(rbOthersOut, new Insets(10));
        
        VBox expense3 = new VBox(expense1, expense2);
        return expense3;
    }

    /**
     * calculate balance
     */
    private void CountBalance() {
        income = 0;
        expense = 0;

        lvIncome.getItems().clear();
        lvExpense.getItems().clear();

        for (int i = 0; i < inList.length(); i++) {
            lvIncome.getItems().add(inList.get(i));
            income += inList.get(i).getAmount();
            lblIncomeAmount.setText(Double.toString(income));
        }
        for (int i = 0; i < outList.length(); i++) {
            lvExpense.getItems().add(outList.get(i));
            expense += outList.get(i).getAmount();
            lblExpenseAmount.setText(Double.toString(expense));
        }

        lblBalanceAmount.setText(Double.toString(income - expense));
    }

    /**
     * write record to file
     * @param write PrintWriter
     * @param tranType enum of Trans
     * @param amount amount of trans
     * @param sdf SimpleDateFormat
     */
    public static void writeOnFile(PrintWriter write,
            TransactionType tranType, double amount, String sdf) {
        write.print(tranType + "|");
        write.print(amount + "|");
        write.print(sdf + "|");
        write.print("\r\n");
    }

    /**
     * save income to income.txt
     */
    private void SaveIncome() {
        File file = new File("income.txt");

        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter write = new PrintWriter(file)) {
            for (int i = 0; i < inList.length(); i++) {
                writeOnFile(write, inList.get(i).getTrans(),
                        inList.get(i).getAmount(),
                        inList.get(i).getDate());
            }

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * save expense to expense.txt
     */
    private void SaveExpense() {
        File file = new File("expense.txt");

        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter write = new PrintWriter(file)) {
            for (int i = 0; i < outList.length(); i++) {
                writeOnFile(write, outList.get(i).getTrans(),
                        outList.get(i).getAmount(),
                        outList.get(i).getDate());
            }

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * read record from file
     * @param file record file
     * @param inList ArrayList of income
     */
    public static void readIncomeFile(File file, TransactionList inList) {
        try {
            file = new File("income.txt");

            Scanner input = new Scanner(file);
            input.useDelimiter("\\|\\s*");

            if (file.exists()) {
                while (input.hasNext()) {
                    String type = input.next();
                    TransactionType.valueOf(type);
                    double amount = input.nextDouble();
                    String date = input.next();
                    Transaction tran = new Transaction(TransactionType.
                            valueOf(type), amount, date);
                    inList.add(tran);
                }
                input.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * read expense from file
     * @param file record file
     * @param outList arraylist of enpense
     */
    public static void readExpenseFile(File file, TransactionList outList) {
        try {
            file = new File("expense.txt");

            Scanner input = new Scanner(file);
            input.useDelimiter("\\|\\s*");

            if (file.exists()) {
                while (input.hasNext()) {
                    String type = input.next();
                    TransactionType.valueOf(type);
                    double amount = input.nextDouble();
                    String date = input.next();
                    Transaction tran = new Transaction(TransactionType.
                            valueOf(type), amount, date);
                    outList.add(tran);
                }
                input.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * set regular expression to enter amount
     */
    public void CheckAmount() {

        Alert alertPrice1 = new Alert(Alert.AlertType.ERROR);
        alertPrice1.setTitle("Data Entry Error");
        alertPrice1.setHeaderText("Invalid Value Entered");
        alertPrice1.setContentText("You must enter a number.");
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
        Matcher isNum = pattern.matcher(tfAmount.getText());
        if (!isNum.matches()) {
            alertPrice1.showAndWait();
            noError = 0;
        } else {
            //when sell price less than,error windows will show up
            double price = Double.valueOf(tfAmount.getText());

            Alert alertPrice2 = new Alert(Alert.AlertType.ERROR);
            alertPrice2.setTitle("Data Entry Error");
            alertPrice2.setHeaderText("Invalid Value Entered");
            alertPrice2.setContentText("Amount must greater than 0.");
            if (price < 1) {
                alertPrice2.showAndWait();
                noError = 0;
            } else {
                noError++;
            }
        }
    }

    /**
     * set regular expression to search textfile
     */
    public void CheckSearch() {

        Alert alertPrice1 = new Alert(Alert.AlertType.ERROR);
        alertPrice1.setTitle("Data Entry Error");
        alertPrice1.setHeaderText("Invalid Value Entered");
        alertPrice1.setContentText("The format for search are: 1 or 1-10.");
        Pattern pattern1 = Pattern.compile("-?[0-9]+.?[0-9]*");
        Pattern pattern2 = Pattern.compile("-?[0-9]+.?[0-9]*--?[0-9]+.?[0-9]*");
        Matcher isNum1 = pattern1.matcher(tfSearch.getText());
        Matcher isNum2 = pattern2.matcher(tfSearch.getText());
        if (isNum1.matches() || isNum2.matches()) {
            noError++;
        } else {
            alertPrice1.showAndWait();
            noError = 0;
        }
    }

    /**
     * an error alert, when user edit wrong type of trans, this alert will be show up
     */
    public void TypeError() {
        Alert alertPrice1 = new Alert(Alert.AlertType.ERROR);
        alertPrice1.setTitle("Type Entry Error");
        alertPrice1.setHeaderText("Invalid Typr Entered");
        alertPrice1.setContentText("You can't change type of money.");
        alertPrice1.showAndWait();
    }
}
