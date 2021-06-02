package mts.teta.resizer;

import picocli.CommandLine;

public class ConsoleAttributes {

    @CommandLine.Parameters(description = "input file name", paramLabel = "input-file")
    protected String inFile = null;

    @CommandLine. Option(names={"--resize"}, description = "resize the image", arity = "2", split = " ")
    protected Integer[] rsize = null; //rwidth, rheight;

    @CommandLine.Option(names={"--quality"}, description = "JPEG/PNG compression level")
    protected Integer value = null;

    @CommandLine.Option(names={"--crop"}, description = "cut out one rectangular area of the image", arity = "4", split = " ")
    protected Integer[] points = null; // cwidth, cheight, x, y;

    @CommandLine.Option(names={"--blur"}, description = "reduce image noise detail levels")
    protected Integer radius = null;

    @CommandLine.Option(names={"--format"}, description = "the image format type")
    protected String formatType = null;

    @CommandLine.Parameters(description = "output file name", paramLabel = "output-file")
    protected String outFile = null;

}
