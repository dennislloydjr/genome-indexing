package sampler;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class ListWriterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String NEW_LINE = System.getProperty("line.separator");
    private Writer output;
    private ListWriter listWriter;

    @Before
    public void setup() {
        output = new StringWriter();
        listWriter = new ListWriter(output);
    }

    @Test
    public void emptyListProducesNoOutput() throws IOException {
        listWriter.write(new Vector<String>(0));
        assertEquals("", output.toString());
    }

    @Test
    public void writerFlushesWhenFinished() throws IOException {
        Writer mockWriter = mock(Writer.class);
        ListWriter listWriter = new ListWriter(mockWriter);

        listWriter.write(new Vector<String>(0));
        verify(mockWriter).flush();
    }

    @Test
    public void exceptionsWrapped() throws IOException {
        Writer mockWriter = mock(Writer.class);
        ListWriter listWriter = new ListWriter(mockWriter);

        String exceptionMessage = "IOException message";
        doThrow(new IOException(exceptionMessage)).when(mockWriter).flush();

        thrown.expect(SamplerException.class);
        thrown.expectMessage(exceptionMessage);
        listWriter.write(new Vector<String>(0));
    }

    @Test
    public void listElementTerminatedWithNewLine() throws IOException {
        List list = new Vector<String>(1);
        String elementValue = "hello";
        list.add(elementValue);

        listWriter.write(list);
        assertEquals(elementValue + NEW_LINE, output.toString());
    }

    @Test
    public void eachElementWritten() throws IOException {
        List list = new Vector<String>(1);
        list.add("line1");
        list.add("line2");
        list.add("line3");

        listWriter.write(list);
        assertEquals("line1" + NEW_LINE + "line2" + NEW_LINE + "line3" + NEW_LINE, output.toString());
    }
}
