package sampler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Partitions a genome based on a well distributed set of suffixes.
 */
public class Sampler {
    private final SequenceReader sequenceReader;
    private final SubSequenceCollector subSequenceCollector;
    private final PeriodicSampler periodicSampler;
    private final ListWriter listWriter;

    public Sampler(SequenceReader sequenceReader, SubSequenceCollector subSequenceCollector, PeriodicSampler periodicSampler, ListWriter listWriter) {
        this.sequenceReader = sequenceReader;
        this.subSequenceCollector = subSequenceCollector;
        this.periodicSampler = periodicSampler;
        this.listWriter = listWriter;
    }

    public void createPartitions(Reader inputReader, Writer outputWriter, int numPartitions) {
        Sequence sequence = sequenceReader.read(inputReader);
        List<Sequence> samples = subSequenceCollector.collect(sequence);
        List<Sequence> partitions = periodicSampler.sample(samples, numPartitions);
        listWriter.write(partitions, outputWriter);
    }

    public static void validateInputs(String[] args) {
        if(args.length != 2) {
            throw new SamplerException("Invalid number of arguments provided to Sampler.");
        }

        if (!(new File(args[0])).exists()) {
            throw new SamplerException(String.format("Sequence input file [%s] does not exist.", args[0]));
        }

        try {
            Integer.parseInt(args[1]);
        } catch(NumberFormatException nfe) {
            throw new SamplerException(String.format("Number of splits must be an integer, but was [%s]", args[1]), nfe);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        validateInputs(args);

        Sampler sampler = new Sampler(new SequenceReader(), new SubSequenceCollector(), new PeriodicSampler(), new ListWriter());
        String inputFileName = args[0];
        String numberOfPartitions = args[1];
        sampler.createPartitions(new FileReader(inputFileName), new PrintWriter(System.out), Integer.parseInt(numberOfPartitions));
    }
}
