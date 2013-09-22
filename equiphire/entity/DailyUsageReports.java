/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * mysql> desc hire;
+----------+-------------
| Field    | Type
+----------+-------------
| hid      | varchar(10)
| nid      | varchar(10)
| eid      | varchar(10)
| hiredate | varchar(15)
| deposit  | varchar(3)
| complete | varchar(3)
+----------+-------------
 * mysql> desc equipment;
+--------------+-------------+--
| Field        | Type        | N
+--------------+-------------+--
| eid          | varchar(10) | N
| type         | varchar(5)  | N
| dailyrental  | varchar(10) | N
| depositvalue | varchar(10) | N
 *mysql> desc payment;
+------------+-------------+-
| Field      | Type        |
+------------+-------------+-
| cost       | varchar(10) |
| h_id       | varchar(10) |
| returndate | varchar(10) |
+------------+-------------+-
 * ustomer;
 *+------------------+------------------+
| Field            | Type             |
+------------------+------------------+
| nid              | int(10) unsigned |

| name             | varchar(100)     |

| address          | varchar(200)     |

| telephone        | varchar(15)      |

| registrationdate | varchar(15)      |

| trusted          | varchar(3)       |

+------------------+------------------+
 */
public class DailyUsageReports extends EquipmentHiring {

    public String CustomerId;
    public String CustomerName;
    public String CustomerAddress;
    public String CustomerTel;
    public String SubmitDate;

    public String EquipmentType;

    public DailyUsageReports() {
    }

    public DailyUsageReports[] getEquipmentHistory(String Eid) {
       DailyUsageReports[] fin = null;
        String query = "select * from hire left join payment on hire.hid=payment.h_id left join customer on hire.nid=customer.nid where hire.eid='"+Eid+"' order by hire.hiredate";
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            fin = fillData(res, fin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fin;
        
    }

    public DailyUsageReports[] getUsageOfCusotmer(String cust_id) {
        DailyUsageReports[] fin = null;
        String query = "select * from hire left join payment on hire.hid=h_id left join customer on hire.nid=customer.nid where customer.nid='" + cust_id + "'";
        //System.out.println(query);
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            fin = fillData(res, fin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fin;
    }

    public DailyUsageReports[] getDailyUsage(String date) {
        DailyUsageReports[] fin = null;
        String query = "select * from hire left join payment on hire.hid=h_id left join customer on hire.nid=customer.nid where payment.returndate='" + date + "' order by customer.name";
        //System.out.println(query);
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            fin = fillData(res, fin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fin;
    }

    private DailyUsageReports[] fillData(ResultSet res, DailyUsageReports[] fin) throws NumberFormatException, SQLException {
        ArrayList<DailyUsageReports> list = new ArrayList<DailyUsageReports>();
        while (res.next()) {
            DailyUsageReports eq = new DailyUsageReports();
            eq.hid = res.getString("hid");
            eq.nid = res.getString("nid");
            eq.eid = res.getString("eid");
            eq.hiredate = res.getString("hiredate");
            eq.deposit = new Integer(res.getString("deposit"));
            eq.complete = new Integer(res.getString("complete"));
            eq.SubmitDate = res.getString("returndate");
            // eq.Depositvalue = new Integer(res.getString("depositvalue"));
            //   eq.DailyRental = new Integer(res.getString("dailyrental"));
            eq.CustomerId = res.getString("nid");
            eq.CustomerName = res.getString("name");
            eq.CustomerAddress = res.getString("address");
            eq.CustomerTel = res.getString("telephone");
            String st = res.getString("cost");
            if (st != null) {
                eq.payment = new Long(st);
            }
            list.add(eq);
        }
        fin = new DailyUsageReports[list.size()];
        fin = list.toArray(fin);
        return fin;
    }
}
