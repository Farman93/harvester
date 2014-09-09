package timePackage;

import dbInteractions.DBCheckNextHarvest;
import dbInteractions.SQLDatetime;
import wrap.IntWrapper;

public class AlarmStarter {
	public static Alarm na;
	public static Alarm newTask() {
		SQLDatetime datetime = new SQLDatetime();
		IntWrapper sid = new IntWrapper();
		IntWrapper qid = new IntWrapper();
		sid.value = -1;
		qid.value = -1;
		if (!DBCheckNextHarvest.dbConnect(datetime, sid, qid)) {
			System.out.println("There is nothing to harvest");
			return null;
		}
		na = new Alarm();
		na.run(datetime, sid.value, qid.value);
		return na;
	}
	
	public static void updateTask() {
		System.out.println("Updating alarm");
		SQLDatetime datetime = new SQLDatetime();
		IntWrapper sid = new IntWrapper();
		IntWrapper qid = new IntWrapper();
		sid.value = -1;
		qid.value = -1;
		if (!DBCheckNextHarvest.dbConnect(datetime, sid, qid)) {
			System.out.println("There is nothing to harvest");
			return;
		}
		na.run(datetime, sid.value, qid.value);
	}
	
	public static void run() {
		if (na == null)
			newTask();
		else
			updateTask();
	}
	public static void main(String[] args) {
		if (na == null)
			newTask();
		else
			updateTask();
	}
}