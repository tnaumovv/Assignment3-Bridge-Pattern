public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !"--demo".equals(args[0])) {
            System.out.println("Usage: java -cp out Main --demo");
            return;
        }
        runDemo();
    }

    private static void runDemo() {
        int passed = 0;
        int total = 5;

        passed += checkResult("T1", "BasicRemote + TvDevice",
                new BasicRemote("basic-1", new TvDevice()),
                "TV power=ON volume=30");
        passed += checkResult("T2", "BasicRemote + RadioDevice",
                new BasicRemote("basic-1", new RadioDevice()),
                "RADIO power=ON volume=30");
        passed += checkResult("T3", "QuietRemote + TvDevice",
                new QuietRemote("quiet-1", new TvDevice()),
                "TV power=ON volume=5");
        passed += checkResult("T4", "QuietRemote + RadioDevice",
                new QuietRemote("quiet-1", new RadioDevice()),
                "RADIO power=ON volume=5");
        passed += checkRuntimeSwitch();

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
        if (passed != total) {
            System.exit(1);
        }
    }

    private static int checkResult(String testId, String classes,
                                   Remote remote, String expected) {
        String actual = remote.execute();
        boolean passed = expected.equals(actual);
        return printCheck(testId, passed, classes + " | result=" + actual,
                "result=" + expected);
    }

    private static int checkRuntimeSwitch() {
        BasicRemote original = new BasicRemote("remote-switch", new TvDevice());
        BasicRemote switched = original;
        String originalId = original.getId();
        int originalVolume = original.getVolumePreset();
        String before = original.execute();

        switched.setImplementation(new RadioDevice());
        String after = switched.execute();

        boolean sameObject = original == switched;
        boolean stateUnchanged = originalId.equals(switched.getId())
                && originalVolume == switched.getVolumePreset();
        boolean passed = sameObject && stateUnchanged
                && "TV power=ON volume=30".equals(before)
                && "RADIO power=ON volume=30".equals(after);

        String details = "BasicRemote + TvDevice -> RadioDevice"
                + "\n  sameObject=" + sameObject + " | stateUnchanged=" + stateUnchanged
                + "\n  id=" + originalId + " -> " + switched.getId()
                + " | volumePreset=" + originalVolume + " -> " + switched.getVolumePreset()
                + "\n  before=" + before + " | after=" + after;
        String expected = "sameObject=true | stateUnchanged=true"
                + " | id=remote-switch -> remote-switch | volumePreset=30 -> 30"
                + " | before=TV power=ON volume=30 | after=RADIO power=ON volume=30";
        return printCheck("T5", passed, details, expected);
    }

    private static int printCheck(String testId, boolean passed,
                                  String details, String expected) {
        String status = passed ? "PASS" : "FAIL";
        System.out.println(testId + " " + status + " | " + details);
        if (!passed) {
            System.out.println("  expected=" + expected);
        }
        return passed ? 1 : 0;
    }
}
