package zheng_zhao_project;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class zheng_zhao_project extends Application {
//create global varaiables File,TextField,ListView,ArrayList,Stage,etc. 
//for use in the whole java class
    File file;
    TextField nameField,dayField,hpField,energyField,foodField;
    ListView<Survivor> leftViewList;
    ArrayList<Survivor> singleDesperate;
    Stage primaryStage;
    Survivor player;
    String name;
    String saveName;
    int saveDay,saveHp,saveEnergy,saveFood;
    Label userName,countDay,countHp,countEnergy,countFood;
    Button btnCard;
    Button btnFood;

    /**This is the main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Scene scene = startPage();
        primaryStage.setTitle("Desperate");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
/**
 * This class is used to set up the start page
 * @return the Scene of startPage
 * startPage() created by Weijie and Wenzhuo
 * last modification by Weijie
 */
    private Scene startPage() {

        //set title to the start page
        Label title = new Label("   DESPERATE");
        title.setId("title");
        Label subTitle = new Label("The True Man Lives 100 Days");
        subTitle.setId("subTitle");
        
        //VBox to put title and subTitle
        VBox title_vBox = new VBox();
        title_vBox.getChildren().addAll(title, subTitle);
        
        //create the button for help and backgroup story
        Button btnInfo = new Button("Info");
        btnInfo.setId("btnInfo");
        
        //setOnAction to info button to swap the Stage
        btnInfo.setOnAction(e -> {
            InfoStage infoStage = new InfoStage();
            infoStage.showAndWait();

        });

        //create GridPane for textField
        GridPane inputPane = new GridPane();
        
        //set Vgap and Hgap to the inputPane
        inputPane.setHgap(5);
        inputPane.setVgap(5);

        //create the field of the player's name
        TextField username = new TextField();
        
        //set the prompt text for text field
        username.setPromptText("Enter you name");
        inputPane.addRow(0, new Label("Name"), username);
        username.setMaxWidth(170);
        username.setMinHeight(35);

        //create start game button
        Button btnStart = new Button("START GAME");
        
        //set id for start button to style it
        btnStart.setId("btnStart");
        
        //create the listener for the text field
        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                name = username.getText();
                
                //create a Survivor Object for main page
                player = new Survivor(name, 1, 100, 10, 10);
                
                //setOnAction to swap stage to main game stage
                btnStart.setOnAction(e -> {
                    Scene nextPage = mainPage(player);
                    primaryStage.setScene(nextPage);
                });
            }
        });

        //create the continue button to continue to play the game
        Button btnContinue = new Button("CONTINUE");
        btnContinue.setId("btnContinue");
        
        //setOnAction to swap stage to load page
        btnContinue.setOnAction(e -> {
            LoadStage loadStage = new LoadStage();
            loadStage.showAndWait();
        });

        //create close game button to close the primaryStage
        Button btnCloseGame_startPage = new Button("Close Game");
        btnCloseGame_startPage.setOnAction(e -> {
            primaryStage.close();
        });
        btnCloseGame_startPage.setId("btnCloseGame_startPage");

        //create the VBox for buttons
        VBox startPage_vBox = new VBox(5);
        startPage_vBox.getChildren().addAll(username,
                btnStart, btnContinue, btnCloseGame_startPage);
        
        //set the position for the startPage_VBox
        startPage_vBox.setAlignment(Pos.CENTER);

        //create the label for our group's name
        Label signature = new Label("WZ Studio");
        
        //create the border pane
        BorderPane startPage_Bp = new BorderPane();
        
        //set id to style it
        startPage_Bp.setId("startPage_Bp");

        //set the alignment and the padding of the startPage_Bp
        startPage_Bp.setPadding(new Insets(20, 20, 20, 20));
        startPage_Bp.setLeft(title_vBox);
        startPage_Bp.setRight(btnInfo);
        startPage_Bp.setBottom(startPage_vBox);

        //create the stackPane for the label and pane
        StackPane startPage_Sp = new StackPane();
        
        //add signature and startPage_Bp to the startPage_Sp
        startPage_Sp.getChildren().addAll(signature, startPage_Bp);
        
        //add the startPage_Sp to the Scene  
        Scene scene = new Scene(startPage_Sp, 640, 442);
        scene.getStylesheets().add("/css/startpage.css");

        //return the scene, when this method is called
        return scene;
    }

    /**
     * This Scene mainPage is used to set game main page
     * @param player player is the object of survivor
     * @return the scene of this mainPage
     * mainPage() created by Weijie and Wenzhuo
     * last modification by Wenzhuo
     */
    private Scene mainPage(Survivor player) {
        //set shadow for three buttons
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0);
        ds.setOffsetX(5.0);
        ds.setColor(Color.BLACK);

        //create three button and style them
        btnCard = new Button("Card");
        btnCard.setId("btnCard");
        btnCard.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnCard.setStyle("-fx-border-color: gray;");
            btnCard.setEffect(ds);
        });
        btnCard.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnCard.setStyle("-fx-border-color: white;");
            btnCard.setEffect(null);
        });

        btnFood = new Button("Hunt");
        btnFood.setId("btnFood");  
        btnFood.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnFood.setStyle("-fx-border-color: gray;");
            btnFood.setEffect(ds);
        });
        btnFood.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnFood.setStyle("-fx-border-color: white;");
            btnFood.setEffect(null);
        });
        
        Button btnSleep = new Button("Sleep");
        btnSleep.setId("btnSleep");
        btnSleep.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSleep.setStyle("-fx-border-color: gray;");
            btnSleep.setEffect(ds);
        });
        btnSleep.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSleep.setStyle("-fx-border-color: white;");
            btnSleep.setEffect(null);
        });

        //create different label for game main stage
        name = player.getUserName();
        userName = new Label("name: " + name);
        countDay = new Label("Day: " + player.getDay());
        countHp = new Label("HP: " + player.getHp());
        countEnergy = new Label("Energy: " + player.getEnergy());
        countFood = new Label("Food: " + player.getFood());
        
        //sleep button call sleep method
        btnSleep.setOnAction(e -> sleep());

        //hunt button call hunt method
        btnFood.setOnAction(e -> food());

        //card button call card method
        btnCard.setOnAction(e -> card());

        countDay.setPadding(new Insets(10));
        countDay.setId("countDay");
        
        ImageView imageView = new ImageView("/img/save.jpg");
        imageView.setFitWidth(45);
        imageView.setFitHeight(45);

        //create save button to save the status of the player
        Button btnSave = new Button();
        btnSave.setId("btnSave");
        btnSave.setGraphic(imageView);

        //use save button to save the game
        btnSave.setOnAction((ActionEvent e) -> {
            
            //the format of the string to write to the file
            String format = "%s,%d,%d,%d,%d\n";
            
            //the value of the string that write to the file
            String content = String.format(format, this.player.getUserName(),
                    this.player.getDay(), this.player.getHp(),
                    this.player.getEnergy(), this.player.getFood());
            File file = new File("PlaySaveInfo.txt");
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(content);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(zheng_zhao_project.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(countDay, btnSave);

        VBox playerInfo_vBox = new VBox(10);
        playerInfo_vBox.getChildren().addAll(
                userName, countHp, countEnergy, countFood);
        playerInfo_vBox.setPadding(new Insets(10));
        playerInfo_vBox.setId("playerInfo");

        FlowPane info = new FlowPane(playerInfo_vBox);
        info.setAlignment(Pos.TOP_RIGHT);

        HBox action_hBox = new HBox(25);
        
        //add three button to the Hbox
        action_hBox.getChildren().addAll(btnCard, btnFood, btnSleep);
        
        //set the alignment and the padding of the hbox
        action_hBox.setAlignment(Pos.BOTTOM_CENTER);
        action_hBox.setPadding(new Insets(50));

        //create the BorderPane and set the padding
        BorderPane mainPage_Bp = new BorderPane();
        mainPage_Bp.setPadding(new Insets(20, 20, 0, 20));

        mainPage_Bp.setLeft(vBox);
        mainPage_Bp.setRight(info);
        mainPage_Bp.setBottom(action_hBox);

        //create a new Scene of the main page
        Scene scene = new Scene(mainPage_Bp, 640, 470);
        scene.getStylesheets().add("css/main.css");

        //press the ESC key, it will swap to menu page
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                MenuStage menuStage = new MenuStage();
                menuStage.showAndWait();
            }
        });
        
        //return the scene,when this class is called
        return scene;
    }

    /** This class is used to present background story and some help and tips
     * this class created by Wenzhuo
     */
    class InfoStage extends Stage {
        public InfoStage() {
            super();
            //create label for content : background story
            Label storyTitle = new Label("Background Story");
            storyTitle.setId("storyTitle");
            Label bgStory = new Label("    The character want to rest and change"
                    + " from his busy work, he decided to go for a trip."
                    + " However, the ship had gone through the heavy storm"
                    + " weather during the journey. When the ship was sinking,"
                    + " a streak of lightening whisked him into a different"
                    + " magic world. He discovered that he landed on an"
                    + " old island. This old island had a number"
                    + " of skeletons to discover.");
            bgStory.setId("bgStory");
            bgStory.setWrapText(true);
            bgStory.setContentDisplay(ContentDisplay.BOTTOM);

            //create label for content : get help
            Label helpTitle = new Label("Helps");
            helpTitle.setId("helpTitle");
            Label help = new Label("type your player name to start the game. "
                    + "When you click the card button, it will reduce 2 energy points to get different events. "
                    + "When you click the HUNT button, it will increase the food"
                    + "When your energy point is equal to zero,sleep to refill the energy point"
                    + "Tips: "
                    + "When your have no food, Do not Sleep!");
            help.setId("help");
            help.setWrapText(true);

            //create close button to swap to start page
            Button btnClose = new Button("Close");
            btnClose.setOnAction(e -> close());
            btnClose.setId("btnClose_info");

            VBox info_vBox = new VBox();
            info_vBox.getChildren().
                    addAll(storyTitle, bgStory, helpTitle, help, btnClose);
            info_vBox.setAlignment(Pos.CENTER);
            info_vBox.setPadding(new Insets(20));

            //create scene for info_vBox
            Scene scene = new Scene(info_vBox, 600, 400);
            scene.getStylesheets().add("/css/startpage.css");
            
            //set title and scene 
            setTitle("Background and Helps");
            setScene(scene);
            initModality(Modality.APPLICATION_MODAL);
        }
    }

    /**this class is used to load the file that save the status of the player
     * this class was created by weijie and Wenzhuo
     * last modification by weijie
     */ 
    class LoadStage extends Stage {
        public LoadStage() {
            super();
            //create the field of the name
            nameField = new TextField();
            nameField.setPrefColumnCount(8);

            //create the field of the hp
            hpField = new TextField();
            hpField.setPrefColumnCount(4);

            //create the field of the energy
            energyField = new TextField();
            energyField.setPrefColumnCount(4);

            //create the field of the user day
            dayField = new TextField();
            dayField.setPrefColumnCount(4);

            //create the field of food
            foodField = new TextField();
            foodField.setPrefColumnCount(4);

            //create the vbox 
            VBox fieldPane = new VBox(10);

            //create two buttons for continue game and close stage
            //set the action to these two botton
            Button btnContinue_load = new Button("Continue");
            btnContinue_load.setId("btnContinue_load");
            btnContinue_load.setOnAction((ActionEvent e) -> {
                Scene continueScene = mainPage(player);
                close();
                primaryStage.setScene(continueScene);
            });
            Button btnClose_load = new Button("Close");
            btnClose_load.setId("btnClose_load");
            btnClose_load.setOnAction((ActionEvent e) -> {
                close();
            });
            
            //create the btnPane for button
            HBox btnPane = new HBox(10);

            //set the Alignment and the padding of the btnPane
            btnPane.setPadding(new Insets(10));
            btnPane.setAlignment(Pos.CENTER);

            //add two buttons to the button pane 
            btnPane.getChildren().addAll(btnContinue_load, btnClose_load);

            //create the border pane
            BorderPane savePane = new BorderPane();
            savePane.setId("loadStage_Bp");

            //set padding to the borderPane
            savePane.setPadding(new Insets(10));

            //create the GridPane for field
            GridPane inputPane = new GridPane();

            //set Vgap and Hgap to the inputPane
            inputPane.setVgap(13);
            inputPane.setHgap(1);

            //set padding for fieldPane
            fieldPane.setPadding(new Insets(10, 10, 10, 10));

            //set the fieldPane to center in the border pane
            savePane.setCenter(fieldPane);

            //add the field and the label to input pane
            inputPane.addRow(0, new Label("Name: "), nameField);
            inputPane.addRow(1, new Label("HP : "), hpField);
            inputPane.addRow(2, new Label("Day : "), dayField);
            inputPane.addRow(3, new Label("Energy :    "), energyField);
            inputPane.addRow(4, new Label("Food :    "), foodField);

            //add the inputPane to the fieldPane
            fieldPane.getChildren().add(inputPane);
            fieldPane.getChildren().add(btnPane);
            
            //create the listview 
            leftViewList = new ListView();
            leftViewList.setPrefSize(150, 20);

            //set the listView to the left in the borderPane
            savePane.setLeft(leftViewList);

            leftViewList.getSelectionModel().selectedItemProperty().
                    addListener(new ChangeListener<Survivor>() {
                        @Override
                        public void changed(ObservableValue<? extends Survivor> observable, Survivor oldValue, Survivor newValue) {
                            leftViewList.getSelectionModel().
                                    getSelectedIndices().forEach((i) -> {
                                        try {
                                            if (oldValue != null) {
                                                oldValue.setDay(Integer.
                                                        parseInt(dayField.getText()));
                                                oldValue.setEnergy(Integer.
                                                        parseInt(energyField.getText()));
                                                oldValue.setHp(Integer.
                                                        parseInt(hpField.getText()));
                                                oldValue.setFood(Integer.
                                                        parseInt(foodField.getText()));
                                            }
                                        } catch (NumberFormatException ex) {
                                            System.out.println(ex.toString());
                                        }
                                        dayField.setText(String.valueOf(
                                                singleDesperate.get(i).getDay()));
                                        hpField.setText(String.valueOf(
                                                singleDesperate.get(i).getHp()));
                                        energyField.setText(String.valueOf(
                                                singleDesperate.get(i).getEnergy()));
                                        nameField.setText(String.valueOf(
                                                singleDesperate.get(i).getUserName()));
                                        foodField.setText(String.valueOf(
                                                singleDesperate.get(i).getFood()));
                                        dayField.setEditable(false);
                                        hpField.setEditable(false);
                                        energyField.setEditable(false);
                                        nameField.setEditable(false);
                                        foodField.setEditable(false);

                                        try {
                                            saveDay = Integer.parseInt(dayField.getText());
                                            saveName = nameField.getText();
                                            saveHp = Integer.parseInt(hpField.getText());
                                            saveEnergy = Integer.parseInt(
                                                    energyField.getText());
                                            saveFood = Integer.parseInt(
                                                    foodField.getText());
                                        } catch (Exception ex) {
                                            System.out.println(ex.toString());
                                        } finally {
                                            player = new Survivor(saveName,
                                                    saveDay, saveHp, saveEnergy, saveFood);
                                        }

                                    });
                        }
                    });

            initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(savePane, 400, 270);
            scene.getStylesheets().add("/css/startpage.css");
            setTitle("Load Stages");
            setScene(scene);
            loadFile();
        }
    }

    /**
     * a stage to display menu
     * this class was created by weijie
     */
    class MenuStage extends Stage {
        public MenuStage() {
            super();

            //button to back to game
            Button btnBackToGame = new Button("Back To Game");
            btnBackToGame.setOnAction(e -> close());
            btnBackToGame.setId("btnBackToGame");

            //button to back to start page
            Button btnBackToStartPage = new Button("Back To StartPage");
            btnBackToStartPage.setOnAction(e -> {
                primaryStage.setScene(startPage());
                close();
            });
            btnBackToStartPage.setId("btnBackToStartPage");

            //button to close game
            Button btnCloseGame_Menu = new Button("Exit Game");
            btnCloseGame_Menu.setOnAction(e -> {
                primaryStage.close();
                close();
            });
            btnCloseGame_Menu.setId("btnCloseGame_Menu");

            //a VBox to add all the button
            VBox menu_vBox = new VBox(10);
            menu_vBox.getChildren().addAll(btnBackToGame,
                    btnBackToStartPage, btnCloseGame_Menu);
            menu_vBox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(menu_vBox, 300, 200);
            scene.getStylesheets().add("/css/main.css");

            setTitle("System Menu");
            setScene(scene);

            //make game can't click, 
            initModality(Modality.APPLICATION_MODAL);
        }
    }

    /**
     * a method to load file
     * this method was created by wenzhuo
     */
    private void loadFile() {
        File selectedFile = new File("PlaySaveInfo.txt");
        singleDesperate = new ArrayList<>();
        //it will scanne the string,and add the object to the arraylist
        try {
            try (Scanner fileInput = new Scanner(selectedFile)) {
                while (fileInput.hasNextLine()) {
                    String lineIn = fileInput.nextLine();
                    String[] lineInSplit = lineIn.split(",");
                    Survivor desperate = new Survivor(
                            lineInSplit[0],
                            Integer.parseInt(lineInSplit[1]),
                            Integer.parseInt(lineInSplit[2]),
                            Integer.parseInt(lineInSplit[3]),
                            Integer.parseInt(lineInSplit[4]));
                    singleDesperate.add(desperate);
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.toString());
        }
        //add all the PlaySaveInfobtn Object to the ListView
        leftViewList.getItems().addAll(singleDesperate);

    }

    /**
     * a method to create card event, it can have different result
     *
     * @throws HeadlessException
     * this method was created by wenzhuo
     */
    private void card() throws HeadlessException {

        //take a card -2 energy
        this.player.setEnergy(this.player.getEnergy() - 2);
        checkEnergy();
        countEnergy.setText("Energy: " + this.player.getEnergy());

        //choose one of three main event
        int choice = (int) (Math.random() * 3);

        //choose a random result
        if (choice == 0) {
            int number = (int) (Math.random() * 15);

            ArrayList<Integer> foodPoint = new ArrayList<>(15);
            foodPoint.add(1);
            foodPoint.add(2);
            foodPoint.add(3);
            foodPoint.add(4);
            foodPoint.add(5);
            foodPoint.add(6);
            foodPoint.add(7);
            foodPoint.add(0);
            foodPoint.add(-1);
            foodPoint.add(-2);
            foodPoint.add(-3);
            foodPoint.add(-4);
            foodPoint.add(-5);
            foodPoint.add(-6);
            foodPoint.add(-7);

            this.player.setFood(player.getFood()
                    + foodPoint.get(number));
            //display result
            JOptionPane.showMessageDialog(null, "food +"
                    + foodPoint.get(number),
                    "Result", JOptionPane.INFORMATION_MESSAGE);

            checkFood();

            countFood.setText("Food: " + this.player.getFood());

        }
        //choose a random result
        if (choice == 1) {

            int number = (int) (Math.random() * 40);
            ArrayList<Integer> hp = new ArrayList<>(40);
            int full = 100 - this.player.getHp();

            hp.add(1);
            hp.add(3);
            hp.add(5);
            hp.add(6);
            hp.add(7);
            hp.add(9);
            hp.add(12);
            hp.add(23);
            hp.add(43);
            hp.add(32);
            hp.add(60);
            hp.add(25);
            hp.add(34);
            hp.add(5);
            hp.add(8);
            hp.add(11);
            hp.add(18);
            hp.add(52);
            hp.add(25);
            hp.add(full);
            hp.add(-1);
            hp.add(-1);
            hp.add(-5);
            hp.add(-23);
            hp.add(-16);
            hp.add(-23);
            hp.add(-45);
            hp.add(-31);
            hp.add(-8);
            hp.add(-9);
            hp.add(-11);
            hp.add(-25);
            hp.add(-18);
            hp.add(-5);
            hp.add(-2);
            hp.add(-36);
            hp.add(-5);
            hp.add(-16);
            hp.add(-15);
            hp.add(-8);

            this.player.setHp(player.getHp() + hp.get(number));
            //display result
            JOptionPane.showMessageDialog(null, "hp +"
                    + hp.get(number),
                    "Result", JOptionPane.INFORMATION_MESSAGE);

            checkHp();

            countHp.setText("HP: " + this.player.getHp());

        }
        //choose a random result
        if (choice == 2) {
            int number = (int) (Math.random() * 40);
            ArrayList<Integer> energyPoint = new ArrayList<>(40);
            int fullEnergy = 10 - this.player.getEnergy();

            energyPoint.add(1);
            energyPoint.add(3);
            energyPoint.add(5);
            energyPoint.add(6);
            energyPoint.add(7);
            energyPoint.add(9);
            energyPoint.add(12);
            energyPoint.add(23);
            energyPoint.add(43);
            energyPoint.add(32);
            energyPoint.add(60);
            energyPoint.add(25);
            energyPoint.add(34);
            energyPoint.add(5);
            energyPoint.add(8);
            energyPoint.add(11);
            energyPoint.add(18);
            energyPoint.add(52);
            energyPoint.add(25);
            energyPoint.add(fullEnergy);
            energyPoint.add(-1);
            energyPoint.add(-1);
            energyPoint.add(-5);
            energyPoint.add(-23);
            energyPoint.add(-16);
            energyPoint.add(-23);
            energyPoint.add(-45);
            energyPoint.add(-31);
            energyPoint.add(-8);
            energyPoint.add(-9);
            energyPoint.add(-11);
            energyPoint.add(-25);
            energyPoint.add(-18);
            energyPoint.add(-5);
            energyPoint.add(-2);
            energyPoint.add(-36);
            energyPoint.add(-5);
            energyPoint.add(-16);
            energyPoint.add(-15);
            energyPoint.add(-8);

            this.player.setEnergy(player.getEnergy() + energyPoint.get(number));
            //display result
            JOptionPane.showMessageDialog(null, "energy +"
                    + energyPoint.get(number),
                    "Result", JOptionPane.INFORMATION_MESSAGE);

            checkEnergy();

            countEnergy.setText("Energy: " + this.player.getEnergy());

        }
    }

  /**
   * method to create food event
   * @throws HeadlessException 
   * this method was created by weijie and wenzhuo
   * last modification by weijie
   */
    private void food() throws HeadlessException {

        //use 2 energy to hunt
        this.player.setEnergy(this.player.getEnergy() - 2);
        checkEnergy();
        countEnergy.setText("Energy: " + this.player.getEnergy());

        //choose a random result
        int number1 = (int) (Math.random() * 11);

        ArrayList<Integer> foodPoint = new ArrayList<>(11);
        foodPoint.add(1);
        foodPoint.add(1);
        foodPoint.add(1);
        foodPoint.add(2);
        foodPoint.add(2);
        foodPoint.add(3);
        foodPoint.add(3);
        foodPoint.add(4);
        foodPoint.add(4);
        foodPoint.add(5);
        foodPoint.add(7);

        this.player.setFood(player.getFood() + foodPoint.get(number1));
        checkFood();

        countFood.setText("Food: " + this.player.getFood());
        //choose a random result    
        int number2 = (int) (Math.random() * 11);
        ArrayList<Integer> hp = new ArrayList<>(11);
        hp.add(-0);
        hp.add(-1);
        hp.add(-1);
        hp.add(-1);
        hp.add(-2);
        hp.add(-2);
        hp.add(-4);
        hp.add(-4);
        hp.add(-5);
        hp.add(-6);
        hp.add(-8);

        this.player.setHp(player.getHp() + hp.get(number2));

        //display different result
        if (number1 < 3) {
            JOptionPane.showMessageDialog(null, "You hunt a fish, Food +"
                    + foodPoint.get(number1) + "\n"
                    + "Hp " + hp.get(number2),
                    "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (number1 > 3 && number1 <= 5) {
            JOptionPane.showMessageDialog(null, "You hunt a hare, Food +"
                    + foodPoint.get(number1) + "\n"
                    + "Hp " + hp.get(number2),
                    "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (number1 > 5 && number1 <= 8) {
            JOptionPane.showMessageDialog(null, "You hunt a sheep, Food +"
                    + foodPoint.get(number1) + "\n"
                    + "Hp " + hp.get(number2),
                    "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (number1 > 8) {
            JOptionPane.showMessageDialog(null, "You hunt a wild boar, Food +"
                    + foodPoint.get(number1) + "\n"
                    + "Hp " + hp.get(number2),
                    "Result", JOptionPane.INFORMATION_MESSAGE);
        }

        checkHp();
        countHp.setText("HP: " + this.player.getHp());

    }

/**
 * a method to create sleep event
 * this method was created by weijie
 */
    private void sleep() {
        // when sleep, day+1, get full energy energy, and lose hp or gain hp
        this.player.setDay(player.getDay() + 1);
        countDay.setText("Day: " + this.player.getDay());

        this.player.setEnergy(10);
        countEnergy.setText("Energy: " + player.getEnergy());
        btnFood.setOnAction(e -> food());
        btnCard.setOnAction(e -> card());

        // if food greater than 9 gain 20 hp everday 
        if (this.player.getFood() > 9) {

            this.player.setHp(player.getHp() + 20);
            checkHp();
            countHp.setText("Hp: " + player.getHp());

            JOptionPane.showMessageDialog(null, "A day passed you ate 5 foods,"
                    + " you restored 20 hp.",
                    "Result", JOptionPane.INFORMATION_MESSAGE);
            //if food less than 1, lose 20hp everyday
        } else if (this.player.getFood() < 1) {

            this.player.setFood(0);

            this.player.setHp(player.getHp() - 30);
            countHp.setText("Hp: " + this.player.getHp());

            JOptionPane.showMessageDialog(null, "Because of you ahve nothing"
                    + " to eat, you lost 30hp.",
                    "Result", JOptionPane.INFORMATION_MESSAGE);

            checkHp();
        } else {

            JOptionPane.showMessageDialog(null, "A day passed you ate 5 food,",
                    "Result", JOptionPane.INFORMATION_MESSAGE);

        }
        //minus 5 foods everyday
        this.player.setFood(player.getFood() - 5);
        checkFood();
        countFood.setText("Food: " + player.getFood());

    }
    // a method to check hp, if hp less than 0 gameover,
    //and hp can't greater than 100
    //this method was created by weijie and wenzhuo
    //last modification by wenzhuo
    private void checkHp() throws HeadlessException {
        if (this.player.getHp() < 0) {

            this.player.setHp(0);
            countHp.setText("HP: " + player.getHp());

            GameOverStage gameOver = new GameOverStage();
            gameOver.showAndWait();

        } else if (this.player.getHp() > 100) {

            this.player.setHp(100);

        }
    }
    //a method to check energy, if energy less than 0, can't do any thing,
    //and energy cant greater than 10
    //this method was created by weijie 
    private void checkEnergy() {

        if (this.player.getEnergy() < 0) {

            this.player.setEnergy(0);
            countEnergy.setText("Energy: " + this.player.getEnergy());

        }

        if (this.player.getEnergy() < 1) {

            btnCard.setOnAction(e -> {

                NoEnergyMes noEnergyMes = new NoEnergyMes();
                noEnergyMes.showAndWait();

            });

            btnFood.setOnAction(e -> {

                NoEnergyMes noEnergyMes = new NoEnergyMes();
                noEnergyMes.showAndWait();

            });
        } else if (this.player.getEnergy() > 10) {

            this.player.setEnergy(10);

        }
    }

    
    //a method to check food, food can't less than 0 and greater than 10
    //this method was created by wenzhuo 
    private void checkFood() {
        if (this.player.getFood() > 10) {

            this.player.setFood(10);
            
        } else if (this.player.getFood() < 0) {

            this.player.setFood(0);

        }
    }
    // a class will display message when no energy
    //this method was created by weijie 
    class NoEnergyMes extends Stage {

        public NoEnergyMes() {
            super();

            Label message_energy = new Label("no energy");
            message_energy.setId("message_energy");
            Button btnClose_energy = new Button("Close");
            btnClose_energy.setId("btnClose_energy");

            btnClose_energy.setOnAction(e -> {
                close();
            });

            VBox message_vBox = new VBox(10);
            message_vBox.getChildren().addAll(message_energy, btnClose_energy);
            message_vBox.setAlignment(Pos.CENTER);
            message_vBox.setPadding(new Insets(10));

            Scene scene = new Scene(message_vBox, 200, 150);
            scene.getStylesheets().add("/css/main.css");

            setTitle("Message");
            setScene(scene);

            initModality(Modality.APPLICATION_MODAL);
        }
    }
    // a class will display message when gameover
    //this method was created by weijie 
    class GameOverStage extends Stage {

        public GameOverStage() {
            super();

            Label message_gameOver = new Label("Game Over");
            message_gameOver.setId("message_gameOver");
            Button btnClose_gameOver = new Button("Close");
            btnClose_gameOver.setId("btnClose_gameOver");

            btnClose_gameOver.setOnAction(e -> {
                primaryStage.setScene(startPage());
                close();
            });

            VBox message_vBox = new VBox(10);
            message_vBox.getChildren().addAll(message_gameOver, btnClose_gameOver);
            message_vBox.setPadding(new Insets(10));
            message_vBox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(message_vBox, 200, 150);
            scene.getStylesheets().add("/css/main.css");

            setTitle("Message");
            setScene(scene);

            initModality(Modality.APPLICATION_MODAL);
        }
    }
}
