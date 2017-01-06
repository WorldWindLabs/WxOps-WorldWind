/*
 * Copyright (C) 2016 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package com.wxops;

import gov.nasa.worldwind.ogc.kml.*;
import gov.nasa.worldwind.ogc.kml.impl.KMLTraversalContext;
import gov.nasa.worldwind.render.DrawContext;

/**
 * @author dcollins
 * @version $Id$
 */
public class WxOpsKMLPlacemark extends KMLPlacemark
{
    public WxOpsKMLPlacemark(String namespaceURI)
    {
        super(namespaceURI);
    }

    @Override
    protected boolean isFeatureActive(KMLTraversalContext tc, DrawContext dc)
    {
        KMLAbstractTimePrimitive timeElem = this.getTimePrimitive();
        if (timeElem instanceof WxOpsKMLTimeSpan && !((WxOpsKMLTimeSpan) timeElem).isActive(tc, dc))
        {
            return false; // TimeSpan is not active
        }

        return super.isFeatureActive(tc, dc);
    }
}
