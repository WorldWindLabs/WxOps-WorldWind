package WxOps.Worldwind;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.view.orbit.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.*;

/**
 *
 * @author pSubacz, wxazygy 9 Jan 2017, updated 14 Jan 2017
 * added jgetPOV() to support getPOV function
 */
public class CameraControlWindow extends javax.swing.JFrame {

    private int c;
    private WorldWindow wwd;
    private String pov;
    private String responseFile = "";
    private String response = "";
    //public CameraControlWindow ccw;
 
    public CameraControlWindow(WorldWindow wwd) { // This section initialise the generated code.
        this.wwd = wwd;
        initComponents();
        if(c<1){
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  //DISPOSE_ON_CLOSE
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); This function will end the program 
        }
        //ccw = this;
        this.setTitle("POV");
        refreshGET();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();  //Lon
        jLabel2 = new javax.swing.JLabel();  //Lat
        jLabel3 = new javax.swing.JLabel();  //Alt
        jLabel4 = new javax.swing.JLabel();  //Roll
        jLabel5 = new javax.swing.JLabel();  //Tilt
        jLabel6 = new javax.swing.JLabel();  //Azi
        jLabel7 = new javax.swing.JLabel();  //FOV
        jButton1 = new javax.swing.JButton();  //Get Cam
        jButton2 = new javax.swing.JButton();  //Set Cam
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Longitude");

        jLabel2.setText("Latitude");

        jLabel3.setText("Altitude(z)");

        jLabel4.setText("Roll");

        jLabel5.setText("Tilt");

        jLabel6.setText("Azimuth");

        jLabel7.setText("F.O.V.");

        jButton1.setText("Get Camera");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Set Camera");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        
        jCheckBox1.setText("Start Clock");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel8.setText(" Camera Control Panel");

        jTextField1.setText("(Null)");

        jTextField2.setText("(Null)");

        jTextField3.setText("(Null)");

        jTextField4.setText("(Null)");

        jTextField5.setText("(Null)");

        jTextField6.setText("(Null)");

        jTextField7.setText("(Null)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField4)
                    .addComponent(jTextField5)
                    .addComponent(jTextField6)
                    .addComponent(jTextField7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jTextField3))
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
// If the checkbox is clicked, the counter 
  //      if (c == 0) {
  //          c = 0;
  //          c++;
  //      } else {
  //          c = 0;
  //      }
        Timer twx = new Timer(1000, new ClockListener());
        twx.start();
    }                                          

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        refreshGET();
    }
    private void refreshGET() {
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

    
   
    public void jgetPOV() {
        View view = this.wwd.getView();
        Position p = view.getEyePosition();;
        pov = "getpov,";
        pov = pov + String.format("%.5f", p.longitude.degrees) + ",";
        pov = pov + String.format("%.5f", p.latitude.degrees) + ","; 
        double z = p.getElevation();
        pov = pov + String.format("%.1f", z) + ","; 
        double roll = view.getRoll().degrees;
        pov = pov + String.format("%.3f", roll) + ","; 
        double tilt = view.getPitch().degrees;
        pov = pov + String.format("%.3f", tilt) + ","; 
        double azi = view.getHeading().degrees;
        pov = pov + String.format("%.3f", azi) + ","; 
        double fov = view.getFieldOfView().degrees;
        pov = pov + String.format("%.3f", fov) + ",5"; 
       
        //write pov to response
        responseFile = "C:/test1/response.txt";
        response = pov;
        //write to response.txt
        writeCmdArgs(response);
        
    }
     public void writeCmdArgs(String line) {
        try {
            PrintWriter pw2 = new PrintWriter(new FileWriter(responseFile, false));
            pw2.println(line);
            pw2.flush();
            pw2.close();   
        } catch (IOException x) {
                        // ignore to keep sample readbale
        }
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        refreshSET();
    } 
    
    private void refreshSET() {
        BasicOrbitView view = (BasicOrbitView) this.wwd.getView();
        view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));
        view.setRoll(Angle.fromDegrees(Double.parseDouble(jTextField4.getText())));
        view.setPitch(Angle.fromDegrees(Double.parseDouble(jTextField5.getText())));
        view.setHeading(Angle.fromDegrees(Double.parseDouble(jTextField6.getText())));
        view.setFieldOfView(Angle.fromDegrees(Double.parseDouble(jTextField7.getText())));
        this.wwd.redraw();
    }
    
    //cannot be called from static class (WatchDir)
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
            refreshSET();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
   
    class ClockListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            String hh = String.format("%02d", now.get(Calendar.HOUR_OF_DAY));
            String mm = String.format("%02d", now.get(Calendar.MINUTE));
            String ss = String.format("%02d", now.get(Calendar.SECOND));
            jCheckBox1.setText("" + hh + ":" + mm + ":" + ss + " Z");
        }
    } //ClockListener
    // Variables declaration - do not modify                     
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
