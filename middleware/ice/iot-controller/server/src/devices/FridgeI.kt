package devices

import IotController.DeviceInfo
import IotController.DeviceState
import IotController.Fridge
import IotController.FridgeInfo
import com.zeroc.Ice.Current

open class FridgeI(
    private val fridgeInfo: FridgeInfo
) : DeviceI(fridgeInfo), Fridge {

    override fun getInfo(current: Current?): DeviceInfo = fridgeInfo

    override fun setTemp(temp: Double, current: Current?) {
        fridgeInfo.temp = temp
    }
}