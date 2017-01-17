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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

// @author pSubacz & vHaley, updated wxazygy 11 Jan 2017
// @author wxazygy update 15 Jan 2017 - AnimationWindowControl
// @author collins update 16 Jan 2017 - final ANI solution

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
        protected Date currentDate = new Date();  //ANI

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
        
        public Date getCurrentDate() {
            return this.currentDate;
        }
        
        public void setCurrentDate(Date date) {
            this.currentDate.setTime(date.getTime()); // copy the date's value to update layers holding a reference to currentDate
        }

        protected void makeMenu() // Main UI and Fuctions are located here.
        {
            // The following are UI components to be used later. Note: Need to fix naming stucture
            JMenuBar menuBar;
            //sts JMenu fileSubmenu, viewSubmenu;
            JMenuItem viewMenu, editMenu, helpMenu, openHelpItem, openFileMenuItem, openURLMenuItem, openCameraControlItem;
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
            // Open KML File
            // The Following section allows for the importing of KML files from any directory to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openFileMenuItem = new JMenuItem("Open KML File"); // This button opens a file selection tool to import a KML files
            openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
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
            fileMenu.add(openFileMenuItem);

            // Open KML URL #1
            // The Following section allows for the importing of KML files from a online URL address to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openURLMenuItem = new JMenuItem("Open URL1");
            openURLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
            openURLMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        String status = JOptionPane.showInputDialog(AppFrame.this, "URL", "http://wxops.com/demo/Dolly.kmz");
                        if (!WWUtil.isEmpty(status)) {
                            new WorldWindUI.WorkerThread(status.trim(), AppFrame.this).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            fileMenu.add(openURLMenuItem);

            // Open KML URL #2
            // The Following section allows for the importing of KML files from a online URL address to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openURLMenuItem = new JMenuItem("Open URL2");
            openURLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
            openURLMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        String status = JOptionPane.showInputDialog(AppFrame.this, "URL", "http://wxops.com/demo/WWE-11.kmz");
                        if (!WWUtil.isEmpty(status)) {
                            new WorldWindUI.WorkerThread(status.trim(), AppFrame.this).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            fileMenu.add(openURLMenuItem);

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
            openCameraControlItem = new JMenuItem("Camera Controls");
            openCameraControlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK)); // Sets a Hotkey to
            openCameraControlItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    boolean ccwRunning = false;
                    // show/hide the Camera Control Form
                    Frame[] frames = Frame.getFrames();
                    for (int i = 0; i < frames.length; i++) {
                        String title = frames[i].getTitle();
                        if (title == "POV") {
                            ccwRunning = true;
                            boolean vis = frames[i].isVisible();
                            vis = !vis;
                            frames[i].setVisible(vis);
                        }
                        //System.out.println(title + " = " + vis);
                    }

                    if (!ccwRunning) {
                        try {
                            CameraControlWindow ccw1 = new CameraControlWindow(getWwd());
                            ccw1.setVisible(true);
                            Path dir = Paths.get("c:/test1");
                            // spawn a separate thread
                            // *************
                            WatchDir2 watcher = new WatchDir2(dir, true);
                            watcher.setCamUI(ccw1);
                            watcher.processEvents();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            viewMenu.add(openCameraControlItem); // Adds the Set Camera Menu,
            
             // The following code creates a pop-up window that allows users to control the camera via typing input commands. The menu can be found in the file called AnimationControlWindow.java
            openCameraControlItem = new JMenuItem("Animation Controls");
            openCameraControlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK)); // Sets a Hotkey to
            openCameraControlItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    boolean aniRunning = false;
                    // show/hide the Camera Control Form
                    Frame[] frames = Frame.getFrames();
                    for (int i = 0; i < frames.length; i++) {
                        String title = frames[i].getTitle();
                        if (title == "ANI") {
                            aniRunning = true;
                            boolean vis = frames[i].isVisible();
                            vis = !vis;
                            frames[i].setVisible(vis);
                        }
                        //System.out.println(title + " = " + vis);
                    }
                    if (!aniRunning) {
                        try {
                            AnimationControlWindow ani1 = new AnimationControlWindow(AppFrame.this);
                            ani1.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            viewMenu.add(openCameraControlItem); // Adds the Set Camera Menu,           

//--------------Help-Menu------------------------------------
            helpMenu = new JMenu("Help");
            helpMenu.setMnemonic(KeyEvent.VK_H);
            menuBar.add(helpMenu);
            // The following code creates a pop-up window that allows users to control the camera via typing input commands. 
            openHelpItem = new JMenuItem("Help");
            openCameraControlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
            helpMenu.add(openHelpItem); // Adds the Help function
            
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
            //sts - defeat this feature, time under user control
            //after reading KML, attempt to set the Valid Time in AnimationControl

            layer.setValue(WxOpsConstants.DATE, this.currentDate); // kml layers hold a reference to currentDate, which is updated in place
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

    public static void main(String[] args) {
        final AppFrame af = (AppFrame) start("WxOps Inc. WorldWind Earth 0.6.2", AppFrame.class);
        //09 Jan 2017 version 0.6.1
        //15 Jan 2017 version 0.6.2 (SBIR Phase 1 Deliverable)
    }

    // Variables declaration - do not modify
    protected static ViewControlsLayer viewControlsLayer;
    private static WorldWindow wwd;

}
