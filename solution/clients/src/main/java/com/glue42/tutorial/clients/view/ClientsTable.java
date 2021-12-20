package com.glue42.tutorial.clients.view;

import com.glue42.tutorial.clients.model.Client;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static java.awt.Color.WHITE;
import static javax.swing.BorderFactory.createEmptyBorder;

public class ClientsTable extends JScrollPane {

    private static final String[] COLUMN_NAMES = { "Full Name", "PID", "GID", "Manager" };

    private final JTable table;

    public ClientsTable() {
        table = new JTable(new String[][] {}, COLUMN_NAMES);
        table.setDefaultEditor(Object.class, null); // make the table non-editable
        setBorder(createEmptyBorder(0, 30, 0, 30));
        setViewportView(table);

        getViewport().setBackground(WHITE);
        setBackground(WHITE);
    }

    public void setClients(List<Client> clients) {
        String[][] rows = clients.stream()
                .map(this::mapToTableRow)
                .toArray(clientsCount -> new String[clientsCount][COLUMN_NAMES.length]);

        table.setModel(new DefaultTableModel(rows, COLUMN_NAMES));
    }

    private String[] mapToTableRow(Client client) {
        return new String[]{client.getFullName(), client.getPid(), client.getGid(), client.getManager()};
    }
}
