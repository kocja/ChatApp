package ch.admin.seco.chatserver;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler  {

    private final Timer timer;
    private final int interval = 60;


    public Scheduler(Timer timer) {
        this.timer = timer;
    }




}
