import IotController.DeviceInfo
import IotController.DeviceState
import IotController.Fridge
import com.zeroc.Ice.Current

class FridgeI(
    private val deviceInfo: DeviceInfo,
    private var temp: Double
) : Fridge {

    override fun getInfo(current: Current?): DeviceInfo = deviceInfo

    override fun turnOn(current: Current?) {
        deviceInfo.state = DeviceState.ON
    }

    override fun turnOff(current: Current?) {
        deviceInfo.state = DeviceState.OFF
    }

    override fun setTemp(temp: Double, current: Current?) {
        this.temp = temp
    }
}