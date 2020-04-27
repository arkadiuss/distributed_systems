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

public interface PtzCameraPrx extends CameraPrx
{
    default void zoomIn()
        throws ArgumentException,
               InvalidOperationException
    {
        zoomIn(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void zoomIn(java.util.Map<String, String> context)
        throws ArgumentException,
               InvalidOperationException
    {
        try
        {
            _iceI_zoomInAsync(context, true).waitForResponseOrUserEx();
        }
        catch(ArgumentException ex)
        {
            throw ex;
        }
        catch(InvalidOperationException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> zoomInAsync()
    {
        return _iceI_zoomInAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> zoomInAsync(java.util.Map<String, String> context)
    {
        return _iceI_zoomInAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_zoomInAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "zoomIn", null, sync, _iceE_zoomIn);
        f.invoke(true, context, null, null, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_zoomIn =
    {
        ArgumentException.class,
        InvalidOperationException.class
    };

    default void zoomOut()
        throws ArgumentException,
               InvalidOperationException
    {
        zoomOut(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void zoomOut(java.util.Map<String, String> context)
        throws ArgumentException,
               InvalidOperationException
    {
        try
        {
            _iceI_zoomOutAsync(context, true).waitForResponseOrUserEx();
        }
        catch(ArgumentException ex)
        {
            throw ex;
        }
        catch(InvalidOperationException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> zoomOutAsync()
    {
        return _iceI_zoomOutAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> zoomOutAsync(java.util.Map<String, String> context)
    {
        return _iceI_zoomOutAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_zoomOutAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "zoomOut", null, sync, _iceE_zoomOut);
        f.invoke(true, context, null, null, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_zoomOut =
    {
        ArgumentException.class,
        InvalidOperationException.class
    };

    default void tilt(double angle)
        throws ArgumentException,
               InvalidOperationException
    {
        tilt(angle, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void tilt(double angle, java.util.Map<String, String> context)
        throws ArgumentException,
               InvalidOperationException
    {
        try
        {
            _iceI_tiltAsync(angle, context, true).waitForResponseOrUserEx();
        }
        catch(ArgumentException ex)
        {
            throw ex;
        }
        catch(InvalidOperationException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> tiltAsync(double angle)
    {
        return _iceI_tiltAsync(angle, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> tiltAsync(double angle, java.util.Map<String, String> context)
    {
        return _iceI_tiltAsync(angle, context, false);
    }

    /**
     * @hidden
     * @param iceP_angle -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_tiltAsync(double iceP_angle, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "tilt", null, sync, _iceE_tilt);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeDouble(iceP_angle);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_tilt =
    {
        ArgumentException.class,
        InvalidOperationException.class
    };

    default void pan(double angle)
        throws ArgumentException,
               InvalidOperationException
    {
        pan(angle, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void pan(double angle, java.util.Map<String, String> context)
        throws ArgumentException,
               InvalidOperationException
    {
        try
        {
            _iceI_panAsync(angle, context, true).waitForResponseOrUserEx();
        }
        catch(ArgumentException ex)
        {
            throw ex;
        }
        catch(InvalidOperationException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> panAsync(double angle)
    {
        return _iceI_panAsync(angle, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> panAsync(double angle, java.util.Map<String, String> context)
    {
        return _iceI_panAsync(angle, context, false);
    }

    /**
     * @hidden
     * @param iceP_angle -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_panAsync(double iceP_angle, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "pan", null, sync, _iceE_pan);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeDouble(iceP_angle);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_pan =
    {
        ArgumentException.class,
        InvalidOperationException.class
    };

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static PtzCameraPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), PtzCameraPrx.class, _PtzCameraPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static PtzCameraPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), PtzCameraPrx.class, _PtzCameraPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static PtzCameraPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), PtzCameraPrx.class, _PtzCameraPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static PtzCameraPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), PtzCameraPrx.class, _PtzCameraPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static PtzCameraPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, PtzCameraPrx.class, _PtzCameraPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static PtzCameraPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, PtzCameraPrx.class, _PtzCameraPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default PtzCameraPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (PtzCameraPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default PtzCameraPrx ice_adapterId(String newAdapterId)
    {
        return (PtzCameraPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default PtzCameraPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (PtzCameraPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default PtzCameraPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (PtzCameraPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default PtzCameraPrx ice_invocationTimeout(int newTimeout)
    {
        return (PtzCameraPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default PtzCameraPrx ice_connectionCached(boolean newCache)
    {
        return (PtzCameraPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default PtzCameraPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (PtzCameraPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default PtzCameraPrx ice_secure(boolean b)
    {
        return (PtzCameraPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default PtzCameraPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (PtzCameraPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default PtzCameraPrx ice_preferSecure(boolean b)
    {
        return (PtzCameraPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default PtzCameraPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (PtzCameraPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default PtzCameraPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (PtzCameraPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default PtzCameraPrx ice_collocationOptimized(boolean b)
    {
        return (PtzCameraPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default PtzCameraPrx ice_twoway()
    {
        return (PtzCameraPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default PtzCameraPrx ice_oneway()
    {
        return (PtzCameraPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default PtzCameraPrx ice_batchOneway()
    {
        return (PtzCameraPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default PtzCameraPrx ice_datagram()
    {
        return (PtzCameraPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default PtzCameraPrx ice_batchDatagram()
    {
        return (PtzCameraPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default PtzCameraPrx ice_compress(boolean co)
    {
        return (PtzCameraPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default PtzCameraPrx ice_timeout(int t)
    {
        return (PtzCameraPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default PtzCameraPrx ice_connectionId(String connectionId)
    {
        return (PtzCameraPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default PtzCameraPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (PtzCameraPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::IotController::PtzCamera";
    }
}