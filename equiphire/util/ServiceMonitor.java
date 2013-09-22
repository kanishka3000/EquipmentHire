/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire.util;

import equiphire.entity.Customer;
import equiphire.entity.EquipmentHiring;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * 
 */
public class ServiceMonitor implements Runnable {

    private Customer customer = null;
    private EquipmentHiring equipmenthire = null;
    private equiphire.Home homeinterface;

    public ServiceMonitor() {
        customer = new Customer();
        equipmenthire = new EquipmentHiring();

        System.out.println("Service monitor service activated......");
    }
    public void setInterface(equiphire.Home home){
    this.homeinterface=home;
    }

    public void run() {
        while (true) {
            EquipmentHiring[] eqs = this.equipmenthire.incompleteTasks();
            System.out.println("checking for exceeding hire values.....");
            for (EquipmentHiring eq : eqs) {
                if (eq.profit > (eq.Depositvalue * 3 / 4)) {
                    System.out.println(eq.hid + "is exceeeding the allowd value as" + eq.profit);
                 
                    Customer cu=Customer.customerExists(eq.nid);
                       homeinterface.addBackgroundInfo(eq.hid + "is exceeeding the allowd value as" + eq.profit+"- I am sending an Email to-"+cu.getAddress());
                    if(cu!=null){
                    SendMailUsage.main("hiring", cu.getAddress(), "Notification of hiring", "Please return or renew your hring"+eq.eid+"hired on:"+eq.hiredate);
                    }
                } else {
                    System.out.println(eq.hid + "is not exceeding the allowd value as" + eq.profit);
                    homeinterface.addBackgroundInfo(eq.hid + "is not exceeding the allowd value as" + eq.profit);
                }
            }

            System.out.println("Checking for trusted customer updates....");
            homeinterface.addBackgroundInfo("Checking for trusted customer updates....");
            ArrayList<Customer> list = customer.checkIfCustomerisTrusted();
            if(list!=null){
            for(Customer cust:list){
          try{
                long MilisforanYear=1000*60*60*24*365;
                System.out.println(cust.registrationDate);
                long registrationdate=new SimpleDateFormat("yyyy-M-d").parse(cust.registrationDate).getTime();

                long today=new Date().getTime();
               long ans=(today-registrationdate)/MilisforanYear;
                if(ans>=1){
                    System.out.println("This customer is a trusted customer, I will update.."+cust.getId());
                     homeinterface.addBackgroundInfo("This customer is a trusted customer, I will update.."+cust.getId());
                    cust.makeTrusted();
                }else{
                System.out.println("Time requirement unfullfilled, not a trusted customer"+cust.getId());
                 homeinterface.addBackgroundInfo("Time requirement unfullfilled,, not a trusted customer"+cust.getId());

                }

          }catch(Exception e){
          e.printStackTrace();
          }
            }
            }
            try {
                System.out.println("Service handled the above events, I will check again in an hour.....");
                homeinterface.addBackgroundInfo("Service handled the above events, I will check again in an hour.....");
                Thread.sleep(1000 * 60*30);
            } catch (Exception e) {
            }
        }

    }
}
