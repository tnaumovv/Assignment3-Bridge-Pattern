public class ProjectorDevice implements Device {
    @Override
    public String applySettings(boolean poweredOn, int volume) {
        return DeviceStatus.format("PROJECTOR", poweredOn, volume);
    }
}
