/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.layerviswwj;


import gov.nasa.worldwind.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.ogc.kml.*;
import gov.nasa.worldwindx.examples.kml.KMLViewController;
import gov.nasa.worldwindx.examples.util.HotSpotController;
import gov.nasa.worldwind.util.layertree.LayerTree;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.util.layertree.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.xml.stream.XMLStreamException;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.net.URL;
import javax.swing.border.*;
import java.io.File;


/**
 *
 * @author vhaley
 */
public class LayerVis extends javax.swing.JFrame {

    /**
     * Creates new form LayerVis
     */
    
    private int toggle1 = 0;
    protected LayerTree layerTree;
    protected RenderableLayer hiddenLayer;

    protected HotSpotController controller;
    
    public LayerVis() {
        initComponents();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        worldWindowGLCanvas1.setModel(m);
        
        File kmlFile = null;
        try{
            kmlFile = new File("");
        }
        catch (Exception ex){
            jTextArea1.setText(ex.toString());
        }
        
        KMLRoot kmlRoot = null;
        try{
            kmlRoot = KMLRoot.createAndParse(kmlFile);
        }
        catch (Exception ex){
            jTextArea1.setText(ex.toString());
        }
        
        kmlRoot.setField(AVKey.DISPLAY_NAME, kmlFile.toString());  
        // Create a KMLController to adapt the KMLRoot to the World Wind renderable interface.
        KMLController kmlController = new KMLController(kmlRoot);
  
        // Adds a new layer containing the KMLRoot to the end of the WorldWindow's layer list. This
        // retrieves the layer name from the KMLRoot's DISPLAY_NAME field.
        RenderableLayer kmlLayer = new RenderableLayer();
        kmlLayer.setName((String) kmlRoot.getField(AVKey.DISPLAY_NAME));
        kmlLayer.setValue(AVKey.HIDDEN, true);
        kmlLayer.addRenderable(kmlController);
        
        KMLViewController viewController = KMLViewController.create(worldWindowGLCanvas1);
        
        if (kmlRoot.getNetworkLinkControl() != null
            && kmlRoot.getNetworkLinkControl().getView() != null)
        {
            if (viewController != null)
                viewController.goTo(kmlRoot.getNetworkLinkControl().getView());
        }
        else if (kmlRoot.getFeature() != null
            && kmlRoot.getFeature().getView() != null)
        {
            if (viewController != null)
                viewController.goTo(kmlRoot.getFeature().getView());
        }
             
        worldWindowGLCanvas1.getModel().getLayers().add(kmlLayer);
        worldWindowGLCanvas1.redrawNow();
        
        this.layerTree = new LayerTree();
        this.layerTree.getModel().refresh(this.worldWindowGLCanvas1.getModel().getLayers());
        
        KMLLayerTreeNode layerNode = new KMLLayerTreeNode(kmlLayer, kmlRoot);
        this.layerTree.getModel().addLayer(layerNode);
        this.layerTree.makeVisible(layerNode.getPath());
        layerNode.expandOpenContainers(this.layerTree);
        
        
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
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 600));

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("KML Layer");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Toggle Layer Tree");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(worldWindowGLCanvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(worldWindowGLCanvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        
//        Layer kmlLayer = worldWindowGLCanvas1.getModel().getLayers().getLayerByName("C:\\Users\\Akki\\Documents\\NetBeansProjects\\CamWWJ\\src\\my\\layerviswwj\\FM10.1-PID.kmz");
//        
//        try{
//            if (((JCheckBox) evt.getSource()).isSelected())
//                kmlLayer.setEnabled(true);
//            else
//                kmlLayer.setEnabled(false);
//
//            worldWindowGLCanvas1.redraw();
//            
//        }
//        catch (Exception ex){
//            jTextArea1.setText(ex.toString());
//        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            // If the button is press
            if (toggle1 == 0) { //this is used to toggle the layer menu and add it to the screen.
                this.layerTree = new LayerTree();
                this.hiddenLayer = new RenderableLayer();
                this.hiddenLayer.addRenderable(layerTree);
                this.worldWindowGLCanvas1.getModel().getLayers().add(this.hiddenLayer);
                this.hiddenLayer.setValue(AVKey.HIDDEN, true);
                layerTree.getModel().refresh(this.worldWindowGLCanvas1.getModel().getLayers());
                this.controller = new HotSpotController(this.worldWindowGLCanvas1);
                
                File kmlFile = null;
                try{
                    kmlFile = new File("C:\\Users\\Akki\\Documents\\NetBeansProjects\\CamWWJ\\src\\my\\layerviswwj\\FM10.1-PID.kmz");
                }
                catch (Exception ex){
                    jTextArea1.setText(ex.toString());
                }
        
                KMLRoot kmlRoot = null;
                try{
                    kmlRoot = KMLRoot.createAndParse(kmlFile);
                }
                catch (Exception ex){
                    jTextArea1.setText(ex.toString());
                }
                
                Layer kmlLayer = worldWindowGLCanvas1.getModel().getLayers().getLayerByName("C:\\Users\\Akki\\Documents\\NetBeansProjects\\CamWWJ\\src\\my\\layerviswwj\\FM10.1-PID.kmz");
                
                KMLLayerTreeNode layerNode = new KMLLayerTreeNode(kmlLayer, kmlRoot);
                this.layerTree.getModel().addLayer(layerNode);
                this.layerTree.makeVisible(layerNode.getPath());
                layerNode.expandOpenContainers(this.layerTree);
                
                layerNode.addPropertyChangeListener(AVKey.RETRIEVAL_STATE_SUCCESSFUL, new PropertyChangeListener()
                {
                    public void propertyChange(final PropertyChangeEvent event)
                    {
                        if (event.getSource() instanceof KMLNetworkLinkTreeNode)
                        {
                            // Manipulate the tree on the EDT.
                            SwingUtilities.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    ((KMLNetworkLinkTreeNode) event.getSource()).expandOpenContainers(layerTree);
                                    worldWindowGLCanvas1.redraw();
                                }
                            });
                        }
                    }
                });

             //This set of code causes the program to resize it self.....
             //Dimension size = new Dimension(1000, 600);
             //this.setPreferredSize(size);

                this.pack();
                WWUtil.alignComponent(null, this, AVKey.CENTER);

                toggle1 = toggle1 + 1;
                //System.out.print(toggle1);
            }
            else if(toggle1 > 0) //this is used to toggle the layer menu and remove it to the screen.
            {
                this.hiddenLayer.removeAllRenderables();
                this.worldWindowGLCanvas1.redraw();
                toggle1 = 0;
                System.out.print(toggle1);
            }
        }
        catch (Exception ex){
            jTextArea1.setText(ex.toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(LayerVis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LayerVis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LayerVis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LayerVis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LayerVis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private gov.nasa.worldwind.awt.WorldWindowGLCanvas worldWindowGLCanvas1;
    // End of variables declaration//GEN-END:variables
}
