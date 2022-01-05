package com.glue42.tutorial.contacts.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.util.List;
import java.util.Map;

import static java.awt.Color.WHITE;
import static javax.swing.BorderFactory.createEmptyBorder;

public class ContactsTable extends JScrollPane {

    private static final String[] COLUMN_NAMES = { "Full Name", "Contact Number", "Action" };

    private final JTable table;

    public ContactsTable() {
        table = new JTable(new String[][] {}, COLUMN_NAMES);
        table.setDefaultEditor(Object.class, null); // make the table non-editable

        setBorder(createEmptyBorder(0, 30, 0, 30));
        setViewportView(table);

        getViewport().setBackground(WHITE);
        setBackground(WHITE);
    }

    public void setContacts(List<Map<String, String>> contacts) {
        String[][] rows = contacts.stream()
                .map(contact -> new String[]{contact.get("clientName"), contact.get("phone"), ""})
                .toArray(contactsCount -> new String[contactsCount][2]);

        DefaultTableModel tableModel = new DefaultTableModel(rows, COLUMN_NAMES);
        table.setModel(tableModel);
        table.getColumn("Action").setCellRenderer(new ActionCellRenderer());
    }

    private static class ActionCellRenderer extends JPanel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return new JButton("Call");
        }
    }
}
