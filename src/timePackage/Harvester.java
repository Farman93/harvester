package timePackage;

import common.IHarvester;
import common.HarvesterInfo;

import dbElem.Query;
import dbInteractions.*;
import wrap.IntWrapper;
import wrap.StringWrapper;


public class Harvester implements Runnable{
	int qid;// Query id
	int sid;// Schedule id
	IntWrapper pid;
	StringWrapper eURL;
	StringWrapper sURL;
	StringWrapper sloc; //structure for the protocol
	Alarm na = new Alarm();
	public Harvester() {
		qid = -1;
		sid = -1;
		pid = new IntWrapper();
		pid.value = -1;
		eURL = new StringWrapper();
		sURL = new StringWrapper();
		sloc = new StringWrapper();
		eURL.value = "default string";
		sURL.value = "default string";
		sloc.value = "default string";
	}
	
	public Harvester(Alarm na, int s, int q){
		qid = q;
		sid = s;
		pid = new IntWrapper();
		pid.value = -1;
		eURL = new StringWrapper();
		sURL = new StringWrapper();
		sloc = new StringWrapper();
		eURL.value = "default string";
		sURL.value = "default string";
		sloc.value = "default string";
		this.na = na;
	}
	
	public void run() {
		System.out.print("schedule_id = ");
		System.out.println(sid);
		int status_id = 0;
		if (DBSelectQuery.dbConnect(qid,eURL,sURL,pid,sloc)) {
			IHarvester harvester = null;
			Query qr = DBSelectQueryForId.dbConnect(qid);
			//Static loading
			String prtName = DBSelectProtocolNameForId.dbConnect(pid.getValue());
			switch (prtName) {
				case "aleph":
					harvester = new aleph.AlephHarvester();					
					break;
				case "opac":
					harvester = new opac.OpacHarvester();
					break;
				case "sru":
					harvester = new sru.SruHarvester();
					break;
				case "marcsql":
					harvester = new marcsql.MarcSQLHarvester();
					break;
			}
			
			//geonetwork loading
			/*harvester = new geonetwork.GeonetworkHarvester();
			*/
			try {
				status_id = harvester.harvest(new HarvesterInfo(qr));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Dynamic loading of classes
			/*String protPath = DBGetProtocolPathForId.dbConnect(pid.getValue());
			String protClass = DBGetProtocolClassForId.dbConnect(pid.getValue());
			try {
				status_id = ModuleEngine.main(protPath, protClass, );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
				
		} else {
		}
		System.out.print("trying to update schedule with id = ");
		System.out.println(sid);
		System.out.print("status_id = ");
		System.out.println(status_id);
		DBUpdateScheduleStatus.dbConnect(sid, status_id);
		AlarmStarter.updateTask();
	}
	
	public static void main(String[] args) {
		
	}
}
