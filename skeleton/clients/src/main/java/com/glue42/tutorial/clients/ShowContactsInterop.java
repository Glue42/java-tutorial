package com.glue42.tutorial.clients;

import com.tick42.glue.Glue;
import com.tick42.glue.core.interop.Instance;

import java.util.Collections;
import java.util.Map;

public class ShowContactsInterop {

    public static void register(Glue glue) {
        // 4.2. Register Interop method here
    }

    private static Map<String, Object> showContacts(Map<String, Object> args, Instance caller) {
        // 5. Start the contacts app

        return Collections.emptyMap();
    }
}
