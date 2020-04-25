module IotController {
    enum DeviceState {
        ON, OFF
    };

    struct DeviceInfo {
        int id;
        string name;
        DeviceState state;
    };

    exception ArgumentException {};

    interface Device {
        DeviceInfo getInfo();
        void turnOn();
        void turnOff();
    };

    interface Radio extends Device {
        void setVolume(int vol) throws ArgumentException;
        void setFrequency(double freq) throws ArgumentException;
    };

    interface Fridge extends Device {
        void setTemp(double temp) throws ArgumentException;
    };
};