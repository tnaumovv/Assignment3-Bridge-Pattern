public class DeviceStatus {
    public static String format(String type, boolean poweredOn, int volume) {
        String power = poweredOn ? "ON" : "OFF";
        return type + " power=" + power + " volume=" + volume;
    }
}
