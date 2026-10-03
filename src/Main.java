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
        int total = 7;

        passed += checkResult("T1", "Circle + VectorRenderer",
                new Circle("circle-1", 2, new VectorRenderer()),
                "VECTOR circle radius=2");
        passed += checkResult("T2", "Circle + RasterRenderer",
                new Circle("circle-1", 2, new RasterRenderer()),
                "RASTER circle radius=2");
        passed += checkResult("T3", "Square + VectorRenderer",
                new Square("square-1", 3, new VectorRenderer()),
                "VECTOR square side=3");
        passed += checkResult("T4", "Square + RasterRenderer",
                new Square("square-1", 3, new RasterRenderer()),
                "RASTER square side=3");
        passed += checkRuntimeSwitch();
        passed += checkResult("T6", "Circle + AsciiRenderer",
                new Circle("circle-1", 2, new AsciiRenderer()),
                "ASCII circle radius=2");
        passed += checkResult("T7", "Square + AsciiRenderer",
                new Square("square-1", 3, new AsciiRenderer()),
                "ASCII square side=3");

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
        if (passed != total) {
            System.exit(1);
        }
    }

    private static int checkResult(String testId, String classes,
                                   Shape shape, String expected) {
        String actual = shape.execute();
        boolean passed = expected.equals(actual);
        return printCheck(testId, passed, classes + " | result=" + actual,
                "result=" + expected);
    }

    private static int checkRuntimeSwitch() {
        Circle original = new Circle("circle-switch", 2, new VectorRenderer());
        Circle switched = original;
        String originalId = original.getId();
        int originalRadius = original.getRadius();
        String before = original.execute();

        switched.setImplementation(new RasterRenderer());
        String after = switched.execute();

        boolean sameObject = original == switched;
        boolean stateUnchanged = originalId.equals(switched.getId())
                && originalRadius == switched.getRadius();
        boolean passed = sameObject && stateUnchanged
                && "VECTOR circle radius=2".equals(before)
                && "RASTER circle radius=2".equals(after);

        String details = "Circle + VectorRenderer -> RasterRenderer"
                + " | sameObject=" + sameObject
                + " | stateUnchanged=" + stateUnchanged
                + " | id=" + originalId + " -> " + switched.getId()
                + " | radius=" + originalRadius + " -> " + switched.getRadius()
                + " | before=" + before + " | after=" + after;
        String expected = "sameObject=true | stateUnchanged=true"
                + " | id=circle-switch -> circle-switch | radius=2 -> 2"
                + " | before=VECTOR circle radius=2"
                + " | after=RASTER circle radius=2";
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
