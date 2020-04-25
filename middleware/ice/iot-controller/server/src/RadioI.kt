import IotController.DeviceInfo
import IotController.DeviceState
import IotController.Radio
import com.zeroc.Ice.Current

class RadioI(
    private val deviceInfo: DeviceInfo,
    private var freguency: Double = 100.8,
    private var volume: Int = 50
): Radio {

    override fun getInfo(current: Current?): DeviceInfo = deviceInfo

    override fun setFrequency(freq: Double, current: Current?) {
        freguency = freq
    }

    override fun setVolume(vol: Int, current: Current?) {
        volume = vol
    }

    override fun turnOff(current: Current?) {
        deviceInfo.state = DeviceState.OFF
    }

    override fun turnOn(current: Current?) {
        deviceInfo.state = DeviceState.ON
    }

}