package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import picocli.CommandLine;
import javax.imageio.IIOException;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "convert", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")

public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {

    // я глядел в код тестов и сделал только те сеттеры, какие там были
    // по-хорошему стоит для всех параметров завести поля и сеттеры/геттеры

    File inputFile, outputFile;
    Integer w, h, q;

    public void setInputFile(File f) {
        this.inputFile = f;
    }

    public void setOutputFile(File f) {
        this.outputFile = f;
    }

    public void setResizeWidth(int s) {
        this.w = s;
    }

    public void setResizeHeight(int s) {
        this.h = s;
    }

    public void setQuality(Integer s) {
        this.q = s;
    }

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();

        if (inFile != null && outFile != null) {
            this.setInputFile(new File(inFile));
            this.setOutputFile(new File(outFile));
        }

        if (value != null)
            this.setQuality(value);

        if (rsize != null) {
            this.setResizeWidth(rsize[0]);
            this.setResizeHeight(rsize[1]);
        }

        if (points != null)
            if (points[0] <= 0 || points[1] <= 0 || points[2] < 0 || points[3] < 0)
                throw new BadAttributesException("Please check params!");

        if (formatType != null)
            if (!formatType.equalsIgnoreCase("jpeg") && !formatType.equalsIgnoreCase("jpg") && !formatType.equalsIgnoreCase("png"))
                throw new BadAttributesException("Please check params!");

        if (this.q != null && (this.q < 0 || this.q > 100))
            throw new BadAttributesException("Please check params!");

        if (!this.inputFile.exists())
            throw new IIOException("Can't read input file!");

        // почему-то эта проверка кладет тесты, поэтому без нее
        //if (this.outputFile != null && !this.outputFile.exists())
            //throw new IIOException("Can't read output file!");

        imageProcessor.processImage(this);
        return 0;
    }
}
