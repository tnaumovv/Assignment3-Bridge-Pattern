# Assignment 3 | Bridge Pattern

- Student: Timur Naumov
- Group: SE-2537
- Topic: D - Remote controls
- Repository: https://github.com/tnaumovv/Assignment3-Bridge-Pattern
- Base commit: this working I1/I2 version will be recorded after committing it.

| Role | Class | Source |
| --- | --- | --- |
| Abstraction | Remote | src/Remote.java |
| A1 | BasicRemote | src/BasicRemote.java |
| A2 | QuietRemote | src/QuietRemote.java |
| Implementor | Device | src/Device.java |
| I1 | TvDevice | src/TvDevice.java |
| I2 | RadioDevice | src/RadioDevice.java |
| Client | Main | src/Main.java |
| Output helper | DeviceStatus | src/DeviceStatus.java |

Remote stores an interface-typed Device reference supplied through its constructor.
BasicRemote uses volume 30; QuietRemote uses volume 5. The shared execute() operation
powers on the selected device. setImplementation(Device) replaces that device.
Main.checkRuntimeSwitch() verifies reference equality, ID, volume preset, and results.
DeviceStatus.format() keeps the status format in one place.

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

| Check | Expected result |
| --- | --- |
| T1 | TV power=ON volume=30 |
| T2 | RADIO power=ON volume=30 |
| T3 | TV power=ON volume=5 |
| T4 | RADIO power=ON volume=5 |
| T5 | Same object; unchanged ID remote-switch and volume preset 30; TV before, RADIO after |

This base version runs T1-T5 and computes SUMMARY: 5/5 PASS.
