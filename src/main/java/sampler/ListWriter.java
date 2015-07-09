package sampler;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ListWriter {
    private static final String NEW_LINE = System.getProperty("line.separator");

    public void write(List list, Writer output) {
        try {
            for (Object element : list) {
                output.write(element.toString());
                output.write(NEW_LINE);
            }

            output.flush();
        } catch (IOException ioe) {
            throw new SamplerException("An exception occurred while writing the output.", ioe);
        }
    }
}
