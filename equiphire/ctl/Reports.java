/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package equiphire.ctl;

import equiphire.entity.DailyUsageReports;

/**
 *
 * 
 */
public class Reports {
equiphire.Reports inter=null;
equiphire.entity.DailyUsageReports hirerep=null;

    public Reports(equiphire.Reports inter) {
        this.inter=inter;
    }
    public DailyUsageReports[] getEquipmentUsage(String equipment){
    if(hirerep==null){
      hirerep=new equiphire.entity.DailyUsageReports();
      }
       
        return hirerep.getEquipmentHistory(equipment);
    }

   public DailyUsageReports[] getDailyUsageReports(String date){
      if(hirerep==null){
      hirerep=new equiphire.entity.DailyUsageReports();
      }
      return hirerep.getDailyUsage(date);

   }
public DailyUsageReports[] getUsageofCustomer(String cid){
if(hirerep==null){
      hirerep=new equiphire.entity.DailyUsageReports();
      }

    return hirerep.getUsageOfCusotmer(cid);
}
}
