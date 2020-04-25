import IotController.Device
import com.zeroc.Ice.Current

class DeviceI : Device {
    override fun getId(current: Current?): Int {
        return 543
    }
}