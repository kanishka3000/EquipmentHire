/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package equiphire.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *mysql> desc payment;
+------------+-------------+-
| Field      | Type        |
+------------+-------------+-
| cost       | varchar(10) |
| h_id       | varchar(10) |
| returndate | varchar(10) |
+------------+-------------+-
 * mysql> desc hire;
+----------+-------------+
| Field    | Type        |
+----------+-------------+
| hid      | int(10)     |
| nid      | varchar(10) |
| eid      | varchar(10) |
| hiredate | varchar(15) |
| deposit  | varchar(3)  |
| complete | varchar(3)  |
+----------+-------------+
 */
public class Payment {
public void makePayment(EquipmentHiring[] requests){
    SimpleDateFormat dt=new SimpleDateFormat("yyyy-M-d");
   Date date=new Date();
   String dat=dt.format(date);
    for(EquipmentHiring eq:requests){
    String query="insert into payment (h_id,cost,returndate)values("+eq.hid+",'"+eq.profit+"','"+dat+"') ";
    //System.out.print(query);
    String query2="update hire set complete='1' where hid="+eq.hid+"";
    try{
    equiphire.util.Connection.getConnectionInstance().runQuery(query);
    equiphire.util.Connection.getConnectionInstance().runQuery(query2);
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    
}

}
