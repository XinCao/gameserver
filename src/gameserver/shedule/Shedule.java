package gameserver.shedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author caoxin
 */
@EnableScheduling
public class Shedule {
    
    @Scheduled (cron = "")
    public void showInfo () {
        System.out.println("");
    }
}
