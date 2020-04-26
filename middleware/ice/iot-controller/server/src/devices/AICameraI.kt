package devices

import IotController.AICamera
import IotController.DeviceInfo
import com.zeroc.Ice.Current
import kotlin.random.Random

class AICameraI(
    deviceInfo: DeviceInfo
) : CameraI(deviceInfo), AICamera {
    override fun detectCats(current: Current?): Boolean {
        return Random.nextBoolean()
    }
}