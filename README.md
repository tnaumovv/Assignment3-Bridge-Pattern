# Assignment 3 | Bridge Pattern

- Student: [Name and surname to be provided]
- Group: [Group to be provided]
- Topic: A - Drawing
- Repository: [GitHub repository URL to be provided]
- Base commit: This is the initial I1/I2 version. Its hash will be recorded after committing it.

## Role map

| Role | Class | Source |
| --- | --- | --- |
| Abstraction | Shape | src/Shape.java |
| A1 | Circle | src/Circle.java |
| A2 | Square | src/Square.java |
| Implementor | Renderer | src/Renderer.java |
| I1 | VectorRenderer | src/VectorRenderer.java |
| I2 | RasterRenderer | src/RasterRenderer.java |
| Client | Main | src/Main.java |

The two independent dimensions are the shape and the rendering method.
`Shape` stores `private Renderer implementation`, which is supplied through its
constructor. `Circle.execute()` calls `renderCircle(radius)` through that reference;
`Square.execute()` calls `renderSquare(side)`. Neither shape knows a concrete renderer.
`Shape.setImplementation(Renderer)` changes the renderer on an existing shape.
`Main.checkRuntimeSwitch()` checks identity with `original == switched`, preserved
ID and radius, and both actual rendering results.

## Build and run

Use JDK 17. From the project root:

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

Only JDK classes are used. The program does not need input, an IDE, or dependencies.
Rendering is simulated with strings.

## Base demo expectations

| Check | Expected outcome |
| --- | --- |
| T1 | VECTOR circle radius=2 |
| T2 | RASTER circle radius=2 |
| T3 | VECTOR square side=3 |
| T4 | RASTER square side=3 |
| T5 | Same Circle object; unchanged ID circle-switch and radius 2; VECTOR circle radius=2 before the switch; RASTER circle radius=2 after it |

PASS/FAIL is calculated from actual results. The summary is `SUMMARY: 5/5 PASS`
when all checks succeed. Failed checks show the expected result. All checks run
before the program exits with code 1 if any check fails.
