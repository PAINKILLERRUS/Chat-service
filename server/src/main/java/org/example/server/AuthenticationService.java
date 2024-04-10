package org.example.server;

public interface AuthenticationService {
    String getNicknameByLoginAndPassword(String login, String password);
}
