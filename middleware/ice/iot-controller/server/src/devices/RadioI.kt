package devices

import IotController.*
import com.zeroc.Ice.Current
import devices.DeviceI

class RadioI(
    private val radioInfo: RadioInfo
): Radio, DeviceI(radioInfo) {

    @Synchronized
    override fun setFrequency(freq: Double, current: Current?) {
        if(radioInfo.state == DeviceState.OFF)
            throw InvalidOperationException("How do you want to set frequency on turned off radio?")
        if(freq < 89.0 || freq > 107.0)
            throw ArgumentException("Frequency should be between 89.0 and 107.0")
        radioInfo.frequency = freq
    }

    @Synchronized
    override fun setVolume(vol: Int, current: Current?) {
        if(radioInfo.state == DeviceState.OFF)
            throw InvalidOperationException("How do you want to set volume on turned off radio?")
        if(vol < 0 || vol > 100)
            throw ArgumentException("Volume should be between 0 and 100")
        radioInfo.volume = vol
    }
}