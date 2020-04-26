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

public interface SmartFridge extends Fridge
{
    void addRecipe(String name, Item[] ingredients, com.zeroc.Ice.Current current);

    Item[] whatINeedFor(String name, com.zeroc.Ice.Current current)
        throws ArgumentException;

    Item[] getCurrentItems(com.zeroc.Ice.Current current)
        throws ArgumentException,
               InvalidOperationException;

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::IotController::Device",
        "::IotController::Fridge",
        "::IotController::SmartFridge"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::IotController::SmartFridge";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_addRecipe(SmartFridge obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_name;
        Item[] iceP_ingredients;
        iceP_name = istr.readString();
        iceP_ingredients = ItemsHelper.read(istr);
        inS.endReadParams();
        obj.addRecipe(iceP_name, iceP_ingredients, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_whatINeedFor(SmartFridge obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_name;
        iceP_name = istr.readString();
        inS.endReadParams();
        Item[] ret = obj.whatINeedFor(iceP_name, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ItemsHelper.write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getCurrentItems(SmartFridge obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        Item[] ret = obj.getCurrentItems(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ItemsHelper.write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "addRecipe",
        "getCurrentItems",
        "getInfo",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "setTemp",
        "turnOff",
        "turnOn",
        "whatINeedFor"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_addRecipe(this, in, current);
            }
            case 1:
            {
                return _iceD_getCurrentItems(this, in, current);
            }
            case 2:
            {
                return Device._iceD_getInfo(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 7:
            {
                return Fridge._iceD_setTemp(this, in, current);
            }
            case 8:
            {
                return Device._iceD_turnOff(this, in, current);
            }
            case 9:
            {
                return Device._iceD_turnOn(this, in, current);
            }
            case 10:
            {
                return _iceD_whatINeedFor(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
