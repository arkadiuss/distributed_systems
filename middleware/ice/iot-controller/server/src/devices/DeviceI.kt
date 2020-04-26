package devices

import IotController.Device
import IotController.DeviceInfo
import IotController.DeviceState
import com.zeroc.Ice.Current

abstract class DeviceI (
    private val deviceInfo: DeviceInfo
): Device {

    override fun getInfo(current: Current?): DeviceInfo = deviceInfo

    override fun turnOn(current: Current?) {
        deviceInfo.state = DeviceState.ON
    }

    override fun turnOff(current: Current?) {
        deviceInfo.state = DeviceState.OFF
    }
}