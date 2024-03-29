//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `IotController.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package IotController;

public class PtzCameraInfo extends DeviceInfo
{
    public PtzCameraInfo()
    {
        super();
    }

    public PtzCameraInfo(int id, String name, DeviceState state, int zoom, double panAngle, double tiltAngle)
    {
        super(id, name, state);
        this.zoom = zoom;
        this.panAngle = panAngle;
        this.tiltAngle = tiltAngle;
    }

    public int zoom;

    public double panAngle;

    public double tiltAngle;

    public PtzCameraInfo clone()
    {
        return (PtzCameraInfo)super.clone();
    }

    public static String ice_staticId()
    {
        return "::IotController::PtzCameraInfo";
    }

    @Override
    public String ice_id()
    {
        return ice_staticId();
    }

    /** @hidden */
    public static final long serialVersionUID = 2614054173225664676L;

    /** @hidden */
    @Override
    protected void _iceWriteImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice(ice_staticId(), -1, false);
        ostr_.writeInt(zoom);
        ostr_.writeDouble(panAngle);
        ostr_.writeDouble(tiltAngle);
        ostr_.endSlice();
        super._iceWriteImpl(ostr_);
    }

    /** @hidden */
    @Override
    protected void _iceReadImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        zoom = istr_.readInt();
        panAngle = istr_.readDouble();
        tiltAngle = istr_.readDouble();
        istr_.endSlice();
        super._iceReadImpl(istr_);
    }
}
