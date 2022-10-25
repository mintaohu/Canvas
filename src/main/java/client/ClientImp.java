package client;

import remote.ClientRemote;
import remote.ServerRemote;
import server.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImp extends UnicastRemoteObject implements ClientRemote, Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private MainFrame clientGUI;
    private ServerRemote serverRemote;
    private String service="canvas";
    private String clientService="";
    private byte[] data;


    protected ClientImp(String username) throws RemoteException {
        super();
        this.username = username;
    }

    @Override
    public void userPool(String[] userList) throws RemoteException {
        //clientGUI.getJlist().setListData(userList)
    }

    @Override
    public void download(byte[] data) throws RemoteException {
        try{
            ByteArrayInputStream input = new ByteArrayInputStream(data);
            BufferedImage canvas = ImageIO.read(input);
            this.clientGUI.getCanvas().setImg(canvas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean qualify(String username) throws RemoteException {
        String[] userList = this.serverRemote.displayUsers();

        for(int i = 0; i < userList.length; i++){
            if (username==userList[i]){
                return false;
            }
        }
        return true;
    }


    public void register(){
        try {
            System.out.println("REGISTER CLIENT");
            Naming.rebind("rmi://localhost/"+ clientService, this);
            this.serverRemote = (ServerRemote) Naming.lookup("rmi://localhost/" + service);

            this.clientGUI.startGUI();
            this.clientGUI.setServerRemote(this.serverRemote);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e){
            throw new RuntimeException(e);
        } catch (NotBoundException e){
            throw new RuntimeException(e);
        }
    }

    public void start(){

    }




}
