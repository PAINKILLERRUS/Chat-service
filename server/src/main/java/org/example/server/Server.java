package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private AuthenticationService authenticationService;

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.authenticationService = new DBAuthenticationService();
            System.out.println("Сервис аутентификации запущен." + authenticationService.getClass().getSimpleName());
            System.out.printf("Сервер запущен на порту: %d, ожидаем подключения клиентов\n", port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(this, socket);
                } catch (Exception e) {
                    System.out.println("Возникла ошибка при обработке подключившегося клиента");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        broadcastMessage("К чату присоединился " + clientHandler.getNickname());
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage("Из чата вышел " + clientHandler.getNickname());
    }

    public synchronized void kick(ClientHandler clientHandler, String nick) {
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(nick)) {
                c.sendMessage("Администратор: " + clientHandler.getNickname() + " удалил из чата клиента " + nick);
                broadcastMessage("Из чата удален: " + c.getNickname());
                c.disconnect();
                return;
            }
        }
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMessage(message);
        }
    }

    public synchronized void privateMessage(ClientHandler clientHandler, String nickTO, String msg) {
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(nickTO)) {
                c.sendMessage("от " + clientHandler.getNickname() + " : " + msg);
                clientHandler.sendMessage("клиенту: " + nickTO + " : " + msg);
                return;
            }
        }
        clientHandler.sendMessage("Участника с ником: " + nickTO + " нет в чате.");
    }

    public synchronized boolean nickIsBusy(String nickname) {
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}