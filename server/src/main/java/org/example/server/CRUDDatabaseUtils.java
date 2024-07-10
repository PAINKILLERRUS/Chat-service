package org.example.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDDatabaseUtils {

    public static User findUserByLoginAndPassword(String login, String password) {
        String select = "SELECT * FROM user_table WHERE login =? AND password =?";
        try (Connection connection = JDBCUserService.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String login1 = rs.getString("login");
                        String password1 = rs.getString("password");
                        String nickname = rs.getString("nickname");
                        Role role = Role.valueOf(rs.getString("role"));
                        return new User(id, login1, password1, nickname, role);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User findRoleByLoginAndPassword(String login, String password) {
        String select = "SELECT * FROM user_table WHERE login =? AND password =?";
        try (Connection connection = JDBCUserService.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String login1 = rs.getString("login");
                        String password1 = rs.getString("password");
                        String nickname = rs.getString("nickname");
                        Role role = Role.valueOf(rs.getString("role"));
                        return new User(id, login1, password1, nickname, role);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User registerUser(String login, String password, String nickname) {
        String select = "INSERT INTO user_table(login, password, nickname) VALUES(?, ?, ?)";
        try (Connection connection = JDBCUserService.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, nickname);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User checkLogin(String login) {
        String select = "SELECT * FROM user_table WHERE login =?";
        try (Connection connection = JDBCUserService.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
                preparedStatement.setString(1, login);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String login1 = rs.getString("login");
                        String password1 = rs.getString("password");
                        String nickname1 = rs.getString("nickname");
                        Role role = Role.valueOf(rs.getString("role"));
                        return new User(id, login1, password1, nickname1, role);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User checkNickName(String nickName) {
        String select = "SELECT * FROM user_table WHERE nickname =?";
        try (Connection connection = JDBCUserService.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
                preparedStatement.setString(1, nickName);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String login1 = rs.getString("login");
                        String password1 = rs.getString("password");
                        String nickname1 = rs.getString("nickname");
                        Role role = Role.valueOf(rs.getString("role"));
                        return new User(id, login1, password1, nickname1, role);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
