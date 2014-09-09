package ru.umeta.harvesting.geonetwork;



import org.jdom.Element;
import ru.umeta.harvesting.base.Query;

public class HarvesterInfo extends Query {
	
	public String ID;
	public Searcher  SEARCHER;
	public int CURRENT_RES_NUM= 0, HANDLE_RES_NUM, TOTAL_RES_NUM = 0;
	
	private boolean run = false, end = false;
	
	public HarvesterInfo() {
		super();
		ID = null;
		SEARCHER = new Searcher();
	}
	
	public HarvesterInfo(Query qr)
	{
		SEARCHER = new Searcher();
		update(qr);
	}
	
	public void update(Query qr)
	{
		this.ID = qr.id;
		this.protocol_id = qr.protocol_id;
		this.endURL = qr.endURL;
		this.startURL = qr.startURL;
		this.reg = qr.reg;
		this.struct_loc = qr.struct_loc;
	}
	
	public void run()
	{
		run = true;
		end = false;
		CURRENT_RES_NUM = 0;
		HANDLE_RES_NUM = 0;
		TOTAL_RES_NUM = 0;

	}
	public boolean isRun()
	{
		return run;
	}
	
	public void end()
	{
		end = true;
		run = false;
	}
	public boolean isEnd()
	{
		return end;
	}

	public class Searcher
	{
		public String title="";
		public String keywords="";
		public String freetext="";
		public String abstract_="";
		
		public Searcher() {
			
		}

		
		public Element createRequest()
		{
			Element req = new Element("request");
			
			add(req, "any",      freetext);
			add(req, "title",    title);
			add(req, "abstract", abstract_);
			add(req, "themekey", keywords);
			
			
			return req;
		}

		private void add(Element req, String name, String value)
		{
			if (value != null && value.length() != 0)
				req.addContent(new Element(name).setText(value));
		}		

	}
}
