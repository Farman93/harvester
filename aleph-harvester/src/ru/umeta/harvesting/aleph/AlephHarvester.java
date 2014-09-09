package ru.umeta.harvesting.aleph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import ru.umeta.harvesting.base.IHarvester;
import ru.umeta.harvesting.base.Query;


public class AlephHarvester implements IHarvester {
    @Override
	public int harvest(Query params){
		
		String line = null;
		try {
			String endUrl = params.getEndURL();
			
			String baseValue = Query.getParameters(params.getStruct_loc()).get("baseValue");
			Process p = Runtime.getRuntime().exec(MessageFormat.format("compiledHarvesters\\Release\\AlephHarvester.exe {0} {1}", endUrl, baseValue));
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
		AlephHarvester harv = new AlephHarvester();
		Query hinfo = new Query();
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
