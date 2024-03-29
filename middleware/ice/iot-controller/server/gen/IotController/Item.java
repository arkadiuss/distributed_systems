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

public class Item implements java.lang.Cloneable,
                             java.io.Serializable
{
    public String name;

    public int quantity;

    public Item()
    {
        this.name = "";
    }

    public Item(String name, int quantity)
    {
        this.name = name;
        this.quantity = quantity;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Item r = null;
        if(rhs instanceof Item)
        {
            r = (Item)rhs;
        }

        if(r != null)
        {
            if(this.name != r.name)
            {
                if(this.name == null || r.name == null || !this.name.equals(r.name))
                {
                    return false;
                }
            }
            if(this.quantity != r.quantity)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::IotController::Item");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, name);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, quantity);
        return h_;
    }

    public Item clone()
    {
        Item c = null;
        try
        {
            c = (Item)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.name);
        ostr.writeInt(this.quantity);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.name = istr.readString();
        this.quantity = istr.readInt();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, Item v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public Item ice_read(com.zeroc.Ice.InputStream istr)
    {
        Item v = new Item();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<Item> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, Item v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<Item> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(Item.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final Item _nullMarshalValue = new Item();

    /** @hidden */
    public static final long serialVersionUID = -3498267094069855444L;
}
