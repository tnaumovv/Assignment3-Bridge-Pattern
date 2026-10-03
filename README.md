# Assignment 3 | Bridge Pattern

- Student: Timur Naumov
- Group: SE-2537
- Topic: A - Drawing
- Repository: [GitHub repository URL to be added before submission]
- Base commit: `70ec3caac1cd89fe2fc0d414b693353eadc0b00b`

## Role map

| Role | Class | Source |
| --- | --- | --- |
| Abstraction | Shape | [src/Shape.java](src/Shape.java) |
| A1 | Circle | [src/Circle.java](src/Circle.java) |
| A2 | Square | [src/Square.java](src/Square.java) |
| Implementor | Renderer | [src/Renderer.java](src/Renderer.java) |
| I1 | VectorRenderer | [src/VectorRenderer.java](src/VectorRenderer.java) |
| I2 | RasterRenderer | [src/RasterRenderer.java](src/RasterRenderer.java) |
| I3 | AsciiRenderer | [src/AsciiRenderer.java](src/AsciiRenderer.java) |
| Client | Main | [src/Main.java](src/Main.java) |

## Bridge and method locations

The independent dimensions are **which shape is drawn** and **how it is rendered**.
The demo uses a circle with radius 2 and a square with side 3. Shapes retain their
ID and dimension, while renderers create the output string.

| Item | Location | Purpose |
| --- | --- | --- |
| Bridge field | `Shape.java`, `private Renderer implementation` | Holds the renderer through the interface |
| Constructor injection | `Shape(String id, Renderer implementation)` | Receives the implementation from the client |
| Main operation | `Shape.execute()` | Public abstract operation returning a string |
| Circle behavior | `Circle.execute()` | Delegates to `getImplementation().renderCircle(radius)` |
| Square behavior | `Square.execute()` | Delegates to `getImplementation().renderSquare(side)` |
| Runtime replacement | `Shape.setImplementation(Renderer)` | Changes the renderer reference on an existing shape |
| T5 identity/state check | `Main.checkRuntimeSwitch()` | Compares `original == switched`, ID, radius, and both results |
| Shared demo check | `Main.checkResult(...)` | Executes a shape and compares its result with the expected string |
| Shared output | `Main.printCheck(...)` | Prints the calculated PASS/FAIL and returns 1 or 0 for the summary |

`Shape`, `Circle`, and `Square` do not construct, cast, or check concrete renderers.
There is no separate subclass for each shape/renderer combination.

## Build and run

Use JDK 17. Run these exact commands from the project root:

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

Only JDK classes are used. Rendering is a local string simulation. The demo needs
no input, IDE, libraries, network, database, or hardware.

Verified by compiling and running both the base and final versions on
Eclipse Temurin JDK 17.0.20.1. Temporary fault checks confirmed that wrong raster
output produces T2/T4/T5 FAIL with expected values and `SUMMARY: 4/7 PASS`, and an
ineffective setter makes T5 fail. Both runs execute all seven checks and exit with
code 1. Changed sample dimensions (radius 5 and side 7) were also verified.

## Expected results

T1/T2 use the same circle ID and radius; T3/T4 use the same square ID and side.
Each check calls the application and compares the actual result or state.

| Check | Participating classes | Expected result or state |
| --- | --- | --- |
| T1 | Circle + VectorRenderer | `VECTOR circle radius=2` |
| T2 | Circle + RasterRenderer | `RASTER circle radius=2` |
| T3 | Square + VectorRenderer | `VECTOR square side=3` |
| T4 | Square + RasterRenderer | `RASTER square side=3` |
| T5 | Circle + VectorRenderer -> RasterRenderer | `original == switched` is true; ID remains `circle-switch`; radius remains 2; before: `VECTOR circle radius=2`; after: `RASTER circle radius=2` |
| T6 | Circle + AsciiRenderer | `ASCII circle radius=2` |
| T7 | Square + AsciiRenderer | `ASCII square side=3` |

All checks run in order. `printCheck` adds 1 only when a comparison succeeds.
The expected successful summary is `SUMMARY: 7/7 PASS`. A failed check prints
the expected value, and the program exits with code 1 after running every check.
The actual captured run is in [demo-output.txt](demo-output.txt).

## Runtime switching

T5 creates one `Circle` and retains its reference as `original`. The variable
`switched` points to that same object. Before the replacement, the test saves
the ID, radius, and actual vector result. It calls
`switched.setImplementation(new RasterRenderer())` and executes the same circle
again. The PASS condition requires reference equality, preserved ID and radius,
and both correct results. It does not infer identity from matching IDs.

## Independent extension

The base commit contains the working two-by-two solution and T5, with a computed
`SUMMARY: 5/5 PASS`. After that commit, `AsciiRenderer` was added and `Main` was
updated to run T6/T7 and count seven checks. These are the only Java source changes.
`sources.txt` and documentation were also updated.

`Shape`, `Circle`, `Square`, `Renderer`, `VectorRenderer`, and `RasterRenderer`
remain unchanged from the base commit. The saved source diff is
[extension.diff](extension.diff). Reproduce it with:

```sh
git diff 70ec3caac1cd89fe2fc0d414b693353eadc0b00b HEAD -- src > extension.diff
```

## Design explanation and report draft

Bridge connects the shape hierarchy to the renderer hierarchy through composition.
A renderer can be added without changing existing shapes. A new shape can use
existing rendering styles, although this shape-specific `Renderer` interface would
need a new operation for a completely new kind of shape. This is one trade-off:
the contract is simple, but its shape operations must be maintained.

Adapter would translate an incompatible existing rendering API into the interface
the application expects. Here all renderers already implement `Renderer`; the aim
is to let shapes and rendering implementations vary independently.

The UML source is [diagram.puml](diagram.puml), and the rendered diagram is
[diagram.png](diagram.png). The English report content is in
[report-draft.txt](report-draft.txt), with the design explanation, five annotated
code excerpts, extension evidence, trade-off, and references. PDF layout is pending.
[defense-notes.txt](defense-notes.txt) contains a short preparation guide in Russian.

References used for the pattern explanation:

- Alan Shalloway and James R. Trott, *Design Patterns Explained*,
  [Introducing the Bridge Pattern](https://www.oreilly.com/library/view/design-patterns-explained/0201715945/0201715945_ch09lev1sec2.html).
- Alan Shalloway and James R. Trott, *Design Patterns Explained*,
  [Introducing the Adapter Pattern](https://www.oreilly.com/library/view/design-patterns-explained/0201715945/0201715945_ch07lev1sec2.html).

## Submission preparation

Before submitting, add the GitHub URL and finish `report.pdf`. Read and understand
the code and report for the individual defense. Obtain the exact submitted source
commit hash with `git rev-parse HEAD` and include it, with the repository URL, in
the report and Moodle submission text. A generated PDF can remain a submission
artifact outside Git so that it can identify that commit without a circular hash.

The final ZIP must be named `Assignment3_SE-2537_Naumov_Timur.zip`. Put these items
directly at its root: `src/`, `sources.txt`, `README.md`, `report.pdf`,
`demo-output.txt`, and `extension.diff`. Build it from the exact submitted source
version; omit `out/` and `.git/`. An optional UML image may also be included.
Do not submit the report draft in place of `report.pdf`.
