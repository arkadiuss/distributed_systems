package devices

import IotController.Device
import IotController.DeviceInfo
import IotController.DeviceState
import com.zeroc.Ice.Current

abstract class DeviceI (
    private val deviceInfo: DeviceInfo
): Device {

    @Synchronized
    override fun getInfo(current: Current?): DeviceInfo = deviceInfo

    @Synchronized
    override fun turnOn(current: Current?) {
        deviceInfo.state = DeviceState.ON
    }

    @Synchronized
    override fun turnOff(current: Current?) {
        deviceInfo.state = DeviceState.OFF
    }
}