package opac;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import common.IHarvester;
import common.HarvesterInfo;


public class OpacHarvester implements IHarvester {
	private final String formatPath = "compiledHarvesters\\Release\\OpacHarvester.exe {0} {1}";
	@Override
	public int harvest(HarvesterInfo params){
		
		String line = null;
		try {
			String endUrl = params.getEndURL();
			
			String iddb = xml.ErrorMessagesXMLParser.getParameters(params.getStruct_loc()).get("iddb");
			Process p = Runtime.getRuntime().exec(MessageFormat.format(formatPath, endUrl, iddb));
			BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = bri.readLine()) != null) {
				switch (line) {
					case "3":
						System.out.println(bri.readLine());
						return 3;
					case "1":
						return 1;
					default:
						System.out.println(bri.readLine());
						return 2;
				}
					
			}
			bri.close();
			while ((line = bre.readLine()) != null) {
				System.out.println(line);
			}
			bre.close();
			p.waitFor();
		}
		catch(Exception e){
			e.printStackTrace(System.err);
		}
		System.out.println(line);
		return 2;
	}
	public static void main(String[] args) { 
		OpacHarvester harv = new OpacHarvester();
		HarvesterInfo hinfo = new HarvesterInfo();
		hinfo.endURL = "http://aleph.nlr.ru/X";
		hinfo.struct_loc = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?><structure><baseValue>nlr01</baseValue></structure>";
		try {
			System.out.println(harv.harvest(hinfo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
