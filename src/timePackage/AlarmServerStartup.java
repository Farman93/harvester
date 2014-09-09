package timePackage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class AlarmServerStartup
 */
public class AlarmServerStartup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlarmServerStartup() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
    	AlarmStarter.newTask();
    }

}
