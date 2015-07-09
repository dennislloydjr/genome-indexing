package sampler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

public class SequenceReaderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sequenceCreatedFromReader() throws Exception {
        StringReader input = new StringReader("abcdefg");
        SequenceReader reader = new SequenceReader();
        Sequence result = reader.read(input);
        assertEquals("abcdefg", result.getSequence());
    }

    @Test
    public void ioExceptionConvertedToSamplerException() throws Exception {
        Reader input = new StringReader("abcdefg");
        input.close();
        SequenceReader reader = new SequenceReader();

        thrown.expect(SamplerException.class);
        thrown.expectCause(instanceOf(IOException.class));
        reader.read(input);
    }
}
