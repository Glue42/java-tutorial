package com.glue42.tutorial.contacts.view;

import com.tick42.glue.Glue;
import com.tick42.glue.desktop.windows.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static java.awt.Color.WHITE;
import static java.util.Collections.emptyMap;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingConstants.CENTER;

public class ContactsFrame extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsFrame.class);

    private final AtomicReference<Window> window = new AtomicReference<>(null);
    private final ContactsTable contactsTable;

    public ContactsFrame() {
        contactsTable = new ContactsTable();
        setUpFrame();
    }

    public void registerWithGlue(Glue glue) {
        // 1. Register JFrame in Glue42
        // and assign received Window instance to this.window:
        // this.window.set(window);

        showContacts(glue);
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
        add(contactsTable, BorderLayout.CENTER);

        getContentPane().setBackground(WHITE);
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JLabel buildHeader() {
        JLabel header = new JLabel("Contacts", CENTER);
        header.setBorder(createEmptyBorder(30, 0, 30, 0));
        return header;
    }

    private void showContacts(Glue glue) {
        // 5. Call findWhoToCall Interop method and pass the result to contactsTable.setContacts()
        glue.interop().invoke("findWhoToCall", emptyMap())
                .thenAccept(result -> {
                    result.getReturned().ifPresent(map -> contactsTable.setContacts((List<Map<String, String>>) map.get("contacts")));
                });
    }
}
