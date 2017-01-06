/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.ogc.kml.*;
import gov.nasa.worldwindx.examples.kml.KMLViewController;
import gov.nasa.worldwindx.examples.kml.KMLApplicationController;
import gov.nasa.worldwindx.examples.util.HotSpotController;
import gov.nasa.worldwind.util.layertree.LayerTree;
import gov.nasa.worldwind.util.tree.TreeNode;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.util.layertree.*;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.ogc.kml.KMLTimeSpan;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import gov.nasa.worldwindx.examples.util.*;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import com.wxops.*;
import gov.nasa.worldwind.util.xml.XMLEventParserContextFactory;

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
import java.util.Date;

/**
 *
 * @author vhaley
 */
public class WorldWindUI_Test extends javax.swing.JFrame {
    
    static {
        // Register the WxOps KML parser context, giving it priority over the default KML parser context
        String[] mimeTypes = new String[]{KMLConstants.KML_MIME_TYPE, KMLConstants.KMZ_MIME_TYPE};
        XMLEventParserContextFactory.prependParserContext(mimeTypes, new WxOpsKMLParserContext(KMLConstants.KML_NAMESPACE));
    }
    
    protected LayerTree layerTree;
    protected RenderableLayer hiddenLayer;

    protected HotSpotController hotSpotController;
    protected KMLApplicationController kmlAppController;
    protected BalloonController balloonController;
    

    /**
     * Creates new form LayerAni2
     */
    public WorldWindUI_Test() {
        initComponents();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        worldWindowGLCanvas1.setModel(m);
        
        this.layerTree = new LayerTree(new Offset(20d, 160d, AVKey.PIXELS, AVKey.INSET_PIXELS));
        this.layerTree.getModel().refresh(this.worldWindowGLCanvas1.getModel().getLayers());
        
        this.hiddenLayer = new RenderableLayer();
        this.hiddenLayer.addRenderable(this.layerTree);
        this.worldWindowGLCanvas1.getModel().getLayers().add(this.hiddenLayer);
        
        this.hotSpotController = new HotSpotController(this.worldWindowGLCanvas1);
        
        this.kmlAppController = new KMLApplicationController(worldWindowGLCanvas1);
        
        this.balloonController = new BalloonController(worldWindowGLCanvas1)
        {
            @Override
            protected void addDocumentLayer(KMLRoot document)
            {
                addKMLLayer(document);
            }
        };
        
        this.kmlAppController.setBalloonController(balloonController);
        
        Dimension size = new Dimension(1400, 800);
        this.setPreferredSize(size);
        this.pack();
        WWUtil.alignComponent(null, this, AVKey.CENTER);
        
        makeMenu(this);
        
    }
    
    protected void addKMLLayer(KMLRoot kmlRoot)
        {
            KMLController kmlController = new KMLController(kmlRoot);

            RenderableLayer layer = new RenderableLayer();
            layer.setName((String) kmlRoot.getField(AVKey.DISPLAY_NAME));
            layer.addRenderable(kmlController);
            this.worldWindowGLCanvas1.getModel().getLayers().add(layer);

            KMLLayerTreeNode layerNode = new KMLLayerTreeNode(layer, kmlRoot);
            this.layerTree.getModel().addLayer(layerNode);
            this.layerTree.makeVisible(layerNode.getPath());
            layerNode.expandOpenContainers(this.layerTree);
            

            layerNode.addPropertyChangeListener(AVKey.RETRIEVAL_STATE_SUCCESSFUL, new PropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    if (event.getSource() instanceof KMLNetworkLinkTreeNode)
                    {
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
            
            final Date begin = WxOpsKMLTimeSpan.parseTimeString("2016-11-16T06:00:00Z"); //in order to work, these two strings must be updated to the current model dates/times
            final Date end = WxOpsKMLTimeSpan.parseTimeString("2016-11-17T11:59:59.9Z");
            final long delta = (end.getTime() - begin.getTime()) / 8;
            final Date date = new Date(begin.getTime());
            layer.setValue(WxOpsConstants.DATE, date);
            System.out.println(date);

            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    date.setTime(date.getTime() + delta);
                    if (date.compareTo(end) > 0) {
                        date.setTime(begin.getTime());
                    }
                    jTextArea1.setText(date.toString());
                    worldWindowGLCanvas1.redraw();
                }
            });
            timer.setInitialDelay(30000);
            
            jButton4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timer.start();
                }
            });
            
            jButton3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                }
            });
            
        }
    public static class WorkerThread extends Thread
    {
        protected Object kmlSource;
        protected WorldWindUI_Test layerAni;

        public WorkerThread(Object kmlSource, WorldWindUI_Test layerAni)
        {
            this.kmlSource = kmlSource;
            this.layerAni = layerAni;
        }
        
        public void run()
        {
            try
            {
                KMLRoot kmlRoot = this.parse();

                // Set the document's display name
                kmlRoot.setField(AVKey.DISPLAY_NAME, formName(this.kmlSource, kmlRoot));

                // Schedule a task on the EDT to add the parsed document to a layer
                final KMLRoot finalKMLRoot = kmlRoot;
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        layerAni.addKMLLayer(finalKMLRoot);
                        
                    }
                });
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
        }

        protected KMLRoot parse() throws IOException, XMLStreamException
        {
            return KMLRoot.createAndParse(this.kmlSource);
        }
    }
    
    protected static String formName(Object kmlSource, KMLRoot kmlRoot)
    {
        KMLAbstractFeature rootFeature = kmlRoot.getFeature();

        if (rootFeature != null && !WWUtil.isEmpty(rootFeature.getName()))
            return rootFeature.getName();

        if (kmlSource instanceof File)
            return ((File) kmlSource).getName();

        if (kmlSource instanceof URL)
            return ((URL) kmlSource).getPath();

        if (kmlSource instanceof String && WWIO.makeURL((String) kmlSource) != null)
            return WWIO.makeURL((String) kmlSource).getPath();

        return "KML Layer";
        
    }
    
    protected static void makeMenu(final WorldWindUI_Test layerAni)
    {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("KML/KMZ File", "kml", "kmz"));

        JMenuBar menuBar = new JMenuBar();
        layerAni.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openFileMenuItem = new JMenuItem(new AbstractAction("Open File...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                try
                {
                    int status = fileChooser.showOpenDialog(layerAni);
                    if (status == JFileChooser.APPROVE_OPTION)
                    {
                        new WorkerThread(fileChooser.getSelectedFile(), layerAni).start();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        fileMenu.add(openFileMenuItem);

        JMenuItem openURLMenuItem = new JMenuItem(new AbstractAction("Open URL...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                try
                {
                    String status = JOptionPane.showInputDialog(layerAni, "URL");
                    if (!WWUtil.isEmpty(status))
                    {
                        new WorkerThread(status.trim(), layerAni).start();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        fileMenu.add(openURLMenuItem);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        worldWindowGLCanvas1 = new gov.nasa.worldwind.awt.WorldWindowGLCanvas();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("<<");

        jButton2.setText("<");

        jButton3.setText("Stop");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(">");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText(">>");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(worldWindowGLCanvas1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(worldWindowGLCanvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Stop Button
        
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // Play Forward Button
        
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
            java.util.logging.Logger.getLogger(WorldWindUI_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorldWindUI_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorldWindUI_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorldWindUI_Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorldWindUI_Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private gov.nasa.worldwind.awt.WorldWindowGLCanvas worldWindowGLCanvas1;
    // End of variables declaration                   
}

