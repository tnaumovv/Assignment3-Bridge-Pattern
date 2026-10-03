public class TvDevice implements Device {
    @Override
    public String applySettings(boolean poweredOn, int volume) {
        return DeviceStatus.format("TV", poweredOn, volume);
    }
}
