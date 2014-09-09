package timePackage;

import java.util.Date;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import dbInteractions.SQLDatetime;


public class Alarm {
    static Timer _timer;
    static int cur_sid = -1;
    static int cur_qid = -1; 
    public Alarm() {
    }
    public void run(SQLDatetime datetime, int sid, int qid) {
    	if (_timer != null)
    		_timer.cancel();
    	int year = -1;
    	int month = -1;
    	int day = -1;
    	int hour = -1;
    	int minute = -1;
    	int second = -1;
    	if (cur_sid == sid) {
    		System.out.println("Данный пункт расписания уже назначен на выполнение.");	
    	}
    	cur_sid = sid;
    	cur_qid = qid;
    	
    	day = datetime.d;
    	month = datetime.mo - 1;
    	year = datetime.y;
    	
    	hour = datetime.h;
    	minute = datetime.mi;
    	second = datetime.s;
    	
    	if (day == -1) {
    		System.out.println("Something is wrong with the time. It has default -1 value.");	
    		return;
    	}
    	
    	if (month == -1) {
    		System.out.println("Something is wrong with the time. It has default -1 value");	
    		return;
    	}
    	
    	if (year == -1) {
    		System.out.println("Something wrong with the time. It has default -1 value");	
    		return;
    	}
    	
    	if (hour == -1) {
    		System.out.println("Something wrong with the time. It has default -1 value");	
    		return;
    	}
    	
    	if (minute == -1) {
    		System.out.println("Something wrong with the time. It has default -1 value");	
    		return;
    	}
    	
    	if (second == -1) {
    		System.out.println("Something wrong with the time. It has default -1 value");	
    		return;
    	}
    	
        Calendar calendar = Calendar.getInstance();
        
        
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        
        
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        Date alarmTime = calendar.getTime();

        _timer = new Timer();
        AlarmTask at = new AlarmTask();
        at.na = this;
        System.out.println("------------------------------------");
        System.out.println("Next harvesting is planned on " + day + "." + (month+1) + "." + year + " " + hour + ":" + minute + ":" + second);
        _timer.schedule(new AlarmTask(), alarmTime);
    }

    class AlarmTask extends TimerTask {
        /**
         * Called on a background thread by Timer
         */
    	public Alarm na = new Alarm();
        public void run() {
            System.out.println("The delay is "+ (System.currentTimeMillis() - scheduledExecutionTime()));
        	System.out.println("It's time for harvesting!");
        	Harvester nextHarv = new Harvester(na, cur_sid, cur_qid);
        	new Thread(nextHarv).start();
        	System.out.println("Debug message. Alarm");
            _timer.cancel();
            AlarmStarter.updateTask();
        }
    }
}
