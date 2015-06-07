package com.songli.codelinecount.state;

public class ReadStateMachine {

    private ReadState readState;
    
    
    
    public ReadStateMachine(String fileType) {
        // TODO Auto-generated constructor stub
    }

    public void setState(ReadState state) {
        this.readState = state;
    }
    
    
    
    
}
