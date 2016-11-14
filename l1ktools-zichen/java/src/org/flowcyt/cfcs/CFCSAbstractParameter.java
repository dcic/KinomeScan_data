package org.flowcyt.cfcs;
// CFCSAbstractParameter.java

/* ------------------------------------------------------------------------- *\
This software and documentation are provided 'as is' and Tree Star, Inc., its
contractors and partners specifically disclaim all other warranties, expressed
or implied, including but not limited to implied warranties of merchantability
and fitness for a particular purpose, or during any particular date range.

By using this software, you are agreeing to these limits of liability, and to
hold Tree Star harmless for any information, accurate or erroneous, that might
be generated by the program.  This software is intended for research use only.

Christopher Lane <cdl@best.classes> for Tree Star  1/14/2002      Copyright 2002
\* ------------------------------------------------------------------------- */



public abstract class CFCSAbstractParameter implements Cloneable, CFCSErrorCodes
{
    protected String shortName = null;
    protected String fullName = null;
    protected String filter = null;
    protected String detector = null;

    protected int range = Integer.MIN_VALUE;

    protected double percent = Double.NaN;
    protected double voltage = Double.NaN;
    protected double decades = Double.NaN;
    protected double offset = Double.NaN;

    // --------------------------------------------------------------------

    /* friendly */
    static final boolean isSet(final String value)
    {
        return value != null;
    }

    /* friendly */
    static final boolean isSet(final double value)
    {
        return !Double.isNaN(value);
    }

    /* friendly */
    static final boolean isSet(final int value)
    {
        return value != Integer.MIN_VALUE;
    }

    // --------------------------------------------------------------------

    /* friendly */
    static final boolean isNotSet(final String value)
    {
        return value == null;
    }

    /* friendly */
    static final boolean isNotSet(final double value)
    {
        return Double.isNaN(value);
    }

    /* friendly */
    static final boolean isNotSet(final int value)
    {
        return value == Integer.MIN_VALUE;
    }

    // --------------------------------------------------------------------

    /* friendly */
    final boolean isEmpty(final String value)
    {
        return (isNotSet(value) || value.length() == 0);
    }

    // --------------------------------------------------------------------

    /* friendly */
    final CFCSAbstractParameter copy()
    {
        CFCSAbstractParameter duplicate = null;

        try
        {
            duplicate = (CFCSAbstractParameter) this.clone();
        }
        catch (CloneNotSupportedException exception)
        {
            throw new CFCSError(CFCSSystemError, exception);
        }

        return duplicate;
    }

    // $xnS ---------------------------------------------------------------

    public final String getFullName()
    {
        if (isNotSet(fullName))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "FullName");
        }

        return fullName;
    }

    public final void setFullName(final String fullName)
    {
        if (isEmpty(fullName))
        {
            throw new CFCSError(CFCSIllegalValue, "(empty string)");
        }
        /* else */ this.fullName = fullName;
    }

    // $xnN ---------------------------------------------------------------

    public final String getShortName()
    {
        if (isNotSet(shortName))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "ShortName");
        }

        return shortName;
    }

    public final void setShortName(final String shortName)
    {
        if (isEmpty(shortName))
        {
            throw new CFCSError(CFCSIllegalValue, "(empty string)");
        }
        /* else */ this.shortName = shortName;
    }

    // $xnR ---------------------------------------------------------------

    public final int getRange()
    {
        if (isNotSet(range))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "Range");
        }

        return range;
    }

    public final void setRange(final int range)
    {
        if (range < 2)
        {
            throw new CFCSError(CFCSIllegalValue, range);
        }
        /* else */ this.range = range;
    }

	//$xnF ---------------------------------------------------------------
    public final String getFilter()
    {
        if (isNotSet(filter))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "Filter");
        }

        return filter;
    }

    public final void setFilter(final String filter)
    {
        if (isEmpty(filter))
        {
            throw new CFCSError(CFCSIllegalValue, "(empty string)");
        }
        /* else */ this.filter = filter;
    }

	//$xnP ---------------------------------------------------------------
    public final double getEmittedPercent()
    {
        if (isNotSet(percent))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "EmittedPercent");
        }

        return percent;
    }
    public final void setEmittedPercent(final double percent)
    {
        if (percent < 0)
        {
            throw new CFCSError(CFCSIllegalValue, percent);
        }
        /* else */ this.percent = percent;
    }

	//$xnV ---------------------------------------------------------------
    public final double getVoltage()
    {
        if (isNotSet(voltage))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "Voltage");
        }

        return voltage;
    }

    public final void setVoltage(final double voltage)
    {
        if (voltage < 0)
        {
            throw new CFCSError(CFCSIllegalValue, voltage);
        }
        /* else */ this.voltage = voltage;
    }

	//$xnT ---------------------------------------------------------------
    public final String getDetectorType()
    {
        if (isNotSet(detector))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "DetectorType");
        }

        return detector;
    }

    public final void setDetectorType(final String detector)
    {
        if (isEmpty(detector))
        {
            throw new CFCSError(CFCSIllegalValue, "(empty string)");
        }
        /* else */ this.detector = detector;
    }

    // $xnE ---------------------------------------------------------------

    // Operates on the value corresponding to the first value of $PnE.

    public final double getLogDecades()
    {
        // JS, Feb 28, 2006
    	// It is sad, but people break FCS3.0 by omitting $PnE keywords when there 
    	// should be the "0,0". This helps to read such files. 
    	// Comment the next line out to handle those files according the standard (i.e., refuse reading them).
    	if (isNotSet(decades)) return 0;

    	if (isNotSet(decades))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "LogDecades");
        }

        return decades;
    }

    // Zero implies linear scaling, in which case the offset should be zero.

    public void setLogDecades(final double decades)
    {
        if (decades < 0.0)
        {
            throw new CFCSError(CFCSIllegalValue, decades);
        }
        else if (decades == 0.0 && isSet(offset) && offset != 0.0)
        {
            throw new CFCSError(CFCSInconsistentValue, "Offset");
        }
        /* else */ this.decades = decades;
    }

    // Operates on the value corresponding to the second value of $PnE.

    public final double getOffset()
    {
        if (isNotSet(offset))
        {
            throw new CFCSError(CFCSUndefinedAttribute, "Offset");
        }

        return offset;
    }

    public final void setOffset(final double offset)
    {
        if (offset < 0.0)
        {
            throw new CFCSError(CFCSIllegalValue, offset);
        }
        /* else */ this.offset = offset;
    }

    // $xnE (bis) ---------------------------------------------------------
    // API treats this as two float attributes but it's really one string

    public final String getLogDecadesAndOffset()
    {
        final StringBuffer buffer = new StringBuffer();

        double exact = getLogDecades();
        long integral = (new Double(exact)).longValue();
        if (integral == exact)
            buffer.append(integral); // 0.0 => 0, etc.
        else
            buffer.append(exact);

        buffer.append(CFCSSystem.VALUE_SEPARATOR_CHAR);

        exact = getOffset();
        integral = (new Double(exact)).longValue();
        if (integral == exact)
            buffer.append(integral);
        else
            buffer.append(exact);

        return buffer.toString();
    }

    public final void setLogDecadesAndOffset(final String string)
    {
        if (isEmpty(string))
        {
            throw new CFCSError(CFCSIllegalValue, "(empty string)");
        }

        int classesma = string.indexOf(CFCSSystem.VALUE_SEPARATOR_CHAR);

        if (classesma > 0)
        {
            try
            {
                setLogDecades((new Double(string.substring(0, classesma))).doubleValue());
                setOffset((new Double(string.substring(classesma + 1))).doubleValue());
            }
            catch (NumberFormatException exception)
            {
                throw new CFCSError(CFCSIllegalValue, exception);
            }
        }
        else
            throw new CFCSError(CFCSIllegalValue, string);
    }

    // --------------------------------------------------------------------

}
