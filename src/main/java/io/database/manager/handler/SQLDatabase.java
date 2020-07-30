package io.database.manager.handler;

import datatypes.credential.Credentials;

public class SQLDatabase {
    private final Credentials credentials;
    private final String URL;
    private final String Driver;

    public SQLDatabase(Credentials credentials, String URL, String Driver) {
        this.credentials = credentials;
        this.URL = URL;
        this.Driver = Driver;
    }

    public SQLDatabase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getURL() {
        return URL;
    }

    public String getDriver() {
        return Driver;
    }
    
}
