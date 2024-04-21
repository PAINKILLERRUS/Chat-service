package org.example.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;
    private Role role;

    public Role getRoles() {
        return role;
    }

    public String getNickname() {
        return nickname;
    }

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                System.out.println("Подключился новый клиент");
                if (tryToAuthentication()) {
                    communicate();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean tryToAuthentication() throws IOException {
        while (true) {
            String msg = in.readUTF();
            if (msg.startsWith("/auth ")) {
                String[] tokens = msg.split(" ");
                if (tokens.length != 3) {
                    sendMessage("Некорректный формат запроса.");
                    continue;
                }
                String login = tokens[1];
                String password = tokens[2];
                String nickname = server.getAuthenticationService().getNicknameByLoginAndPassword(login, password);
                Role role = server.getAuthenticationService().getRoleByLoginAndPassword(login, password);
                if (nickname == null) {
                    sendMessage("Неверный логин/пароль");
                    continue;
                }
                if (server.nickIsBusy(nickname)) {
                    sendMessage("Учетная запись занята. Попробуйте зайти позднее.");
                    continue;
                }
                this.nickname = nickname;
                this.role = role;
                server.subscribe(this);
                sendMessage(nickname + " , добро пожаловать в чат!");
                return true;
            } else if (msg.startsWith("/register ")) {
                String[] tokens = msg.split(" ");
                if (tokens.length != 4) {
                    sendMessage("Некорректный формат запроса.");
                    continue;
                }
                String login = tokens[1];
                String password = tokens[2];
                String nickname = tokens[3];
                if (server.getAuthenticationService().isLoginAlreadyExist(login)) {
                    sendMessage("Указанный логин уже занят");
                    continue;
                }
                if (server.getAuthenticationService().isNicknameAlreadyExist(nickname)) {
                    sendMessage("Указынный никнейм занят");
                    continue;
                }
                if (!server.getAuthenticationService().register(login, password, nickname)) {
                    sendMessage("Не удалось пройти регистрацию");
                    continue;
                }
                this.nickname = nickname;
                server.subscribe(this);
                sendMessage("Вы успешно зарегистрировались " + nickname + " , добро пожаловать в чат!");
                return true;
            } else if (msg.equals("/exit")) {
                return false;
            } else {
                sendMessage("Вам необходимо авторизоваться");
            }
        }
    }

    private void communicate() throws IOException {
        while (true) {
            String msg = in.readUTF();
            if (msg.startsWith("/")) {
                if (msg.startsWith("/exit")) {
                    break;
                }
                if (msg.startsWith("/w")) {
                    String[] tokens = msg.split(" ");
                    String nick = tokens[1];
                    String message = msg.substring(4 + nick.length());
                    server.privateMessage(this, nick, message);
                }
                if (msg.startsWith("/kick") && role == Role.ADMIN) {
                    String[] tokens = msg.split(" ");
                    if (tokens.length != 2) {
                        sendMessage("Некорректный ввод команды.");
                        continue;
                    }
                    String nick = tokens[1];
                    server.kick(this, nick);
                }
                continue;
            }
            server.broadcastMessage(nickname + ": " + msg);
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
