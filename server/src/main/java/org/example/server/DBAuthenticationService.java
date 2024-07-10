package org.example.server;

import java.sql.SQLException;

public class DBAuthenticationService implements AuthenticationService {

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) throws SQLException {
        User user = CRUDDatabaseUtils.findUserByLoginAndPassword(login, password);
        assert user != null;
        return user.nickname;
    }

    @Override
    public boolean register(String login, String password, String nickname) throws SQLException {
        if (isLoginAlreadyExist(login) || isNicknameAlreadyExist(nickname)) {
            return false;
        }
        CRUDDatabaseUtils.registerUser(login, password, nickname);
        return true;
    }

    @Override
    public boolean isLoginAlreadyExist(String login) throws SQLException {
        User user = CRUDDatabaseUtils.checkLogin(login);
        return user != null;
    }

    @Override
    public boolean isNicknameAlreadyExist(String nickname) throws SQLException {
        User user = CRUDDatabaseUtils.checkNickName(nickname);
        return user != null;
    }

    @Override
    public Role getRoleByLoginAndPassword(String login, String password) throws SQLException {
        User user = CRUDDatabaseUtils.findRoleByLoginAndPassword(login, password);
        assert user != null;
        return user.role;
    }
}
