package edu.nefu.main;

import java.util.TimerTask;

public class ExitTimerTask extends TimerTask{

	public void run() {  
        UserHelper.setIsExit(false);  
    }  

}
