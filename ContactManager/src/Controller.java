import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Controller {

    public TextField name;
    public TextField tele;
    public TextField addr;
    public TextArea outPut;
    public TextField host;
    public TextField dbName;


    public String in_host; //50576
    public String in_dbName; //TestDB
    public String writeText;


    public void write(String text){
        try {

            File file = new File("ForManagerOnly.txt");
            Writer writer = new FileWriter(file, true);
            writer.write(  text + "\r\n");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void set(ActionEvent actionEvent) {
        in_host = host.getText();
        in_dbName = dbName.getText();
        System.out.println(in_host + in_dbName);
        System.out.println("set Done");
        outPut.setText("DataBase and Host saved!");

    }


    public void add(ActionEvent actionEvent) {

        String in_name = name.getText();
        String in_tele = tele.getText();
        String in_addr = addr.getText();
//        System.out.println(in_name);
//        System.out.println(in_tele);
//        System.out.println(in_addr);

        try {

        String connectionString = "jdbc:sqlserver://localhost:"+in_host+";DatabaseName="+in_dbName+";integratedSecurity=true";
        Connection myConn = DriverManager.getConnection(connectionString);

        Statement myStatement = myConn.createStatement();

        String query="INSERT INTO Contacts (Names ,tel ,addres) VALUES ('"+in_name+"','"+in_tele+"', '"+in_addr+"');";

        myStatement.executeUpdate(query);
	    outPut.setText("");
        outPut.setText("Contact saved!");
        System.out.println("add done");

        } catch (Exception e) {
        e.printStackTrace();
        }

        writeText =LocalDateTime.now() + " --- User1 add name: " + in_name + ", tel.: " + in_tele + ", address: " + in_addr;
        System.out.println(writeText);

        write(writeText);

    }


    public void search(ActionEvent actionEvent) {
        String in_name = name.getText();
        String in_tele = tele.getText();
        String in_addr = addr.getText();

        try {

            String connectionString = "jdbc:sqlserver://localhost:"+in_host+";DatabaseName="+in_dbName+";integratedSecurity=true";
            Connection myConn = DriverManager.getConnection(connectionString);

            Statement myStatement = myConn.createStatement();

            String query="SELECT * FROM Contacts WHERE Names = '"+in_name+"' OR tel = '"+in_tele+"' OR addres = '"+in_addr+"';";

            ResultSet myResultSet = myStatement.executeQuery(query);
            outPut.setText("");

            while (myResultSet.next()) {

                outPut.appendText(("Name: " + myResultSet.getString("Names") + "\n" +
                        "Telephon Number: "+ myResultSet.getString("tel") + "\n" +
                        "Address: " + myResultSet.getString("addres") ));

            }
            System.out.println("select done");

        } catch (Exception e) {
            e.printStackTrace();
        }

        writeText =LocalDateTime.now() + " --- User1 search " + outPut.getText();
        System.out.println(writeText);

        write(writeText);

    }
}