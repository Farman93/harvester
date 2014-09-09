package wrap;
//Сообщение, содержащее помимо кода и текста сообщения массив с элементами расписания
import java.util.ArrayList;

import dbElem.ScheduleElement;

public class ScheduleMessage extends ServiceMessage {
	public ScheduleElement[] scheduleArray;
	
	public ScheduleMessage() {
		super();
		scheduleArray = null;
	}
	public ScheduleMessage(int code, String text, ScheduleElement[] sch) {
		super(code, text);
		scheduleArray = sch;
	}
	
	
	public ScheduleElement[] getScheduleArray() {
		return scheduleArray;
	}
	public void setScheduleArray(ScheduleElement[] scheduleArray) {
		this.scheduleArray = scheduleArray;
	}

	public boolean getScheduleArrayFromList (ArrayList<ScheduleElement> sch) {
		if ( sch == null )
			return false;
		int size = sch.size();
		this.scheduleArray = new ScheduleElement[size];
		for (int i = 0; i < size; ++i) {
			this.scheduleArray[i] = sch.get(i);
		}
		return true;
	}

}
