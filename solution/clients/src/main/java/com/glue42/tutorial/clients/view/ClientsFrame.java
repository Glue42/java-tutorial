package com.glue42.tutorial.clients.view;

import com.glue42.tutorial.clients.model.Client;
import com.glue42.tutorial.clients.util.RestClient;
import com.tick42.glue.Glue;
import com.tick42.glue.desktop.windows.Window;
import com.tick42.glue.desktop.windows.WindowHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static java.awt.Color.WHITE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingConstants.CENTER;

public class ClientsFrame extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientsFrame.class);

    private final AtomicReference<Window> window = new AtomicReference<>(null);
    private final ClientsTable clientsTable;

    public ClientsFrame() throws HeadlessException {
        clientsTable = new ClientsTable();
        setUpFrame();
        fetchClients();
    }

    public void registerWithGlue(Glue glue) {
        WindowHandle<ClientsFrame> handle = glue.windows().getWindowHandle(this);
        glue.windows()
                .register(handle)
                .whenComplete((win, exception) -> {
                    if (exception != null || win == null) {
                        LOGGER.error("unable to register JFrame", exception);
                    } else {
                        window.set(win);
                    }
                });
    }

    public CompletionStage<Void> stop() {
        List<CompletableFuture<Void>> closeFutures = new ArrayList<>();
//        if(context != null)
//        {
//            closeFutures.add(context.closeAsync().toCompletableFuture());
//        }
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
        add(clientsTable, BorderLayout.CENTER);

        getContentPane().setBackground(WHITE);
        setSize(800, 450);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JLabel buildHeader() {
        JLabel header = new JLabel("Clients", CENTER);
        header.setBorder(createEmptyBorder(30, 0, 30, 0));
        return header;
    }

    private void fetchClients() {
        RestClient restClient = new RestClient();
        try {
            List<Client> clients = restClient.fetchClients();
            clientsTable.setClients(clients);
        } catch (IOException exception) {
            LOGGER.error("unable to fetch clients", exception);
        }
    }
}
