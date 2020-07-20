package main;

import io.database.Auditor;
import datatypes.printlayout.Layout;
import view.*;
import datatypes.*;
import java.sql.*;

public enum Erasmus implements Displayable {
    INSTANCE();
    
    private Money portfolioValue;
    private User currentUser;
    private Time timescale;
    private Money portfolioChange;
    private int numTransactions;
    private String lastCommand;
    private String messages;
    private Boolean programIsActive;
    private EnvironmentVariables environmentVariables;
    private Credentials credentials;
    
    //TODO: Finish implementing constructor, which populates all program fields.
    Erasmus() {
        
        //TODO: Load environment variables from external file.
        environmentVariables = EnvironmentVariables.INSTANCE;
        
        
        
        //TODO: Load credentials.
        credentials.populate();
        
    }
    
    //TODO: finish implementing generateLayout.
    @Override
    public Layout generateLayout() {
        
        Layout erasmusLayout = new Layout();
        return erasmusLayout;

    }
    
    
//<editor-fold defaultstate="collapsed" desc="Setters and getters">
    public synchronized void setProgramIsActive(Boolean programActive) {
        this.programIsActive = programActive;
    }
    
    public synchronized void setPortfolioValue(Money portfolioValue) {
        this.portfolioValue = portfolioValue;
    }
    
    public synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    public synchronized void setTimescale(Time timescale) {
        this.timescale = timescale;
    }
    
    public synchronized void setPortfolioChange(Money portfolioChange) {
        this.portfolioChange = portfolioChange;
    }
    
    public synchronized void setNumTransactions(int numTransactions) {
        this.numTransactions = numTransactions;
    }
    
    public synchronized void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }
    
    public synchronized void setMessages(String messages) {
        this.messages = messages;
    }
    
    public synchronized Credentials getCredentials() {
        return credentials;
    }
    
    public synchronized void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
//</editor-fold>
    
    public static void main(String[] args) {
        
        Erasmus instance = Erasmus.INSTANCE;
        
        /*
        * TODO: Implement a way to check the database for consistency.
        * This must check the local database against any online data. 
        * It should also check for corruption/improper exits. 
        */
        
        //TODO: Complete the auditor class.
        Auditor auditor = new Auditor();
        auditor.audit();

        // TODO: Implement a way to find and then start all background processes.
        // findBackgroundProcesses();
        // startBackgroundProcesses();
        
        // TODO: Implement the main thread.
        
        instance.setProgramIsActive(false);
        while(instance.programIsActive) {
             
            // TODO: Create a method to print the dashboard. 
            // prettyPrinter(instance); 
            
            // TODO: Create the main loop which asks the user for a command.
            // acceptCommands();
        }
        
        // TODO: Create a method to cleanly shut down all processes. 
        // gracefulShutdown();
        
    }
}
