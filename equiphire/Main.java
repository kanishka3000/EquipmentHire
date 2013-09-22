/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire;

import equiphire.util.ServiceMonitor;


/**
 *
 * 
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
      Home start=new Home();
      start.setVisible(true);
      ServiceMonitor service=new ServiceMonitor();
      service.setInterface(start);
      Thread t=new Thread(service);
      t.start();
     
    }
}
