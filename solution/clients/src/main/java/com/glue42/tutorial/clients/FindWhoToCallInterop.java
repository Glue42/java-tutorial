package com.glue42.tutorial.clients;

import com.glue42.tutorial.clients.model.Contact;
import com.glue42.tutorial.clients.util.RestClient;
import com.tick42.glue.Glue;
import com.tick42.glue.core.interop.Instance;
import com.tick42.glue.core.interop.MethodDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;

public class FindWhoToCallInterop {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindWhoToCallInterop.class);

    public static void register(Glue glue) {
        glue.interop()
                .register(
                        MethodDefinition.builder("findWhoToCall").withReturns("Composite: {String clientName, String phone}[] contacts").build(),
                        FindWhoToCallInterop::interopMethod
                );
    }

    private static Map<String, Object> interopMethod(Map<String, Object> args, Instance caller) {
        return singletonMap("contacts", getContacts());
    }

    private static List<Contact> getContacts() {
        try {
            return new RestClient().fetchClients()
                    .stream()
                    .map(client -> new Contact(client.getFullName(), client.getPhoneNumber()))
                    .collect(toList());
        } catch (IOException exception) {
            LOGGER.error("unable to fetch clients", exception);
            return emptyList();
        }
    }
}
