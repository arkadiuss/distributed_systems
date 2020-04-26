import IotController.*
import com.zeroc.Ice.Current
import com.zeroc.Ice.Object
import com.zeroc.Ice.ObjectNotFoundException
import com.zeroc.Ice.ServantLocator
import devices.*

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
            "radio1" -> RadioI(RadioInfo(0, "Living room radio", DeviceState.OFF, 97.6, 50))
            "radio2" -> RadioI(RadioInfo(1, "Kitchen radio", DeviceState.OFF, 97.6, 50))
            "fridge1" -> FridgeI(FridgeInfo(2, "My favourite fridge", DeviceState.ON, 4.0))
            "smartfridge1" -> SmartFridgeI(FridgeInfo(3, "I'm smart! Fridge", DeviceState.ON, 4.0),
                listOf(Item("egg", 2), Item("milk", 1)))
            "camera1" -> CameraI(DeviceInfo(4, "Garden camera", DeviceState.ON))
            "ptzcamera1" -> PtzCameraI(PtzCameraInfo(5, "Garden camera", DeviceState.ON, 1, 0.0, 0.0))
            "ptzcamera2" -> PtzCameraI(PtzCameraInfo(6, "Entrance camera", DeviceState.ON, 1, 0.0, 0.0))
            "aicamera1" -> AICameraI(DeviceInfo(7, "Entrance camera", DeviceState.ON))
            else -> throw ObjectNotFoundException()
        }
    }
}