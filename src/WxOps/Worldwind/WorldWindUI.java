/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package WxOps.Worldwind;

import gov.nasa.worldwindx.examples.util.*;
import gov.nasa.worldwind.util.layertree.*;
import gov.nasa.worldwind.ogc.kml.*;
import gov.nasa.worldwind.util.*;
import static gov.nasa.worldwindx.examples.ApplicationTemplate.start;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.retrieve.RetrievalService;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwindx.examples.kml.KMLApplicationController;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwindx.examples.util.BalloonController;
import javax.xml.stream.XMLStreamException;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import java.io.IOException;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import com.wxops.*;
import gov.nasa.worldwind.util.xml.XMLEventParserContextFactory;
import java.util.Date;

// @author pSubacz & vHaley
public class WorldWindUI extends ApplicationTemplate {

    public static class AppFrame extends ApplicationTemplate.AppFrame {

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
        private WorldWindow wwd;
        protected Date begin = WxOpsKMLTimeSpan.parseTimeString("2008-07-23T18:00:00Z");
        protected Date end = WxOpsKMLTimeSpan.parseTimeString("2008-07-23T18:59:59.9Z");
        protected long delta = (end.getTime() - begin.getTime()) / 40; // divided by 8 due to hard coding timesteps.
        protected Date date = new Date(begin.getTime());
        protected Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTimerFired();
            }
        });

        public AppFrame() {
            super(true, false, false); // Leave as is unless a the side panel for Windlayers is needed.
            this.layerTree = new LayerTree(new Offset(20d, 160d, AVKey.PIXELS, AVKey.INSET_PIXELS));
            this.layerTree.getModel().refresh(this.getWwd().getModel().getLayers());
            this.hiddenLayer = new RenderableLayer();
            this.hiddenLayer.addRenderable(this.layerTree);
            this.getWwd().getModel().getLayers().add(this.hiddenLayer);
            this.hotSpotController = new HotSpotController(this.getWwd());
            this.kmlAppController = new KMLApplicationController(this.getWwd());
            this.balloonController = new BalloonController(this.getWwd()) {
                @Override
                protected void addDocumentLayer(KMLRoot document) {
                    addKMLLayer(document);
                }
            };
            this.kmlAppController.setBalloonController(balloonController);
            //Sets the dimensions to the program
            Dimension size = new Dimension(1400, 800);
            this.setPreferredSize(size);
            this.pack();
            WWUtil.alignComponent(null, this, AVKey.CENTER);
            this.makeMenu(); //Calls the makeMenu function for the UI components 
            WorldWind.getRetrievalService().setSSLExceptionListener(new RetrievalService.SSLExceptionListener() {
                public void onException(Throwable e, String path) {
                    System.out.println(path);
                    System.out.println(e);
                }
            });
        }

        protected void makeMenu() // Main UI and Fuctions are located here.    
        {
            // The following are UI components to be used later. Note: Need to fix naming stucture
            JMenuBar menuBar;
            JMenu fileSubmenu, viewSubmenu;
            JMenuItem viewMenu, editMenu, animationMenu, openFileMenuItem, openURLMenuItem, openCameraControlItem, playmenuItem;
            JRadioButtonMenuItem rbMenuItem;
            JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9;
            JTextField jTextField1, jTextField2, jTextField3, jTextField4, jTextField5, jTextField6, jTextField7, jTextField8, jTextField9;
            JCheckBoxMenuItem cbMenuItem;
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("KML/KMZ File", "kml", "kmz"));

//--------------FILE-Menu------------------------------------------
            menuBar = new JMenuBar(); // Creates the menubar to work off of.
            this.setJMenuBar(menuBar);
            JMenu fileMenu = new JMenu("File"); // Adds "File" to the menu bar.
            menuBar.add(fileMenu); // Adds the "File" section to the menu bar.
//-------------File-Submenu----------------------------------------
            // The Following Code creates a menu item that has seperate items.
            fileMenu.addSeparator();
            fileSubmenu = new JMenu("Open KML Data");
            fileSubmenu.setMnemonic(KeyEvent.VK_S); // Mnemonic allows for hotkeys
            // The Following section allows for the importing of KML files from any directory to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openFileMenuItem = new JMenuItem("Open File..."); // This button opens a file selection tool to import a KML files
            openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
            openFileMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        int status = fileChooser.showOpenDialog(AppFrame.this);
                        if (status == JFileChooser.APPROVE_OPTION) {
                            for (File file : fileChooser.getSelectedFiles()) {
                                new WorldWindUI.WorkerThread(file, AppFrame.this).start();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            fileSubmenu.add(openFileMenuItem);

            // The Following section allows for the importing of KML files from a online URL address to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openURLMenuItem = new JMenuItem("Open Url...");
            openURLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
            openURLMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        String status = JOptionPane.showInputDialog(AppFrame.this, "URL");
                        if (!WWUtil.isEmpty(status)) {
                            new WorldWindUI.WorkerThread(status.trim(), AppFrame.this).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            fileSubmenu.add(openURLMenuItem);
            fileMenu.add(fileSubmenu);

//--------------EDIT-Menu------------------------------------
            editMenu = new JMenu("Edit");
            editMenu.setMnemonic(KeyEvent.VK_E);
            editMenu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
            menuBar.add(editMenu);

//--------------VIEW-Menu------------------------------------     
            viewMenu = new JMenu("View");
            viewMenu.setMnemonic(KeyEvent.VK_V);
            menuBar.add(viewMenu);
            // The following code creates a pop-up window that allows users to control the camera via typing input commands. The menu can be found in the file called CameraControlWindow.java
            openCameraControlItem = new JMenuItem("Camera Controls Menu ");
            viewMenu.add(openCameraControlItem); // Adds the Set Camera Menu, 
            openCameraControlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK)); // Sets a Hotkey to 
            openCameraControlItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        new CameraControlWindow(getWwd()).setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//--------------Animation-Menu------------------------------------
            //The animation Menu allows a user to play animations found within the hardcoded timestamps.  
            animationMenu = new JMenu("Animation");
            animationMenu.setMnemonic(KeyEvent.VK_N);
            menuBar.add(animationMenu);
            //This function menu item allows the users to toggle animations loaded in KML Files. 
            playmenuItem = new JMenuItem("Play/Stop ");
            playmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
            animationMenu.add(playmenuItem); // Adds play/stop button, 
            playmenuItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        onPlayPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        protected void onPlayPressed() {    // When the item is selected the timer is stoped and started.
            if (timer.isRunning()) {
                timer.stop();
            } else {
                timer.start();
            }
        }

        protected void onTimerFired() {
            date.setTime(date.getTime() + delta);
            if (date.compareTo(end) > 0) {
                date.setTime(begin.getTime());
            }
        //    jTextArea1.setText(date.toString()); // print the sting a text file on screen. to know when dates/time
            getWwd().redraw();
            System.out.println(date);
        }

        protected void addKMLLayer(KMLRoot kmlRoot) {
            //KML Layers are individual files that are brought together via XML file. 
            KMLController kmlController = new KMLController(kmlRoot);
            RenderableLayer layer = new RenderableLayer();
            layer.setName((String) kmlRoot.getField(AVKey.DISPLAY_NAME));
            layer.addRenderable(kmlController);
            this.getWwd().getModel().getLayers().add(layer);
            KMLLayerTreeNode layerNode = new KMLLayerTreeNode(layer, kmlRoot);
            this.layerTree.getModel().addLayer(layerNode);
            this.layerTree.makeVisible(layerNode.getPath());
            layerNode.expandOpenContainers(this.layerTree);
            layerNode.addPropertyChangeListener(AVKey.RETRIEVAL_STATE_SUCCESSFUL, new PropertyChangeListener() {
                public void propertyChange(final PropertyChangeEvent event) {
                    if (event.getSource() instanceof KMLNetworkLinkTreeNode) {
                        // Manipulate the tree on the EDT.
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                ((KMLNetworkLinkTreeNode) event.getSource()).expandOpenContainers(layerTree);
                                getWwd().redraw();
                            }
                        });
                    }
                }
            });

            layer.setValue(WxOpsConstants.DATE, date);
        }
    }

    public static class WorkerThread extends Thread {

        protected Object kmlSource;
        protected AppFrame appFrame;
        public WorkerThread(Object kmlSource, AppFrame appFrame) {
            this.kmlSource = kmlSource;
            this.appFrame = appFrame;
        }

        public void run() {
            try {
                KMLRoot kmlRoot = this.parse();
                kmlRoot.setField(AVKey.DISPLAY_NAME, formName(this.kmlSource, kmlRoot));
                final KMLRoot finalKMLRoot = kmlRoot;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        appFrame.addKMLLayer(finalKMLRoot);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        protected KMLRoot parse() throws IOException, XMLStreamException {
            return KMLRoot.createAndParse(this.kmlSource);
        }
    }

    protected static String formName(Object kmlSource, KMLRoot kmlRoot) {
        KMLAbstractFeature rootFeature = kmlRoot.getFeature();
        if (rootFeature != null && !WWUtil.isEmpty(rootFeature.getName())) {
            return rootFeature.getName();
        }

        if (kmlSource instanceof File) {
            return ((File) kmlSource).getName();
        }

        if (kmlSource instanceof String && WWIO.makeURL((String) kmlSource) != null) {
            return WWIO.makeURL((String) kmlSource).getPath();
        }

        return "KML Layer";
    }
//    These are test buttons that are not implemented.  
//    private void animationPlayButton() {
//
//        aniplayButton = new JButton("Start/Stop Animation");
//        aniplayButton.setVerticalTextPosition(AbstractButton.CENTER);
//        aniplayButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//        aniplayButton.setMnemonic(KeyEvent.VK_D);
//        aniplayButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            }
//        });
//    }
//
//    private void animationStopButton() {
//
//        anistopButton = new JButton("Start/Stop Animation");
//        anistopButton.setVerticalTextPosition(AbstractButton.CENTER);
//        anistopButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//        anistopButton.setMnemonic(KeyEvent.VK_D);
//        anistopButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//
//            }
//        });
//    }

    public static void main(String[] args) {
        final AppFrame af = (AppFrame) start("WxOps Inc. WorldWind Earth 0.6.0", AppFrame.class);
    }
    
    // Variables declaration - do not modify   
    protected static ViewControlsLayer viewControlsLayer;
    private static WorldWindow wwd;
    protected JButton aniplayButton, anistopButton;
    Boolean animationFlag = false;

}
