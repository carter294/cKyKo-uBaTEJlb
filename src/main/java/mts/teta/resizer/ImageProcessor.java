package mts.teta.resizer;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;
import net.coobird.thumbnailator.Thumbnails;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    public static void processImage(ResizerApp r) throws IOException {
        MarvinImage m = MarvinImageIO.loadImage(r.inputFile.getAbsolutePath());
        if (r.points != null) {
            crop(r, m);
            r.setInputFile(new File(r.outputFile.getAbsolutePath()));
        }
        if (r.radius != null && r.radius > 0 && r.radius < 100) {
            blur(r, m);
            r.setInputFile(new File(r.outputFile.getAbsolutePath()));
        }
        if (r.w != null && r.h != null) {
            resize(r, m);
            r.setInputFile(new File(r.outputFile.getAbsolutePath()));
        }
        MarvinImageIO.saveImage(m, r.outputFile.getAbsolutePath());

        if (r.q != null && r.q != 0) {
            changeQuality(r);
            r.setInputFile(new File(r.outputFile.getAbsolutePath()));
        }

        if (r.formatType != null)
            changeExtension(r);

    }

    private static void crop(ResizerApp r, MarvinImage m) {
        MarvinPluginCollection.crop(m.clone(), m, r.points[2], r.points[3], r.points[0], r.points[1]);
    }

    private static void blur(ResizerApp r, MarvinImage m) {
        MarvinPluginCollection.gaussianBlur(m.clone(), m, r.radius);
    }

    private static void resize(ResizerApp r, MarvinImage m) {
        MarvinPluginCollection.scale(m.clone(), m, r.w, r.h);
    }

    private static void changeQuality(ResizerApp r) throws IOException {
        Thumbnails.of(r.inputFile).scale(1).outputQuality(r.q / 100.0).toFile(r.outputFile);
    }

    private static void changeExtension(ResizerApp r) throws IOException {
        Thumbnails.of(r.outputFile).scale(1).outputFormat(r.formatType).toFile(r.outputFile);
    }
}
