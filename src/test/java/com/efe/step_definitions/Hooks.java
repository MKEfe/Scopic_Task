package com.efe.step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before("@db")
    public void dbStart(){
        DBUtils.createConnection();
    }

    @After("@db")
    public void dbEnd(){
        DBUtils.destroy();
    }
}
