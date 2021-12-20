package com.glue42.tutorial.clients;

import com.glue42.tutorial.clients.view.ClientsFrame;
import com.tick42.glue.Glue;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        Glue glue = Glue.builder().build();
        SwingUtilities.invokeLater(() -> {
            ClientsFrame frame = new ClientsFrame();
            frame.registerWithGlue(glue);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(final WindowEvent e) {
                    frame.stop().thenRun(glue::closeAsync);
                }
            });
        });
    }

}
