package com.glue42.tutorial.portfolio;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static java.awt.Color.WHITE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class PortfolioTable extends JScrollPane {

    private static final String[] COLUMN_NAMES = { "Symbol", "Description", "Bid", "Ask" };

    private final JTable table;

    public PortfolioTable() {
        table = new JTable(new String[][] {}, COLUMN_NAMES);
        table.setDefaultEditor(Object.class, null); // make the table non-editable
        table.setSelectionMode(SINGLE_SELECTION);

        setBorder(createEmptyBorder(0, 30, 0, 30));
        setViewportView(table);

        getViewport().setBackground(WHITE);
        setBackground(WHITE);
    }

    public void setPortfolio(List<PortfolioEntry> portfolio) {
        String[][] rows = portfolio.stream()
                .map(entry -> new String[] { entry.getSymbol(), entry.getDescription(), entry.getBid(), entry.getAsk() })
                .toArray(portfolioCount -> new String[portfolioCount][COLUMN_NAMES.length]);

        table.setModel(new DefaultTableModel(rows, COLUMN_NAMES));
    }
}
