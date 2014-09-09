//Класс, соответствующий сущности Schedule в БД. Отличается от нее неполным количеством полей (столбцов)

package ru.umeta.harvesting.base;

import java.io.PrintStream;

public class ScheduleElement {
	public String datetime;
	public String result; //идентификатор строки в таблице статуса выполнения в БД, соответствующий сообщению
	public String message; //сообщение о статусе выполнения элемента расписания
	public String attempt;
	public ScheduleElement() {
		datetime = "default";
		result = "default";
		message = "default";
		attempt = "default";
	}
	public ScheduleElement(String dt, String res, String att, String msg) {
		datetime = dt;
		result = res;
		message = msg;
		attempt = att;
	}
	
	public void println() {
		PrintStream out_ = System.out;
		out_.print(datetime + '\t');
		out_.print(result + '\t');
		out_.print(message + '\t');
		out_.println(attempt);
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAttempt() {
		return attempt;
	}
	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}
}
