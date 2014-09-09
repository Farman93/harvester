package timePackage;

import common.IHarvester;
import common.HarvesterInfo;

public class ModuleEngine {

public static int main(String pth, String name, HarvesterInfo hinfo) throws Exception {

	ModuleLoader loader = new ModuleLoader(pth, ModuleEngine.class.getClassLoader());
 
	  try {
	    Class clazz = loader.loadClass(name);
	    IHarvester execute = (IHarvester)clazz.newInstance();
	    return execute.harvest(hinfo);
	  } catch (ClassNotFoundException e) {
	    e.printStackTrace();
	  } catch (InstantiationException e) {
	    e.printStackTrace();
	  } catch (IllegalAccessException e) {
	    e.printStackTrace();
	  }
	  return -1;
}
  
}
