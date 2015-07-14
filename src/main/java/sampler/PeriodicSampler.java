package sampler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Sorts a List and then chooses N evenly spaced samples from that List.
 */
public class PeriodicSampler {
    public static final Logger LOGGER = LoggerFactory.getLogger(PeriodicSampler.class);

    public <T extends Comparable<? super T>> List<T> sample(List<? extends T> elements, int numberOfSamples) {
        List<T> samples = new Vector<>(numberOfSamples);
        Collections.sort(elements);

        if (numberOfSamples > 0) {
            double periodicIncrement = ((double)elements.size()) / (numberOfSamples + 1);
            double startPosition = periodicIncrement;
            int endPosition = elements.size() - 1;

            LOGGER.debug(String.format("Retrieving %d samples from list of size %d; period increment is %.2f.",
                    numberOfSamples, elements.size(), periodicIncrement));

            for (double sampleIndex = startPosition; sampleIndex <= endPosition; sampleIndex += periodicIncrement) {
                int index = (int)Math.ceil(sampleIndex - 1);
                samples.add(elements.get(index));
            }
        } else {
            LOGGER.debug("Attempting to retrieve 0 samples from list");
        }

        return samples;
    }
}
