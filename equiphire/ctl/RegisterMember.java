/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package equiphire.ctl;
import equiphire.entity.Customer;
/**
 *
 * 
 */
public class RegisterMember {
Customer customer;
equiphire.RegisterMember inter;
public RegisterMember(equiphire.RegisterMember inter){
this.customer=new Customer();
this.inter=inter;
}
    public void saveMember(String nid,String Name,String Address, String Telephone){
        this.customer.setId(nid);
        this.customer.setName(Name);
        this.customer.setAddress(Address);
        this.customer.setTelephone(Telephone);
        boolean result=this.customer.saveCustomer();
if(!result){
    inter.setAlert("Regisration failed-"+customer.Exception);
}else{
 inter.setAlert("Regisration suceeded.......");
}

}


}
