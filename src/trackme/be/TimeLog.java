/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.be;

import java.time.LocalDateTime;

/**
 *
 * @author WøbbePC
 */
public class TimeLog {

    private TimeType type;
    private LocalDateTime time;
    

    public TimeLog(TimeType type, LocalDateTime time) {
        this.type = type;
        this.time = time;
    }


    public TimeType getType() {
        return type;
    }

    public void setType(TimeType type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    public enum TimeType {
        PLAY, STOP;
    }
    
}
