package sampler;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ListWriter {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private Writer output;

    public ListWriter(Writer output) {
        this.output = output;
    }

    public void write(List list) {
        try {
            for(Object element : list) {
                output.write(element.toString());
                output.write(NEW_LINE);
            }

            output.flush();
        } catch (IOException ioe) {
            throw new SamplerException(ioe);
        }
    }
}
