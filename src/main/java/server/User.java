package server;

import remote.ClientRemote;
import remote.ServerRemote;

public class User {
    private String username;
    private ServerRemote serverRemote;
    private ClientRemote clientRemote;
    private boolean isManager = false;

    public User(String username, ClientRemote clientRemote){
        this.username = username;
        this.clientRemote = clientRemote;
    }

    public boolean isManager() {
        return isManager;
    }

    public ClientRemote getClientRemote() {
        return clientRemote;
    }

    public String getUsername() {
        return username;
    }

    public void setClientRemote(ClientRemote clientRemote) {
        this.clientRemote = clientRemote;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
