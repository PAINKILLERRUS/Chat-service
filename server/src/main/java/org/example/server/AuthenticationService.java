package org.example.server;

import java.sql.SQLException;

public interface AuthenticationService {
    String getNicknameByLoginAndPassword(String login, String password) throws SQLException;

    boolean register(String login, String password, String nickname) throws SQLException;

    boolean isLoginAlreadyExist(String login) throws SQLException;

    boolean isNicknameAlreadyExist(String nickname) throws SQLException;

    Role getRoleByLoginAndPassword(String login, String password) throws SQLException;
}
