public class RadioDevice implements Device {
    @Override
    public String applySettings(boolean poweredOn, int volume) {
        return DeviceStatus.format("RADIO", poweredOn, volume);
    }
}
