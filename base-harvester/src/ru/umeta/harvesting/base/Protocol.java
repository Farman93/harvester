package ru.umeta.harvesting.base;

import java.io.PrintStream;

public class Protocol {
	private int id;
	private String name = null;
	private String path = null;
	private String class_ = null;
	private String xml = null;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getClass_() {
		return class_;
	}
	public void setClass_(String class_) {
		this.class_ = class_;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public Protocol(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public void println() {
		PrintStream out_ = System.out;
		out_.print(String.valueOf(id) + '\t');
		out_.println(name);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
