package com.company.customs;

import com.company.server.Server;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.logging.Logger;

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
    private CheckBox isShutdown;

    @FXML
    private CheckBox isCrazyAcceptor;

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
            if(server.isLeader()) {
                isShutdown.setVisible(false);
            }else {
                isShutdown.setSelected(server.isShutdown());
                isShutdown.setOnMouseClicked(event -> {
                    server.setShutdown(isShutdown.isSelected());
                });
            }
            if(server.isLeader()) {
                isCrazyAcceptor.setVisible(false);
            }else {
                isCrazyAcceptor.setSelected(server.isCrazyAcceptor());
                isCrazyAcceptor.setOnMouseClicked(event -> {
                    server.setCrazyAcceptor(isCrazyAcceptor.isSelected());
                });
            }

            setText(null);
            setGraphic(gridPane);
        }
    }
}
