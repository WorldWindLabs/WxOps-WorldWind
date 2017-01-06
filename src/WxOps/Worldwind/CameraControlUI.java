/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;
import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.view.orbit.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.Calendar;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchService;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.*; //file reader
import javax.swing.SwingUtilities;
/**
 *
 * @author Pete Subacz
 */
public class CameraControlUI extends javax.swing.JFrame {

    /**
     * Creates new form CamUI
     */
    public CameraControlUI() {
        initComponents();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        worldWindowGLCanvas1.setModel(m);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        worldWindowGLCanvas1 = new gov.nasa.worldwind.awt.WorldWindowGLCanvas();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jTextField1.setText("-95.26548");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setText("38.95939");

        jTextField3.setText("0.0");

        jLabel1.setText("Lon");

        jLabel2.setText("Lat");

        jLabel3.setText("Z");

        jCheckBox1.setText("Alt Mode");

        jTextField4.setText("0.0");

        jTextField5.setText("0.000");

        jTextField6.setText("0.000");

        jTextField7.setText("0.000");

        jTextField8.setText("1");

        jLabel4.setText("Alt");

        jLabel5.setText("Roll");

        jLabel6.setText("Tilt");

        jLabel7.setText("Azi");

        jLabel8.setText("Spd");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel9.setText("FOV");

        jTextField9.setText("0.000");

        jButton3.setText("Clock");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setText("jLabel10");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jButton1)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jButton2))
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jTextField6)
                                                .addComponent(jTextField5)
                                                .addComponent(jTextField4)
                                                .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField9)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(36, 36, 36)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(worldWindowGLCanvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(worldWindowGLCanvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //protected gov.nasa.worldwindx.examples.ApplicationTemplate.AppPanel wwjPanel;
    //private WorldWindow wwd;
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //get camera
        try{
            
            View view = this.worldWindowGLCanvas1.getView();
            Position p = view.getEyePosition();
            
            //lat and lon
            jTextField1.setText(String.format("%.5f",p.longitude.degrees)); //lon
            jTextField2.setText(String.format("%.5f", p.latitude.degrees)); //lat
            
            //elevation
            double z = p.getElevation();
            jTextField3.setText(String.format("%.1f", z));
            
            //altitude - same as elevation in default mode
            double alt = p.getAltitude();
            jTextField4.setText(String.format("%.1f", alt));
            
            //alt mode
            //???
            
            //range isn't used to set camera, instead Roll and Field of View is used
            double roll = view.getRoll().degrees;
            jTextField5.setText(String.format("%.3f", roll));
            
            //tilt which is pitch in WWJ
            double tilt = view.getPitch().degrees;
            jTextField6.setText(String.format("%.3f", tilt));
            
            //azimuth which is heading in WWJ
            double azi = view.getHeading().degrees;
            jTextField7.setText(String.format("%.3f", azi));
            
            //field of view
            double fov = view.getFieldOfView().degrees;
            jTextField9.setText(String.format("%.3f", fov));
        }
        catch (Exception ex){
            jTextArea1.setText(ex.toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // set camera
        try{
            //BasicFlyView view = (BasicFlyView) this.wwd.getView();
            BasicOrbitView view = (BasicOrbitView) this.worldWindowGLCanvas1.getView();
            
            view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));

            view.setHeading(Angle.fromDegrees(Double.parseDouble(jTextField7.getText())));
            view.setPitch(Angle.fromDegrees(Double.parseDouble(jTextField6.getText())));
            view.setFieldOfView(Angle.fromDegrees(Double.parseDouble(jTextField9.getText())));
            view.setRoll(Angle.fromDegrees(Double.parseDouble(jTextField5.getText())));
            
            //view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));
            
            
            this.worldWindowGLCanvas1.redraw();
            
        }
        catch (Exception ex){
            jTextArea1.setText(ex.toString());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    //Clock HH:mm:ss
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // implements shipley method to emulate COM command
        //initialize timer (heartbeat)
        Timer twx = new Timer(1000, new ClockListener());
        twx.start();
        jTextArea1.setText("heya");

        //execute WWJ function
        try {
            //lat and lon
            double longwx = -175.0;
            double latwx = 7.78;
            jTextField1.setText(String.format("%.5f", longwx)); //lon
            jTextField2.setText(String.format("%.5f", latwx)); //lat

            //elevation
            double zwx = 19070000.0;
            jTextField3.setText(String.format("%.1f", zwx));

            //altitude - same as elevation in default mode
            double altwx = 19070000.0;
            jTextField4.setText(String.format("%.1f", altwx));

            //range isn't used to set camera, instead Roll and Field of View is used
            double rollwx = 0.0;
            jTextField5.setText(String.format("%.3f", rollwx));

            //tilt which is pitch in WWJ
            double tiltwx = 0.0;
            jTextField6.setText(String.format("%.3f", tiltwx));

            //azimuth which is heading in WWJ
            double aziwx = 0.0;
            jTextField7.setText(String.format("%.3f", aziwx));

            //field of view
            double fovwx = 45;
            jTextField9.setText(String.format("%.3f", fovwx));

            //BasicFlyView view = (BasicFlyView) this.wwd.getView();
            BasicOrbitView view = (BasicOrbitView) this.worldWindowGLCanvas1.getView();

            view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));

            view.setHeading(Angle.fromDegrees(Double.parseDouble(jTextField7.getText())));
            view.setPitch(Angle.fromDegrees(Double.parseDouble(jTextField6.getText())));
            view.setFieldOfView(Angle.fromDegrees(Double.parseDouble(jTextField9.getText())));
            view.setRoll(Angle.fromDegrees(Double.parseDouble(jTextField5.getText())));
            //view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));
            this.worldWindowGLCanvas1.redraw();
            jLabel10.setText("heya");
        } catch (Exception ex) {
            jTextArea1.setText(ex.toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

                                     

    //cannot be called from static class (WatchDir)
    public void jsetPOV(String sLon, String sLat, String sRng, String sRoll,
            String sTilt, String sAzi, String sFOV, String sSpd) {
        try {
            //lat and lon
            jTextField1.setText(sLon); //lon
            jTextField2.setText(sLat); //lat

            //elevation
            //double zwx = 19070000.0;
            jTextField3.setText(sRng);
            //altitude - same as elevation in default mode
            jTextField4.setText(sRng);

            //range isn't used to set camera, instead Roll and Field of View is used
            jTextField5.setText(sRoll);

            //tilt which is pitch in WWJ
            jTextField6.setText(sTilt);

            //azimuth which is heading in WWJ
            jTextField7.setText(sAzi);

            //field of view
            jTextField9.setText(sFOV);

            //BasicFlyView view = (BasicFlyView) this.wwd.getView();
            BasicOrbitView view = (BasicOrbitView) this.worldWindowGLCanvas1.getView();

            view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));

            view.setHeading(Angle.fromDegrees(Double.parseDouble(jTextField7.getText())));
            view.setPitch(Angle.fromDegrees(Double.parseDouble(jTextField6.getText())));
            view.setFieldOfView(Angle.fromDegrees(Double.parseDouble(jTextField9.getText())));
            view.setRoll(Angle.fromDegrees(Double.parseDouble(jTextField5.getText())));
            //view.setEyePosition(Position.fromDegrees(Double.parseDouble(jTextField2.getText()), Double.parseDouble(jTextField1.getText()), Double.parseDouble(jTextField3.getText())));
            this.worldWindowGLCanvas1.redraw();
            jLabel10.setText("com api " + sLon + "," + sLat);
        } catch (Exception ex) {
            jTextArea1.setText(ex.toString());
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CameraControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CameraControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CameraControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CameraControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        final CameraControlUI cameraControlUI = new CameraControlUI();
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                cameraControlUI.setVisible(true);
            }
        });
        
        //spawn a separate thread
        try {
            Path dir = Paths.get("c:/test1");
            // works as static process, but cannot talk to CamUI runtime
            // *************
            WatchDir2 watcher = new WatchDir2(dir, true);
            watcher.setCamUI(cameraControlUI);
            watcher.processEvents();
            // *************                
        } catch (Exception ex) {
        }
        
    } //main

    // This is a simple clock to show HH:mm:ss updating every second      
    class ClockListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            int h = now.get(Calendar.HOUR_OF_DAY);
            int m = now.get(Calendar.MINUTE);
            int s = now.get(Calendar.SECOND);
            jLabel10.setText("" + h + ":" + m + ":" + s);
        }
    } //ClockListener
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private gov.nasa.worldwind.awt.WorldWindowGLCanvas worldWindowGLCanvas1;
    // End of variables declaration//GEN-END:variables
}
