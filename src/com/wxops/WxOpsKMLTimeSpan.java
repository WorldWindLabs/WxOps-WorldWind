/*
 * Copyright (C) 2016 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package com.wxops;

import gov.nasa.worldwind.ogc.kml.KMLTimeSpan;
import gov.nasa.worldwind.ogc.kml.impl.KMLTraversalContext;
import gov.nasa.worldwind.render.DrawContext;

import java.text.*;
import java.util.*;

/**
 * @author dcollins
 * @version $Id$
 */
public class WxOpsKMLTimeSpan extends KMLTimeSpan
{
    protected Date beginDate;

    protected Date endDate;

    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    protected static SimpleDateFormat dateFormatWithMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");

    static
    {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormatWithMillis.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public WxOpsKMLTimeSpan(String namespaceURI)
    {
        super(namespaceURI);
    }

    public Date getBeginDate()
    {
        if (this.beginDate == null)
        {
            if (this.getBegin() != null)
            {
                this.beginDate = parseTimeString(this.getBegin());
            }
        }

        return this.beginDate;
    }

    public Date getEndDate()
    {
        if (this.endDate == null)
        {
            if (this.getEnd() != null)
            {
                this.endDate = parseTimeString(this.getEnd());
            }
        }

        return this.endDate;
    }

    public boolean isActive(KMLTraversalContext tc, DrawContext dc)
    {
        Date currentDate = (Date) dc.getCurrentLayer().getValue(WxOpsConstants.DATE);
        if (currentDate != null && this.getBeginDate() != null && this.getBeginDate().compareTo(currentDate) > 0)
        {
            return false; // current date is before the begin date
        }
        else if (currentDate != null && this.getEndDate() != null && this.getEndDate().compareTo(currentDate) < 0)
        {
            return false; // current date is after the end date
        }
        else
        {
            return true; // current date unspecified, or current date is within the time span (inclusive)
        }
    }

    public static Date parseTimeString(String timeString)
    {
        try
        {
            return dateFormat.parse(timeString);
        }
        catch (ParseException e)
        {
        }

        try
        {
            return dateFormatWithMillis.parse(timeString);
        }
        catch (ParseException e)
        {
        }

        return null;
    }
}
