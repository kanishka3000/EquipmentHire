/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package equiphire.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
mysql> desc equipment;
+--------------+---------
| Field        | Type
+--------------+---------
| eid          | varchar(
| type         | varchar(
| dailyrental  | varchar(
| depositvalue | varchar(
+--------------+---------
 *
 */
public class Equipment {
public String Eid;
public String type;
public String DailyRental;
public String DepositValue;
@Override
public String toString(){

return this.type+" | "+this.DailyRental+" | "+this.DepositValue;
}

public Equipment[] getEquipments(){
String query="select * from equipment";
try{
ResultSet res=equiphire.util.Connection.getConnectionInstance().getResult(query);
ArrayList<Equipment> list=new ArrayList<Equipment>();
            Equipment[] retlist = fillData(res, list);
return retlist;
}catch(Exception e){
e.printStackTrace();}
return null;
}

    private Equipment[] fillData(ResultSet res, ArrayList<Equipment> list) throws SQLException {
        while (res.next()) {
            Equipment eq = new Equipment();
            eq.Eid = res.getString("eid");
            eq.type = res.getString("type");
            eq.DailyRental=res.getString("dailyrental");
            eq.DepositValue=res.getString("depositvalue");
            list.add(eq);
        }
        Equipment[] retlist = new Equipment[list.size()];
        retlist = list.toArray(retlist);
        return retlist;
    }

}
