/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;
import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;

/**
 *
 * @author Akki
 */
public class WWHelper {
    private WWHelper() {
   }

   public static void setup(final WorldWindow ww) {
       Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
       ww.setModel(m);
   }
}
