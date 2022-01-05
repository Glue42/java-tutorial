package com.glue42.tutorial.notifications;

import com.tick42.glue.Glue;
import com.tick42.glue.desktop.windows.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static java.awt.Color.WHITE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingConstants.CENTER;

public class NotificationsFrame extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationsFrame.class);

    private final AtomicReference<Window> window = new AtomicReference<>(null);

    private Glue glue;
    private JTextField titleTextField;

    public NotificationsFrame() {
        setUpFrame();
    }

    public void registerWithGlue(Glue glue) {
        // 1. Register JFrame in Glue42
        // and assign received Window instance to this.window:
        // this.window.set(window);

        this.glue = glue;
    }

    public CompletionStage<Void> stop() {
        List<CompletableFuture<Void>> closeFutures = new ArrayList<>();
        if (window.get() != null) {
            closeFutures.add(window.get().closeAsync().toCompletableFuture());
        } else {
            closeFutures.add(CompletableFuture.completedFuture(null));
        }
        return CompletableFuture.allOf(closeFutures.toArray(new CompletableFuture[0]));
    }

    private void setUpFrame() {
        setLayout(new BorderLayout());
        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        getContentPane().setBackground(WHITE);
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JLabel buildHeader() {
        JLabel header = new JLabel("Manually raise notifications", CENTER);
        header.setBorder(createEmptyBorder(30, 0, 30, 0));
        return header;
    }

    private Component buildContent() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);

        titleTextField = new JTextField(20);
        panel.add(titleTextField);

        JButton button = new JButton("Raise");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                raiseNotification();
            }
        });
        panel.add(button);

        return panel;
    }

    private void raiseNotification() {
        String title = titleTextField.getText();

        // 4.1 Raise a notification here
        // 4.2 The notification should invoke the method registered in the Clients application which opens the Contact Info
    }
}
