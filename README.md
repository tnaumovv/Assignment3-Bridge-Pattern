# Assignment 3 - Bridge Pattern

- Student: Timur Naumov
- Group: SE-2537
- Topic: D - Remote controls
- Repository: [https://github.com/tnaumovv/Assignment3-Bridge-Pattern](https://github.com/tnaumovv/Assignment3-Bridge-Pattern)

## Role map

| Role | Class | Source |
| --- | --- | --- |
| Abstraction | Remote | [src/Remote.java](src/Remote.java) |
| A1 | BasicRemote | [src/BasicRemote.java](src/BasicRemote.java) |
| A2 | QuietRemote | [src/QuietRemote.java](src/QuietRemote.java) |
| Implementor | Device | [src/Device.java](src/Device.java) |
| I1 | TvDevice | [src/TvDevice.java](src/TvDevice.java) |
| I2 | RadioDevice | [src/RadioDevice.java](src/RadioDevice.java) |
| I3 | ProjectorDevice | [src/ProjectorDevice.java](src/ProjectorDevice.java) |
| Client | Main | [src/Main.java](src/Main.java) |
| Shared output helper | DeviceStatus | [src/DeviceStatus.java](src/DeviceStatus.java) |

| Requirement | Location |
| --- | --- |
| Interface-typed bridge | `Remote.java`: `private Device implementation` |
| Constructor injection | `Remote(String id, int volumePreset, Device implementation)` |
| Application operation | `Remote.execute()` delegates to `implementation.applySettings(true, volumePreset)` |
| BasicRemote behavior | Constructor calls `super(id, 30, implementation)` |
| QuietRemote behavior | Constructor calls `super(id, 5, implementation)` |
| Runtime replacement | `Remote.setImplementation(Device)` |
| Same-object and state proof | `Main.checkRuntimeSwitch()` (T5) |
| Result comparison | `Main.checkResult(...)` |
| Calculated PASS/FAIL output | `Main.printCheck(...)` |

Remote type and device type vary separately. Both remotes inherit the same execute()
workflow, but keep different volume presets. DeviceStatus formats the output once.
Remote does not depend on concrete devices, perform casts, or check device types.
The program is a local simulation; it returns device type, power state, and volume.

## Build and run

From the extracted project root, with JDK 17:

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

No input or additional dependencies are needed. Verified on JDK 17.0.20.1.

## Expected outcomes

| Check | Classes | Expected result |
| --- | --- | --- |
| T1 | BasicRemote + TvDevice | `TV power=ON volume=30` |
| T2 | BasicRemote + RadioDevice | `RADIO power=ON volume=30` |
| T3 | QuietRemote + TvDevice | `TV power=ON volume=5` |
| T4 | QuietRemote + RadioDevice | `RADIO power=ON volume=5` |
| T5 | BasicRemote + TvDevice -> RadioDevice | Same object (`original == switched`); ID stays `remote-switch`; volume preset stays 30; `TV power=ON volume=30` before; `RADIO power=ON volume=30` after |
| T6 | BasicRemote + ProjectorDevice | `PROJECTOR power=ON volume=30` |
| T7 | QuietRemote + ProjectorDevice | `PROJECTOR power=ON volume=5` |

T1/T2 use identical ID and preset data, as do T3/T4. Each check runs the application
and compares its result or state. T5 checks reference identity, not just equal IDs.
All checks run in order. A failure prints the expected value. The summary counts
successful comparisons and the process exits with code 1 if any check fails.
The successful summary is `SUMMARY: 7/7 PASS`. See [demo-output.txt](demo-output.txt).

## Extension evidence

The base commit has the working TV/Radio solution and T1-T5. ProjectorDevice was
added afterwards. Within src/, only ProjectorDevice.java and Main.java changed;
Remote, BasicRemote, QuietRemote, Device, TvDevice, RadioDevice, and DeviceStatus
are unchanged. sources.txt and documentation were also updated.

The source diff is [extension.diff](extension.diff). Reproduce it using:

```sh
git diff 544357acd1ba9ec025a578b9c9b8cb821a28c93a HEAD -- src > extension.diff
```
