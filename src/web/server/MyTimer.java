package web.server;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

 public class MyTimer implements ServletContextListener {
     private Timer timer = null;
     @Override
     public void contextDestroyed(ServletContextEvent arg0) {
         timer.cancel();
     }
 
     @Override
     public void contextInitialized(ServletContextEvent arg0) {
         System.out.println("-----开始启动定时器------");
         Calendar twentyOne = Calendar.getInstance();
         twentyOne.set(Calendar.HOUR_OF_DAY, 23);
         twentyOne.set(Calendar.MINUTE, 0);
         twentyOne.set(Calendar.SECOND, 0);
         timer = new Timer(true);
         //第一次是晚上23点执行，间隔24小时执行一次
         timer.schedule(new MyTimerTask(), twentyOne.getTime(), 1000*60*60*24);
         
         
         /*System.out.println("-----开始启动定时器------");
         Calendar twentyOne = Calendar.getInstance();
         twentyOne.set(Calendar.HOUR_OF_DAY, 14);
         twentyOne.set(Calendar.MINUTE, 47);
         twentyOne.set(Calendar.SECOND, 0);
         timer = new Timer(true);
         timer.schedule(new MyTimerTask(), twentyOne.getTime(), 1000*10);*/
     }
     
 }
