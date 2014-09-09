package ru.umeta.harvesting.geonetwork;

import java.io.Serializable;

import javax.activation.DataHandler;

public class HarvesterFileMessage implements Serializable {
	int code;
	String message;
	DataHandler fileDataHandler;
	HarvesterFileMessage() {
		code = -1;
		message = "default constructor was used";
	}
	HarvesterFileMessage(int code, String message, DataHandler fileDataHandler) {
		this.code = code;
		this.message = message;
		this.fileDataHandler = fileDataHandler;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DataHandler getFileDataHandler() {
		return fileDataHandler;
	}
	public void setFileDataHandler(DataHandler fileDataHandler) {
		this.fileDataHandler = fileDataHandler;
	}
}
