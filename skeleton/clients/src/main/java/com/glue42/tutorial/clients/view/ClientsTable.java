package com.glue42.tutorial.clients.view;

import com.glue42.tutorial.clients.model.Client;
import com.tick42.glue.Glue;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static java.awt.Color.WHITE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class ClientsTable extends JScrollPane {

    private static final String[] COLUMN_NAMES = { "Full Name", "PID", "GID", "Manager" };

    private final JTable table;

    private List<Client> clients;
    private Glue glue;

    public ClientsTable() {
        table = new JTable(new String[][] {}, COLUMN_NAMES);
        table.setDefaultEditor(Object.class, null); // make the table non-editable
        table.setSelectionMode(SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::onClientSelected);

        setBorder(createEmptyBorder(0, 30, 0, 30));
        setViewportView(table);

        getViewport().setBackground(WHITE);
        setBackground(WHITE);
    }

    public void setGlue(Glue glue) {
        this.glue = glue;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        String[][] rows = clients.stream()
                .map(this::mapToTableRow)
                .toArray(clientsCount -> new String[clientsCount][COLUMN_NAMES.length]);

        table.setModel(new DefaultTableModel(rows, COLUMN_NAMES));
    }

    private String[] mapToTableRow(Client client) {
        return new String[]{client.getFullName(), client.getPid(), client.getGid(), client.getManager()};
    }

    private void onClientSelected(ListSelectionEvent event) {
        int clientIndex = table.getSelectedRow();
        if (event.getValueIsAdjusting() || clientIndex < -1) {
            return;
        }

        Client client = clients.get(clientIndex);

        // 2. Context update should be invoked here

        // 3.2 Call "showPortfolio" Interop method here
    }
}
