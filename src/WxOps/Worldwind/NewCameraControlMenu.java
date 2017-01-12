/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;

/**
 *
 * @author PC
 */

// MARKED FOR DELETION


public class NewCameraControlMenu extends javax.swing.JPanel {
 
    private int c;
    private WorldWindow wwd;
    //public CameraControlWindow ccw;
 
    public NewCameraControlMenu(WorldWindow wwd) { // This section initialise the generated code.
        this.wwd = wwd;
        initMenuComponents();

    }    
    private void initMenuComponents() {
        
        
    }
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        View view = this.wwd.getView();
        Position p = view.getEyePosition();

        //lat and lon
        jTextField1.setText(String.format("%.5f", p.longitude.degrees)); //lon
        jTextField2.setText(String.format("%.5f", p.latitude.degrees)); //lat

        double z = p.getElevation();
        jTextField3.setText(String.format("%.1f", z));

        double roll = view.getRoll().degrees;
        jTextField4.setText(String.format("%.3f", roll));

        //tilt which is pitch in WWJ
        double tilt = view.getPitch().degrees;
        jTextField5.setText(String.format("%.3f", tilt));

        //azimuth which is heading in WWJ
        double azi = view.getHeading().degrees;
        jTextField6.setText(String.format("%.3f", azi));

        //field of view
        double fov = view.getFieldOfView().degrees;
        jTextField7.setText(String.format("%.3f", fov));
    }                                        
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        refresh();
    } 
    
    private void refresh() {
        BasicOrbitView view = (BasicOrbitView) this.wwd.getView();
        view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));
        view.setRoll(Angle.fromDegrees(Double.parseDouble(jTextField4.getText())));
        view.setPitch(Angle.fromDegrees(Double.parseDouble(jTextField5.getText())));
        view.setHeading(Angle.fromDegrees(Double.parseDouble(jTextField6.getText())));
        view.setFieldOfView(Angle.fromDegrees(Double.parseDouble(jTextField7.getText())));
        this.wwd.redraw();
    }
    
    public void jsetPOV(String sLon, String sLat, String sRng, String sRoll,
            String sTilt, String sAzi, String sFOV, String sSpd) {
        try {
            jTextField1.setText(sLon); //lon
            jTextField2.setText(sLat); //lat
            //elevation OR range
            jTextField3.setText(sRng);//double zwx = 19070000.0;
            jTextField4.setText(sRoll);//tilt which is pitch in WWJ
            jTextField5.setText(sTilt);//azimuth which is heading in WWJ
            jTextField6.setText(sAzi);
            jTextField7.setText(sFOV);//field of view
            refresh();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
 
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration                   

}
