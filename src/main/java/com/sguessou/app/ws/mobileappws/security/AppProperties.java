package com.sguessou.app.ws.mobileappws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    private Environment env;

    @Autowired
    public AppProperties(Environment env) {
        this.env = env;
    }

    public String getTokenString() {
        return env.getProperty("tokenSecret");
    }

}
