package com.anton.railway.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@Configuration
public class HttpSessionConfig {
    @Bean
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(HttpSessionBindingEvent se) {

            }

            @Override
            public void attributeRemoved(HttpSessionBindingEvent se) {
                if (se.getName().equals("index")) {
                    HttpSession session = se.getSession();

                    session.removeAttribute("selectedWagonClass");
                    session.removeAttribute("selectedWagon");
                    session.removeAttribute("wagons");
                    session.removeAttribute("seats");
                }
            }

            @Override
            public void attributeReplaced(HttpSessionBindingEvent se) {
                if (se.getName().equals("trip")) {
                    HttpSession session = se.getSession();

                    session.removeAttribute("selectedWagonClass");
                    session.removeAttribute("wagons");
                    session.removeAttribute("selectedWagon");
                    session.removeAttribute("seats");
                    session.removeAttribute("selectedSeat");
                    session.removeAttribute("ticket");
                }
            }
        };
    }
}
