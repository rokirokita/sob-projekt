package com.company;

import com.company.customs.ServerListViewCell;
import com.company.server.Server;
import com.company.server.Servers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.SocketException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    private TextField votingValueField;

    @FXML
    private ListView<Server> serverList;

    ObservableList<Server> serversData;

    private Servers servers;

    private List<Server> connections = new ArrayList<Server>();

    public Controller() {
        try {
            this.servers = new Servers(
                    new Server(4440, connections, true),
                    new Server(4441, connections),
                    new Server(4442, connections),
                    new Server(4443, connections),
                    new Server(4444, connections),
                    new Server(4445, connections),
                    new Server(4446, connections),
                    new Server(4447, connections)
            );
        } catch (SocketException e) {
            e.printStackTrace();
        }
        serversData = FXCollections.observableArrayList(this.connections);
    }

    public void sendToVoting(MouseEvent mouseEvent) {
        Logger.getLogger("Controller").info(votingValueField.getText());
        servers.sendPrepareMessage(Long.valueOf(votingValueField.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverList.setItems(serversData);
        serverList.setCellFactory(serverListView -> new ServerListViewCell());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                serverList.refresh();
            }
        }, 2000, 2000);
    }
}
