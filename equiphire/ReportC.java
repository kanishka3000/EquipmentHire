/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire;

import equiphire.util.Connection;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;



/**
 *
 * @author kanishkaw
 */
public class ReportC {

    String reportSource = "I:\\EquipHire\\src\\equiphire\\Equipment.jrxml";
    String reportDest = "C:\\Documents and Settings\\kanishkaw\\My Documents\\NetBeansProjects\\jaspertest\\src\\jaspertestoutput.html";
    Map<String, Object> params = new HashMap<String, Object>();

    public ReportC() {
    }

    public static void main(String args[]) {

        new ReportC().getReport();
    }

    public void getReport() {

        try {
            JasperReport jasperReport =
                    JasperCompileManager.compileReport(reportSource);
            java.sql.Connection conn = Connection.getConnectionObject();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            //  JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            // JasperViewer.viewReport(jasperPrint);
            Reportinterface inter = new Reportinterface();
            JRViewer viewer=new JRViewer(jasperPrint);
            viewer.setSize(830, 400);
            inter.MainPanel.add(viewer, BorderLayout.CENTER);
            inter.MainPanel.revalidate();
            inter.setVisible(true);
//          JRViewer jrviewr=new JRViewer(jasperPrint);
//          jaspertest.Outer outer=new jaspertest.Outer();
//          outer.mainpanel.add(jrviewr,BorderLayout.CENTER);
//          outer.mainpanel.revalidate();
//          outer.setVisible(true);


        } catch (JRException ex) {



            ex.printStackTrace();
        }
    }
}
