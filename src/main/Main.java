package main;

import helper.FruitsQuery;
import helper.usersQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 500,208));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        
        JDBC.openConnection();


        //TestScripts.loginTest();

        launch(args);


        //program close
        JDBC.closeConnection();
        System.exit(0);
    }

    public static void lambdas(){
        // value returning lambda expression
        //GeneralInterface square = n -> n * n;
        //System.out.println(square.calculateSquare(5));

        // void lambda expression with one parameter
        //GeneralInterface message = s -> System.out.println("hello again " + s);
        //message.displayMessage("Malcolm!");

        // void Lambda Expression with one parameter

        // Multiple parameter Lambda Expression
        //GeneralInterface sum = (n1, n2) -> n1 + n2;
        //System.out.println(sum.calculateSum(5, 0));

        // no parameter Lambda Expression
        //GeneralInterface message = () -> System.out.println("hello World! ");
        //message.displayMessage();

        // multiple statement Lambda Expression
        /*GeneralInterface square = n -> {
            int result = n * n;
            return result;
        };
        System.out.println(square.calculateSquare(6));*/

        // using local variable in lambda expression
         /*final int num = 50;

        GeneralInterface square = n -> n * n;
        System.out.println(square.calculateSquare(num));*/
    }
}