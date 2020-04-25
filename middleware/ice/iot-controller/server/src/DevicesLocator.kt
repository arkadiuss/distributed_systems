import IotController.Device
import IotController.DeviceInfo
import IotController.DeviceState
import com.zeroc.Ice.Current
import com.zeroc.Ice.Object
import com.zeroc.Ice.ObjectNotFoundException
import com.zeroc.Ice.ServantLocator

class DevicesLocator : ServantLocator {
    private val devices = mutableMapOf<String, Device>()

    override fun finished(current: Current?, servant: Object?, cookie: Any?) = Unit

    override fun deactivate(p0: String?) {
        val keys = devices.keys
        keys.forEach {
            devices.remove(it)
        }
    }

    override fun locate(current: Current?): ServantLocator.LocateResult {
        val id = current?.id?.name ?: throw ObjectNotFoundException()
        devices.run {
            if(!containsKey(id)) {
                 put(id, createDevice(id))
            }
        }

        return ServantLocator.LocateResult(devices[id], null)
    }

    private fun createDevice(id: String): Device {
        return when(id) {
            "radio1" -> RadioI(DeviceInfo(0, "Living room radio", DeviceState.OFF))
            "radio2" -> RadioI(DeviceInfo(1, "Kitchen radio", DeviceState.OFF))
            "fridge1" -> FridgeI(DeviceInfo(2, "My favourite fridge", DeviceState.ON), 4.0)
            else -> throw ObjectNotFoundException()
        }
    }
}