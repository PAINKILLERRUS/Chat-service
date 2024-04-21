package org.example.server;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAuthenticationService implements AuthenticationService {
    private class User {
        String login;
        String password;
        String nickname;
        Role role;

        public void setRoles(Role role) {
            this.role = role;
        }

        public Role getRoles() {
            return role;
        }

        public User(String login, String password, String nickname, Role role) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
            this.role = role;
        }
    }

    private List<User> users;

    public InMemoryAuthenticationService() {
        this.users = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            this.users.add(new User("login" + i, "pass" + i, "nick" + i, Role.USER));
        }
        users.get(0).setRoles(Role.ADMIN);
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.nickname;
            }
        }
        return null;
    }

    @Override
    public Role getRoleByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.role;
            }
        }
        return null;
    }

    @Override
    public boolean register(String login, String password, String nickname) {
        if (isLoginAlreadyExist(login)) {
            return false;
        }
        if (isNicknameAlreadyExist(nickname)) {
            return false;
        }
        users.add(new User(login, password, nickname, Role.USER));
        return true;
    }

    @Override
    public boolean isLoginAlreadyExist(String login) {
        for (User u : users) {
            if (u.login.equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNicknameAlreadyExist(String nickname) {
        for (User u : users) {
            if (u.nickname.equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}
