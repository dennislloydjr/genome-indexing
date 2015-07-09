package sampler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Vector;

import static org.mockito.Mockito.*;

public class SamplerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void partitionsCreatedFromInputAndWrittenToOutput() {
        SequenceReader sequenceReader = mock(SequenceReader.class);
        SubSequenceCollector subSequenceCollector = mock(SubSequenceCollector.class);
        PeriodicSampler periodicSampler = mock(PeriodicSampler.class);
        ListWriter listWriter = mock(ListWriter.class);

        Sampler sampler = new Sampler(sequenceReader, subSequenceCollector, periodicSampler, listWriter);
        Reader inputReader = new StringReader("abcdefghi");
        Writer outputWriter = new StringWriter();
        int numPartitions = 5;

        Sequence inputSequence = new Sequence("abcdefghi");
        List<Sequence> sequenceList = new Vector<>();
        sequenceList.add(new Sequence("abc"));
        sequenceList.add(new Sequence("def"));
        sequenceList.add(new Sequence("ghi"));

        List<Sequence> partitions = new Vector<>();
        partitions.add(new Sequence("abc"));
        partitions.add(new Sequence("ghi"));

        when(sequenceReader.read(inputReader)).thenReturn(inputSequence);
        when(subSequenceCollector.collect(inputSequence)).thenReturn(sequenceList);
        when(periodicSampler.sample(sequenceList, numPartitions)).thenReturn(partitions);

        sampler.createPartitions(inputReader, outputWriter, numPartitions);

        verify(listWriter).write(partitions, outputWriter);
    }

    @Test
    public void validateFailsIfLessThanTwoParametersPassed() {
        thrown.expect(SamplerException.class);
        Sampler.validateInputs(new String[] {});
    }

    @Test
    public void validateFailsIfMoreThanTwoParametersPassed() {
        thrown.expect(SamplerException.class);
        Sampler.validateInputs(new String[] {"", "", ""});
    }

    @Test
    public void firstArgumentMustBeFile() {
        thrown.expect(SamplerException.class);
        Sampler.validateInputs(new String[] {"does not exist", "1"});
    }

    @Test
    public void secondArgumentMustBeInteger() throws IOException {
        File file = File.createTempFile("sampler-unit-test", ".tmp");
        thrown.expect(SamplerException.class);
        Sampler.validateInputs(new String[] {file.getAbsolutePath(), "abc"});
    }
}
