

package Model;

import Connections.sqlLiteJDBCDriverConnection;
import Logger.Logger;
import Objects.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller;
import sun.rmi.runtime.Log;

public class Model extends Observable implements ISQLModel {
    private Controller controller;
    private sqlLiteJDBCDriverConnection driver = new sqlLiteJDBCDriverConnection();

    public Model() {
    }

    public void createUsersTable() {
        String url = "jdbc:sqlite:vacation_for_u.db";
        String sql = "CREATE TABLE IF NOT EXISTS users (\n\tusername text PRIMARY KEY,\n\tpassword text NOT NULL,\n\tbirth_date DATE ,\n\tfirst_name text NOT NULL,\n\tlast_name text NOT NULL,\n\taddress text NOT NULL\n);";

        try {
            Connection conn = DriverManager.getConnection(url);
            Throwable var4 = null;

            try {
                Statement stmt = conn.createStatement();
                Throwable var6 = null;

                try {
                    stmt.execute(sql);
                    Logger.getInstance().log("created new table users");
                } catch (Throwable var31) {
                    var6 = var31;
                    throw var31;
                } finally {
                    if (stmt != null) {
                        if (var6 != null) {
                            try {
                                stmt.close();
                            } catch (Throwable var30) {
                                var6.addSuppressed(var30);
                            }
                        } else {
                            stmt.close();
                        }
                    }

                }
            } catch (Throwable var33) {
                var4 = var33;
                throw var33;
            } finally {
                if (conn != null) {
                    if (var4 != null) {
                        try {
                            conn.close();
                        } catch (Throwable var29) {
                            var4.addSuppressed(var29);
                        }
                    } else {
                        conn.close();
                    }
                }

            }
        } catch (SQLException var35) {
            var35.printStackTrace();
            System.out.println(var35.getMessage());
            Logger.getInstance().log("failed to create new table users");
        }

    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void insert(String userName, String password, String firstName, String lastName, String city, Date birthDate) {
        String sql = "INSERT INTO users(username, password, birth_date, first_name, last_name ,address) VALUES(?,?,?,?,?,?)";

        try {
            Connection conn = this.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, city);
            pstmt.setDate(6, new Date(1, 2, 3));
            pstmt.executeUpdate();
            System.out.println("userName = [" + userName + "], password = [" + password + "], firstName = [" + firstName + "], lastName = [" + lastName + "], city = [" + city + "], birthDate = [" + birthDate + "]");
            this.closeConnection(conn);
            Logger.getInstance().log("INSERT : " + userName + " , " + password + " - SUCCESS");
        } catch (SQLException var10) {
            var10.printStackTrace();
            System.out.println(var10.getMessage());
            Logger.getInstance().log(var10.getMessage());
        }

    }

    public void deleteUsers(String userName) {
        String sql = "DELETE FROM users WHERE username = ? ";

        try{
            Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,userName);
            stmt.executeUpdate();
            Logger.getInstance().log("DELETED " + userName);
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
            Logger.getInstance().log("FAILED TO DELETE " + userName);
        }


    }

    public void updateUsers() {
    }

    public ObservableList selectAllDataBase() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM users";
        ObservableList result = null;

        try {
            Connection conn = this.openConnection();
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            result = this.convertResultsToObservableList(resultSet);
            conn.close();
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
            Logger.getInstance().log(var6.getMessage());
        }

        return result;
    }

    public ObservableList searchRecordsByFields(User fields) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM users\nWHERE ";
        ObservableList result = null;
        sql = sql + this.getFieldsForQuery(fields);

        try {
            Connection conn = this.openConnection();
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            result = this.convertResultsToObservableList(resultSet);
            conn.close();
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
            Logger.getInstance().log(var7.getMessage());
        }

        return result;
    }

    private ObservableList convertResultsToObservableList(ResultSet resultSet) {
        ObservableList observableList = FXCollections.observableArrayList();

        try {
            while(resultSet.next()) {
                observableList.add(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5) + " " + resultSet.getString(6));
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        return observableList;
    }

    private String getFieldsForQuery(User fields) {
        String ans = "";
        if (!fields.getUsername().equals("")) {
            ans = ans + "username = '" + fields.getUsername() + "',";
        }

        if (!fields.getPassword().equals("")) {
            ans = ans + "password = " + fields.getPassword() + ",";
        }

        if (!fields.getFirstname().equals("")) {
            ans = ans + "first_name = " + fields.getFirstname() + ",";
        }

        if (!fields.getLastname().equals("")) {
            ans = ans + "last_name = " + fields.getLastname() + ",";
        }

        if (!fields.getCity().equals("")) {
            ans = ans + "address = " + fields.getCity() + ",";
        }

        return ans.substring(0, ans.length() - 1);
    }

    public Connection openConnection() {
        Connection conn = this.driver.connect();
        Logger.getInstance().log("connection opened");
        return conn;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
            Logger.getInstance().log("connection closed");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }


    public void createUser(User user){
        Date sqlUserBirthDate = dateConvertor(user.getDate());
        insert(user.getUsername(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getCity(),sqlUserBirthDate);

    }


    private Date dateConvertor(String sDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        try {
            java.util.Date jDate = dateFormat.parse(sDate);
            java.sql.Date sqlDate = new java.sql.Date(jDate.getTime());
            return sqlDate;


        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("problem with parsing the date form string");
            return null;
        }

    }



}