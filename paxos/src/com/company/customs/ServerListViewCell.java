package com.company.customs;

import com.company.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ServerListViewCell extends ListCell<Server> {

    @FXML
    private Label label1;

    @FXML
    private Label label2;
    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private GridPane gridPane;


    FXMLLoader mLLoader;


    @Override
    protected void updateItem(Server server, boolean empty) {
        super.updateItem(server, empty);
        if(empty || server == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("ListCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            label1.setText(String.valueOf(server.getConnection().getPort()));
            label2.setText(String.valueOf(server.isLeader()));
            label3.setText(String.valueOf(server.getCurrentId()));
            label4.setText(String.valueOf(server.getCurrentValue()));

            setText(null);
            setGraphic(gridPane);
        }
    }
}
