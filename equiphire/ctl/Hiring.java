/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire.ctl;

import equiphire.entity.*;

/**
 *
 *
 */
public class Hiring {

    equiphire.ctl.RegisterMember control = null;
    equiphire.Hiring inter;
    Customer customer = null;
    EquipmentHiring equphire = null;

    public Hiring(equiphire.Hiring inter) {
        this.inter = inter;
      //  this.control = new equiphire.ctl.RegisterMember(this);
    }

    public void startHiring(String nid) {
        customer = Customer.customerExists(nid);
        if (customer != null) {
            this.inter.setAlert("Customer found......");
            equphire = new EquipmentHiring();
            EquipmentHiring[] eq = equphire.checkUnfinishedHiring(nid);
           String msg="Trusted";
            if(!customer.isIsTrusted()){
               this.inter.setAlert("This customer is not trusted....");
               msg="Not trusted";
           }
            if (eq.length == 0) {
                this.inter.setAlert("Customer exists: No returning tasks,-"+msg);
                this.inter.setItemListVisible();
                this.inter.setExistPanelInvisible();
            } else {
                if (eq.length < 2) {

                    // this.inter.setItemListVisible();
                    this.inter.setRequestTableVisible(eq);
                    this.inter.setItemListInvisible();
                    //this.inter.setEquipmentDataTable(eq);
                } else {
                    this.inter.setAlert("Customer exists: Hired the maximum allowd at a time");
                    this.inter.setItemListInvisible();
                    this.inter.setRequestTableVisible(eq);
                }
            }

//return "Customer is:"+customer.getName()+"Address:"+customer.getAddress()+"Telephone:"+customer.getTelephone();
        } else {
//return "Customer Does not Exist";
            this.inter.setAlert("Customer Does not Exist");
            this.inter.setExistPanelInvisible();
            this.inter.setItemListInvisible();
        }

    }

    public void makeHiring(Equipment[] Eq_ids) throws Exception {
        if (this.customer != null) {
            equphire.makeHiring(customer, Eq_ids);
            this.inter.setAlert("Hring was handled successfully-"+customer.getId());
        } else {
            this.inter.setAlert("Process failed due to an unknown reason");
            throw new Exception("No Customer found");
        }

    }

    public void makePayment(EquipmentHiring[] equis) {
        Payment payment = new Payment();
        payment.makePayment(equis);


    }
}
