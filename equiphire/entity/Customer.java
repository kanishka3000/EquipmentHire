/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;e

/** customer;
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
 * 
 */
public class Customer {
public static Collection createBeanCollection(){

return null;
}
    private static Customer fillData(ResultSet res, Customer customer) throws SQLException {
        while (res.next()) {
            customer = new Customer();
            customer.setId(res.getString("nid"));
            customer.setName(res.getString("name"));
            customer.setAddress(res.getString("address"));
            customer.setTelephone(res.getString("telephone"));
            customer.setIsTrusted(res.getString("trusted"));
        }
        return customer;
    }
    private String Id;
    private String Name;
    private String Address;
    private String Telephone;
    private String Date;
    private boolean isTrusted;
    public String Exception;
    public String registrationDate;
    private equiphire.util.Connection con;

    public Customer() {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-M-d");
        this.Date = dt.format(new Date());
    }

    /**
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * @return the Telephone
     */
    public String getTelephone() {
        return Telephone;
    }

    /**
     * @param Telephone the Telephone to set
     */
    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    /**
     * @return the Date
     */
    public String getDate() {
        return Date;
    }

    /**
     * @param Date the Date to set
     */
    public void setDate(String Date) {
        Date d = new Date();



    }

    public boolean saveCustomer() {
        if (customerExists(this.getId()) == null) {
            String query = "insert into customer(nid,name,address,telephone,registrationdate,trusted) values('" + getId() + "','" + getName() + "','" + getAddress() + "','"+getTelephone()+"','"+getDate()+"','0')";
//System.out.println(query);
            try {
                equiphire.util.Connection.getConnectionInstance().runQuery(query);
            } catch (Exception e) {
                this.Exception = "saving Error:" + e.toString();
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            //System.out.println("Duplication detected");
            this.Exception = "Duplication of the customer";
            return false;
        }

    }

    public static Customer customerExists(String cid) {
        String query = "select * from customer where nid='" + cid + "'";
        Customer customer = null;
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            customer = fillData(res, customer);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return customer;
    }

    public boolean isIsTrusted() {
        return isTrusted;
    }

    public void setIsTrusted(String isTrusted) {
        boolean tu;
        if (isTrusted.equals("0")) {
            tu = false;
        } else {
            tu = true;
        }
        this.isTrusted = tu;
    }

    public ArrayList<Customer> checkIfCustomerisTrusted() {
        String query = "select * from customer where trusted='0'";
        ArrayList<Customer> list = null;
        try {
            ResultSet res = equiphire.util.Connection.getConnectionInstance().getResult(query);
            list = new ArrayList<Customer>();
            while (res.next()) {
                Customer customer = new Customer();
                customer.setId(res.getString("nid"));
                customer.setName(res.getString("name"));
                customer.setAddress(res.getString("address"));
                customer.setTelephone(res.getString("telephone"));
                customer.setIsTrusted(res.getString("trusted"));
                customer.registrationDate = res.getString("registrationdate");

                list.add(customer);
                // return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void makeTrusted() {
        String query = "update customer set trusted='1' where nid='" + getId() + "'";
        try {
            equiphire.util.Connection.getConnectionInstance().runQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
