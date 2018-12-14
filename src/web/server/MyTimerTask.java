package web.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import web.demo.*;


 public class MyTimerTask extends TimerTask{
     private static StringBuffer url = new StringBuffer();
     @Override
     public void run(){
    	 try {
			Runtime.getRuntime().exec("/home/levishery/Documents/Web/search/crawlers/crawlers/spiders/task.sh");
			nlp.extract();
			Index.index();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
 
}
