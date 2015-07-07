package sampler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Vector;

/**
 * Creates a list of sub-sequences from the input sequence. The sub-sequences are roughly evenly spaced throughout
 * input sequence (by the stepSize). They include at least a minimum number of characters and a minimum number of
 * unique characters.
 */
public class SubSequenceCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubSequenceCollector.class);

    private final int stepSize;
    private final int minimumCharacters;
    private final int minimumUniqueCharacters;

    public SubSequenceCollector(int stepSize, int minimumCharacters, int minimumUniqueCharacters) {
        this.stepSize = stepSize;
        this.minimumCharacters = minimumCharacters;
        this.minimumUniqueCharacters = minimumUniqueCharacters;
    }

    public List<Sequence> collect(Sequence input) {
        String sequence = input.getSequence();
        Vector<Sequence> results = new Vector<>(0);

        int sequenceSize = sequence.length();
        int initialPosition = Math.max(1, Math.min(sequenceSize, stepSize)) - 1;
        int lastPosition = 1 + sequence.length() - minimumCharacters;

        StringBuilder subSequenceBuilder = new StringBuilder();

        LOGGER.debug(String.format("Getting sub-sequences for sequence of length %d, step size %d, min chars %d, min unique chars %d", sequence.length(), stepSize, 0, 0));

        int position = initialPosition;
        while(position < lastPosition) {
            int subSequencePosition = position;
            int uniqueCharCount = 0;
            int charCount = 0;
            char previousChar = '\0';

            boolean buildingSubSequence = true;
            while(buildingSubSequence && subSequencePosition < sequenceSize) {
                char currentChar = sequence.charAt(subSequencePosition);
                subSequenceBuilder.append(currentChar);

                charCount++;
                subSequencePosition++;
                if(previousChar != currentChar) {
                    uniqueCharCount++;
                }
                previousChar = currentChar;

                buildingSubSequence = (uniqueCharCount < minimumUniqueCharacters || charCount < minimumCharacters);
            }

            if(!buildingSubSequence) {
                results.add(new Sequence(subSequenceBuilder.toString()));
                subSequenceBuilder.setLength(0);
            }

            position += stepSize;
        }

        return results;
    }
}
