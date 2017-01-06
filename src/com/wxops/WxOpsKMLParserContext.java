/*
 * Copyright (C) 2016 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package com.wxops;import gov.nasa.worldwind.ogc.kml.KMLParserContext;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;

/**
 * @author dcollins
 * @version $Id$
 */
public class WxOpsKMLParserContext extends KMLParserContext
{
    public WxOpsKMLParserContext(String defaultNamespace)
    {
        super(defaultNamespace);
    }

    public WxOpsKMLParserContext(XMLEventReader eventReader, String defaultNamespace)
    {
        super(eventReader, defaultNamespace);
    }

    public WxOpsKMLParserContext(WxOpsKMLParserContext ctx)
    {
        super(ctx);
    }

    protected void initializeParsers(String ns)
    {
        super.initializeParsers(ns);
        this.parsers.put(new QName(ns, "Placemark"), new WxOpsKMLPlacemark(ns));
        this.parsers.put(new QName(ns, "TimeSpan"), new WxOpsKMLTimeSpan(ns));
    }
}
