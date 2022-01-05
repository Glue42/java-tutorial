package com.glue42.tutorial.clients;

import com.tick42.glue.Glue;
import com.tick42.glue.core.interop.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class ShowContactsInterop {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowContactsInterop.class);
    private static Glue glue;

    public static void register(Glue glue) {
        ShowContactsInterop.glue = glue;

        // 4.2. Register Interop method here
        glue.interop()
                .register("showContacts", ShowContactsInterop::showContacts);
    }

    private static Map<String, Object> showContacts(Map<String, Object> args, Instance caller) {
        // 5. Start the contacts app
        glue.appManager()
                .start("contacts")
                .whenComplete((instance, error) -> {
                    if (error != null) {
                        LOGGER.error("unable to start contacts application", error);
                    }
                });

        return Collections.emptyMap();
    }
}
