package sampler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Reads the given input into a Sequence.
 */
public class SequenceReader {
    public Sequence read(Reader input) {

        try {
            BufferedReader bufferedReader = new BufferedReader(input);
            String sequenceString = bufferedReader.readLine();
            return new Sequence(sequenceString);
        } catch (IOException ioe) {
            throw new SamplerException("An error occurred while reading the sequence.", ioe);
        }
    }
}
