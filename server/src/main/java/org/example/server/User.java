package org.example.server;

public class User {
    int id;
    String login;
    String password;
    String nickname;
    Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    public Role getRoles() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public User(int id,String login, String password, String nickname, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + id + '\'' +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role=" + role + '}';
    }
}
