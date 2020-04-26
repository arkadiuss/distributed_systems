package devices

import IotController.Camera
import IotController.DeviceInfo
import com.zeroc.Ice.Current
import kotlin.random.Random

open class CameraI(
    deviceInfo: DeviceInfo
) : DeviceI(deviceInfo), Camera {
    override fun takePicture(current: Current?): ByteArray {
        return ByteArray(8*6).map { Random.nextInt(0, 255).toByte() }.toByteArray()
    }
}