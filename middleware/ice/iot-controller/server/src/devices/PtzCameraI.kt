package devices

import IotController.ArgumentException
import IotController.PtzCamera
import IotController.PtzCameraInfo
import com.zeroc.Ice.Current
import kotlin.math.max
import kotlin.math.min

class PtzCameraI(
    private val ptzCameraInfo: PtzCameraInfo
): CameraI(ptzCameraInfo), PtzCamera {
    override fun zoomOut(current: Current?) {
        ptzCameraInfo.zoom = max(ptzCameraInfo.zoom-1,0)
    }

    override fun zoomIn(current: Current?) {
        ptzCameraInfo.zoom = min(ptzCameraInfo.zoom+1,5)
    }

    override fun tilt(angle: Double, current: Current?) {
        if(angle < 0 || angle > 15)
            throw ArgumentException("Tilt angle should be between 0 and 15")
        ptzCameraInfo.tiltAngle = angle
    }

    override fun pan(angle: Double, current: Current?) {
        if(angle < -180 || angle > 180)
            throw ArgumentException("Pan angle should be between -180 and 180")
        ptzCameraInfo.panAngle = angle
    }
}
