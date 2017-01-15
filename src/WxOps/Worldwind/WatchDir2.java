/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;

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
 * @author wxazygy, updated 14 Jan 2017
 * adding getPOV() function
 */
public class WatchDir2 {

    private WatchService watcher;
    private Map<WatchKey, Path> keys;
    private boolean recursive;
    private boolean trace = false;
    private String logFile = "";
    private long startTime;
    private long endTime;
    public String cmdarg;
    private CameraControlWindow ccw2;

    @SuppressWarnings("unchecked")
    <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    // Creates a WatchService and registers the given directory
    WatchDir2(Path dir, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        this.recursive = recursive;
        if (recursive) {
            //System.out.format("Scanning %s ...\n", dir);
            registerAll(dir);
            //System.out.println("Done.");
        } else {
            register(dir);
        }
        // enable trace after initial registration
        this.trace = true;
    }

        // Register the given directory, and all its sub-directories, with the
    // WatchService.
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    // Register the given directory with the WatchService
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                //System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    //System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    public CameraControlWindow getCamUI() {
        return this.ccw2;
    }

    public void setCamUI(CameraControlWindow camUI) {
        this.ccw2 = camUI;
    }

    //Process all events for keys queued to the watcher
    public void processEvents() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                doProcessEvents();
            }
        });

        t.setDaemon(true);
        t.start();
    }

    void doProcessEvents() {
        for (;;) {
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();

            } catch (InterruptedException x) {
                return;
            }
            Path dir = keys.get(key);
            if (dir == null) {
                //System.err.println("WatchKey not recognized!!");
                continue;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);

                if (kind == ENTRY_CREATE) {
                    System.out.format("%s: %s\n", "** ", child);
                    // The name of the file to open.
                    String fileName = child.toString();
                    if (fileName.contains("cmd.txt")) {
                        logFile = "c:/test1/log.txt";  //hardcoded for now
                        startTime = System.nanoTime();  //start Timer
                        readCmdArgs(fileName);

                        //jsetText();
                    }
                }
                
                if (kind == ENTRY_MODIFY) {
                    System.out.format("%s: %s\n", "** ", child);
                    // The name of the file to open.
                    String fileName = child.toString();
                    if (fileName.contains("cmd.txt")) {
                        logFile = "c:/test1/log.txt";  //hardcoded for now
                        startTime = System.nanoTime();  //start Timer
                        readCmdArgs(fileName);

                        //jsetText();
                    }
                }

                if (kind == ENTRY_DELETE) {
                    System.out.format("%s: %s\n", "** ", child);
                    endTime = System.nanoTime();
                    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                    System.out.println("deleted " + duration / 1000000 + " msec " + cmdarg);
                }

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

    public void readCmdArgs(String file1) {
        File inFile = new File(file1);
        if (!inFile.isFile()) {
            //System.out.println("Parameter is not an existing file");
            return;
        }
        try {
            //Construct the log file 
            //File logFile = new File(inFile.getAbsolutePath() + "log.txt");
            BufferedReader br = new BufferedReader(new FileReader(file1));
            PrintWriter pw = new PrintWriter(new FileWriter(logFile, true));
            String line;
            //Read from the original file and write to the new 
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                //process the cmd here
                processCmd(line);
                //cmdarg = line.substring(3);
                //save cmd to logFile
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                //System.out.println("Could not delete file");
                endTime = System.nanoTime();
                long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                System.out.println("not deleted " + duration / 1000000 + " msec " + cmdarg);
                return;
            }
            //Rename the new file to the filename the original file had.
            //if (!tempFile.renameTo(inFile))
            //    System.out.println("Could not rename file");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void processCmd(String cmd) {
        String[] args = cmd.split(",");
        if (args[0].indexOf("setpov") >= 0) {
            final String lon = args[1];
            final String lat = args[2];
            final String rng = args[3];
            final String roll = args[4];
            final String tilt = args[5];
            final String azi = args[6];
            final String FOV = args[7];
            final String spd = args[8];
            
            System.out.println(lon + "," + lat);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    getCamUI().jsetPOV(lon, lat, rng, roll, tilt, azi, FOV, spd);
                }
            });
        }
        
        if (args[0].indexOf("getpov") >= 0) {
            
            System.out.println("getpov heya");
            //get the current POV
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    getCamUI().jgetPOV();
                }
            });
                                  
        }
    }
   
}
