package com.glue42.tutorial.notifications;

import com.tick42.glue.Glue;

import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        Glue glue = Glue.builder()
                .withApplicationName("Tutorial Notifications")
                .build();

        SwingUtilities.invokeLater(() -> {
            NotificationsFrame frame = new NotificationsFrame();
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
