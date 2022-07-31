package package01;

import javax.swing.*;

import java.sql.*;

public class Database {
    private static Connection c;
    private static Statement stmt;

    static String jdbcUrl = "jdbc:postgresql://localhost:5432/adventuredb";
    static String admin = "postgres";
    static String adminPass = "password";
    static int signInSuccess;

    public static void main(String[] args) {
        new Database();
    }

    public Database(){
        //connect to database
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(jdbcUrl, admin, adminPass);
            System.out.println("Connected to the database.");

        }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }

        //create login table
        try {
            stmt = c.createStatement();
            String sql = "create table IF NOT EXISTS userprogress (ID SERIAL," +
                    "USERNAME VARCHAR(20) NOT NULL, " +
                    "PASSWORD VARCHAR(20) NOT NULL, " +
                    "PROGRESSKEY VARCHAR(20)," +
                    "HEALTH INT," +
                    "LOCATION VARCHAR(20)" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("Table has been created");
        }
        catch(Exception excp) {
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }
    }

//    --------------------------------------------------CREATE NEW USER ------------------------------------------------
//    --------------------------------------------------CREATE NEW USER ------------------------------------------------
//    --------------------------------------------------CREATE NEW USER ------------------------------------------------
    
    public static void signUp(String user, String pass) {
        if(pass.length() < 4 || user.length() < 4){
            JFrame invalidPassword = new JFrame();
            JOptionPane.showMessageDialog(invalidPassword, "Username or Password is invalid. Please try again.");
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(jdbcUrl, admin, adminPass);
            System.out.println("Connected to the database.");

        }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }

            try {
                stmt = c.createStatement();

                String SQL = "SELECT * FROM userprogress where username ='" + user +"'";
                ResultSet rs = stmt.executeQuery(SQL);

                //check if username already exists in database
            if (rs.next()) {
                JFrame signUpFailure = new JFrame();
                JOptionPane.showMessageDialog(signUpFailure, "Username already taken. Please try again.");
                //signUpSuccess = 0;
            }

            //if username does not already exist, add user info to database
            else {
                c.setAutoCommit(false);
                String defaultKey = "0000000000000";
                int defaultHealth = 100;

                PreparedStatement st = c.prepareStatement("INSERT INTO userprogress(USERNAME, PASSWORD, PROGRESSKEY, HEALTH) VALUES(?,?,?,?)");
                st.setString(1, user);
                st.setString(2, pass);
                st.setString(3, defaultKey);
                st.setInt(4, defaultHealth);

                st.executeUpdate();

                st.close();
                c.commit();
                c.close();

                System.out.println("Added elements to table");
                JFrame signUpSuccessful = new JFrame();
                JOptionPane.showMessageDialog(signUpSuccessful, "Successfully created account. Sign in to continue.");
            }
        }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }

    }

    //    --------------------------------------------------LOG IN -------------------------------------------------
    //    --------------------------------------------------LOG IN -------------------------------------------------
    //    --------------------------------------------------LOG IN -------------------------------------------------

    public static void logIn(String user, String pass) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(jdbcUrl, admin, adminPass);
            System.out.println("Connected to the database.");

        }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }

        try {
            signInSuccess = 0;
            stmt = c.createStatement();
            String SQL = "SELECT * " +
                    "FROM userprogress " +
                    "WHERE username ='" + user + "'AND password ='" + pass + "'";
            ResultSet rs = stmt.executeQuery(SQL);
//            JFrame loginSuccess = new JFrame();
//            JOptionPane.showMessageDialog(loginSuccess, "Username already taken. Please try again.");

            //if user input matches database, login is successful
            if(rs.next()) {
                signInSuccess = 1;
                int user_id = rs.getInt("id");
                String progressKey = rs.getString("progresskey");
                int hp = rs.getInt("HEALTH");
                String curLocation = rs.getString("LOCATION");

                Player.setPlayer_id(user_id);
                Player.setProgresskey(progressKey);
                Player.set_HP(hp);
                Player.set_Location(curLocation);

//				Story.selectPositio (location);

                System.out.println("User info retrieved and set");


            }

            //username or password do not match database
            else {
                signInSuccess = 0;
                JFrame loginFailure = new JFrame();
                JOptionPane.showMessageDialog(loginFailure, "Username or password was not recognized. Please try again.");

            }

            rs.close();
            stmt.close();
            c.close();

        }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }
        //return signInSuccess;

    }

//    --------------------------------------------------SAVE USER INFO -------------------------------------------------
//    --------------------------------------------------SAVE USER INFO -------------------------------------------------
//    --------------------------------------------------SAVE USER INFO -------------------------------------------------


    public static void saveUserInfo(int userID, String updateKey, int updateHealth, String updateLocation) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(jdbcUrl, admin, adminPass);
            System.out.println("Connected to the database.");

        }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }

        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();

//          Update:     progressKey / health / location
    		String sql =
                    "UPDATE userprogress " +
                    "SET PROGRESSKEY  = '" + updateKey + "'," +
                         " HEALTH = " + updateHealth + "," +
                        " LOCATION = '" + updateLocation + "' " +
                    "WHERE id = " + userID + ";";

                stmt.executeUpdate(sql);
                c.commit();
            stmt.close();
            c.close();
            }
        catch(Exception excp){
            excp.printStackTrace();
            System.err.println(excp.getClass().getName() + ": " + excp.getMessage());
            System.exit(0);
        }
    }

}

