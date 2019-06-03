package Futoshiki;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

/**
 * Provides the entry point for the Futoshiki project,
 * extending the JavaFX Application class ....
 * created 06/01/2019 by wdc
 * last modified 06/02/2019 by wdc - experimenting with TextField options
 * previously modified 06/01/2019 by wdc - initial creation
 * @author Warren D. Craft (wdc)
 * @author Tyler H. Fenske (thf)
 */
public class Main extends Application {

    // helpful constants and other fields
    private final int PANE_WIDTH = 600;
    private final int PANE_HEIGHT = 400;

    /**
     * The start() override method required of applications extending the
     * JavaFX Application class, serving to set up the initial display and
     * controls.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Futoshiki");

        // Some elementary stage/scene-setting
        // which will eventually be moved to a separate GUI class

        BorderPane theBorderPane = new BorderPane();

        // HBox to hold buttons, controls, etc., at top of BorderPane
        HBox hBoxForTop = new HBox();

        // VBox to hold buttons, controls, etc., in left portion of BorderPane
        VBox vBoxForLeft = new VBox();

        // HBox to hold buttons, controls, etc., at bottom of BorderPane
        HBox hBoxForBottom = new HBox();

        // Group to hold content in center of BorderPane
        Group groupForCenter = new Group();
        // HBox to hold content in center of BorderPane
        HBox hBoxForCenter = new HBox();
        // The following borrowed from Uwe (Apr 5 '16 at 20:18) at
        // https://stackoverflow.com/questions/7555564/...
        // what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        // to constrain user TextField input to desired integers
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("\\d{0,1}")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextField exampleTextField01 = new TextField("1");
        exampleTextField01.setTextFormatter(textFormatter);

        // A 2nd try.
        // The following borrowed from Emily L. (Apr 20 '16 at 16:18) at
        // https://stackoverflow.com/questions/7555564/...
        // what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        // to constrain user TextField input to desired integers
        TextFormatter<Integer> formatter = new TextFormatter<Integer>(
            new IntegerStringConverter(),
            0,
            new IntegerFilter());
        TextField exampleTextField02 = new TextField("2");
        exampleTextField02.setTextFormatter(formatter);
        exampleTextField02.setText("5");
        exampleTextField02.setPrefColumnCount(1);
        exampleTextField02.setMinWidth(20);

        // A 3rd try.
        // The following based on code borrowed from
        // http://www.java2s.com/Tutorials/...
        // Java/JavaFX/0460__JavaFX_TextField.htm (Example 3)
        TextField exampleTextField03 = new TextField("3") {
            @Override
            public void replaceText(int start, int end, String text) {
                if (text.matches("^$|^[1-7]{0,1}$")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("^$|^[1-7]{0,1}$")) {
                    super.replaceSelection(text);
                }
            }
        };
        // and the following based on code borrowed from
        // ItachiUchiha (Mar 28 '14 at 18:27) at
        // https://stackoverflow.com/questions/22714268/...
        // how-to-limit-the-amount-of-characters-a-javafx-textfield/47362303
        exampleTextField03.lengthProperty().addListener(
            new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (exampleTextField03.getText().length() >= 1) {

                        // if it's the 2nd character then just setText to
                        // previous one
                        exampleTextField03.setText(
                            exampleTextField03.getText().substring(0, 1)
                        );
                    }
                }
            }

        });
        exampleTextField03.setPrefHeight(100);
        exampleTextField03.setPrefWidth(100);
        exampleTextField03.setMinWidth(100);
        // exampleTextField03.setMaxWidth(100);
        exampleTextField03.setAlignment(Pos.CENTER);


        // A label for testing
        Label exampleLabel = new Label("Info Here");

        hBoxForCenter.getChildren().addAll(
            exampleTextField01, exampleTextField02, exampleTextField03,
            exampleLabel);

        // Put the BorderPane together
        theBorderPane.setTop(hBoxForTop);
        theBorderPane.setLeft(vBoxForLeft);
        theBorderPane.setBottom(hBoxForBottom);
        theBorderPane.setCenter(hBoxForCenter);


        // Establish the Scene
        Scene scene = new Scene(theBorderPane, PANE_WIDTH, PANE_HEIGHT);

        // Add the Scene to the Stage
        primaryStage.setScene(scene);

        // Display contents of the Stage
        primaryStage.show();
    }

    /**
     * The stop() override method required of applications extending the
     * JavaFX Application class; the stop() method is currently empty.
     */
    @Override
    public void stop(){
        // System.out.println("Goodbye!");
    }


    /**
     * The main() method within the Main class, serving to initiate the
     * required start() override method.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }



}
