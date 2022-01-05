package com.glue42.tutorial.portfolio;

import com.tick42.glue.Glue;
import com.tick42.glue.core.contexts.Context;
import com.tick42.glue.core.interop.MethodDefinition;
import com.tick42.glue.desktop.windows.Window;
import com.tick42.glue.desktop.windows.WindowHandle;
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
import static java.util.stream.Collectors.toList;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingConstants.CENTER;

public class PortfolioFrame extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioFrame.class);

    private final AtomicReference<Window> window = new AtomicReference<>(null);
    private final PortfolioTable portfolioTable;

    private Context<Map<String, Object>> context;

    public PortfolioFrame() {
        portfolioTable = new PortfolioTable();
        setUpFrame();
    }

    public void registerWithGlue(Glue glue) {
        // 1. Register JFrame in Glue42
        WindowHandle<PortfolioFrame> handle = glue.windows().getWindowHandle(this);
        glue.windows()
                .register(handle)
                .whenComplete((window, exception) -> {
                    if (exception != null || window == null) {
                        LOGGER.error("unable to register JFrame", exception);
                    } else {
                        this.window.set(window);
                    }
                });

        // 2. Subscribe to Shared Context changes here and call updatePortfolio()
        // subscribeToSharedContextChanges(glue);

        // 3.1. Register "showPortfolio" interop method here and call updatePortfolio()
        registerInteropMethod(glue);
    }

    public CompletionStage<Void> stop() {
        List<CompletableFuture<Void>> closeFutures = new ArrayList<>();
        if (context != null) {
            closeFutures.add(context.closeAsync().toCompletableFuture());
        }
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
        add(portfolioTable, BorderLayout.CENTER);

        getContentPane().setBackground(WHITE);
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JLabel buildHeader() {
        JLabel header = new JLabel("Portfolio", CENTER);
        header.setBorder(createEmptyBorder(30, 0, 30, 0));
        return header;
    }

    private void subscribeToSharedContextChanges(Glue glue) {
        glue.contexts()
                .subscribe("selectedClient")
                .thenAccept(context -> {
                    this.context = context;
                    updatePortfolio(context.getData());
                    context.data(this::updatePortfolio);
                });
    }

    private void registerInteropMethod(Glue glue) {
        glue.interop()
                .<Map<String, Object>, Map<String, Object>>register(
                        MethodDefinition.builder("showPortfolio").withAccepts("String clientName, Composite: {String symbol, String description, Double bid, Double ask}[] portfolio").build(),
                        (arg, caller) -> {
                            updatePortfolio(arg);
                            return emptyMap();
                        });
    }

    private void updatePortfolio(Map<String, Object> arg) {
        portfolioTable.setPortfolio(mapToPortfolioEntries(arg));
    }

    private List<PortfolioEntry> mapToPortfolioEntries(Map<String, Object> arg) {
        return ((List<Map<String, Object>>) arg.get("portfolio"))
                .stream()
                .map(entry -> new PortfolioEntry((String) entry.get("symbol"), (String) entry.get("description"), entry.get("bid").toString(), entry.get("ask").toString()))
                .collect(toList());

    }
}
