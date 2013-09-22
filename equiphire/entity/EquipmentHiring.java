/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
mysql> desc hire;
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
+--------------+-------------+--
 *mysql> desc payment;
+------------+-------------+-
| Field      | Type        |
+------------+-------------+-
| cost       | varchar(10) |
| h_id       | varchar(10) |
| returndate | varchar(10) |
+------------+-------------+-
 */
public class EquipmentHiring {

    public String hid;
    public String nid;
    public String eid;
    public String hiredate;
    public int deposit;
    public int complete;
    public int DailyRental;
    public int Depositvalue;
    public long payment;

    public long profit;




    public EquipmentHiring[] checkUnfinishedHiring(String C_id) {
        String query = "select * from hire left join equipment  on hire.eid=equipment.eid where hire.nid='" + C_id + "' and complete='0'";
        // System.out.println(query);
        EquipmentHiring[] equipments = null;
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            ArrayList<EquipmentHiring> list = new ArrayList<EquipmentHiring>();
            fillData(res, list);
            equipments = new EquipmentHiring[list.size()];
            equipments = list.toArray(equipments);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return equipments;
    }

    private void fillData(ResultSet res, ArrayList<EquipmentHiring> list) throws SQLException {
        while (res.next()) {
            EquipmentHiring eq = new EquipmentHiring();
            eq.hid = res.getString("hid");
            eq.nid = res.getString("nid");
            eq.eid = res.getString("eid");
            eq.hiredate = res.getString("hiredate");
            eq.deposit = new Integer(res.getString("deposit"));
            eq.complete = new Integer(res.getString("complete"));
            eq.Depositvalue = new Integer(res.getString("depositvalue"));
            eq.DailyRental = new Integer(res.getString("dailyrental"));
            try {
                eq.calculatePayment();
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(eq);
        }
    }

    public void makeHiring(Customer cust, Equipment[] Eq_ids) {
        String trusted;
        if (cust.isIsTrusted()) {
            trusted = "0";
        } else {
            trusted = "1";
        }
        Date data = new Date();
        SimpleDateFormat dtformat = new SimpleDateFormat("yyyy-M-d");
        String oudate = dtformat.format(data);
        for (Equipment Eq_id : Eq_ids) {
            String query = "insert into hire(nid,eid,hiredate,deposit,complete)values ('" + cust.getId() + "','" + Eq_id.Eid + "','" + oudate + "','" + trusted + "','0')";
            try {
                equiphire.util.Connection.getConnectionInstance().runQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void calculatePayment() throws Exception {
        Date today = new Date();
        Date regday = new SimpleDateFormat("yyyy-M-d").parse(this.hiredate);
        long Milsforaday = 1000 * 60 * 60 * 24;
        long noofdays = (today.getTime() - regday.getTime()) / Milsforaday + 1;
        if (noofdays == 0) {
            noofdays = 1;
        }
        if (this.deposit == 0) {
            // a trusted customer
            this.payment = this.DailyRental * noofdays;
            this.profit=this.payment;
        } else {
            this.payment = -(this.Depositvalue - (this.DailyRental * noofdays));
            this.profit=this.DailyRental * noofdays;
        }
    }

    public EquipmentHiring[] incompleteTasks(){
    String query = "select * from hire left join equipment  on hire.eid=equipment.eid where complete='0'";
        // System.out.println(query);
        EquipmentHiring[] equipments = null;
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            ArrayList<EquipmentHiring> list = new ArrayList<EquipmentHiring>();
            fillData(res, list);
            equipments = new EquipmentHiring[list.size()];
            equipments = list.toArray(equipments);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return equipments;
    }

}
