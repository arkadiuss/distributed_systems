module IotController {
    enum DeviceState {
        ON, OFF
    };

    class DeviceInfo {
        int id;
        string name;
        DeviceState state;
    };

    class RadioInfo extends DeviceInfo {
        double frequency;
        int volume;
    };

    class PtzCameraInfo extends DeviceInfo {
        int zoom;
        double panAngle;
        double tiltAngle;
    };

    class FridgeInfo extends DeviceInfo {
        double temp;
    };

    struct Item {
        string name;
        int quantity;
    };

    sequence<Item> Items;
    sequence<byte> Bytes;

    exception BaseException {
        string message;
    };

    exception ArgumentException extends BaseException {};
    exception InvalidOperationException extends BaseException {};

    interface Device {
        DeviceInfo getInfo();
        void turnOn();
        void turnOff();
    };

    interface Radio extends Device {
        void setVolume(int vol) throws ArgumentException, InvalidOperationException;
        void setFrequency(double freq) throws ArgumentException, InvalidOperationException;
    };

    interface Camera extends Device {
         Bytes takePicture();
    };

    interface PtzCamera extends Camera {
        void zoomIn() throws ArgumentException, InvalidOperationException;
        void zoomOut() throws ArgumentException, InvalidOperationException;
        void tilt(double angle) throws ArgumentException, InvalidOperationException;
        void pan(double angle) throws ArgumentException, InvalidOperationException;
    };

    interface AICamera extends Camera {
        bool detectCats();
    };

    interface Fridge extends Device {
        void setTemp(double temp) throws ArgumentException, InvalidOperationException;
    };

    interface SmartFridge extends Fridge {
        void addRecipe(string name, Items ingredients) throws InvalidOperationException;
        Items whatINeedFor(string name) throws ArgumentException;
        Items getCurrentItems() throws ArgumentException, InvalidOperationException;
    };
};