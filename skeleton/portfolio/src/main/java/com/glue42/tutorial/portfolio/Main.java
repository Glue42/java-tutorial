package com.glue42.tutorial.portfolio;

import com.tick42.glue.Glue;

import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        Glue glue = Glue.builder()
                .withApplicationName("Tutorial Portfolio")
                .build();

        SwingUtilities.invokeLater(() -> {
            PortfolioFrame frame = new PortfolioFrame();
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
