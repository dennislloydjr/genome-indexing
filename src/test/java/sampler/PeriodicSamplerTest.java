package sampler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class PeriodicSamplerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void zeroSamplesReturnedForEmptyList() {
        PeriodicSampler sampler = new PeriodicSampler();
        List<String> result = sampler.sample(new Vector<String>(0), 0);
        assertEquals(0, result.size());
    }

    @Test
    public void zeroSamplesReturnedForZeroSamplesRequested() {
        PeriodicSampler sampler = new PeriodicSampler();
        Vector<String> inputList = new Vector<>(1);
        inputList.add("1");

        List<String> result = sampler.sample(inputList, 0);
        assertEquals(0, result.size());
    }

    @Test
    public void zeroSamplesReturnedForOneSampleRequested() {
        PeriodicSampler sampler = new PeriodicSampler();
        Vector<String> inputList = new Vector<>(1);
        inputList.add("1");

        List<String> result = sampler.sample(inputList, 1);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0));
    }

    @Test
    public void returnsAllSamplesWhenAllSamplesRequested() {
        PeriodicSampler sampler = new PeriodicSampler();
        List<String> inputList = Arrays.asList("1", "2", "3", "4", "5");

        List<String> result = sampler.sample(inputList, 5);
        assertEquals(inputList, result);
    }

    @Test
    public void returnsEvenlyDistributedSamples() {
        PeriodicSampler sampler = new PeriodicSampler();
        List<String> inputList = Arrays.asList("1", "2", "3", "4", "5");

        List<String> result = sampler.sample(inputList, 2);
        List<String> expected = Arrays.asList("2", "4");
        assertEquals(expected, result);
    }

    @Test
    public void samplesAreReturnedFromSortedList() {
        PeriodicSampler sampler = new PeriodicSampler();
        List<String> inputList = Arrays.asList("1", "9", "8", "7", "3");

        List<String> result = sampler.sample(inputList, 2);
        List<String> expected = Arrays.asList("3", "8");
        assertEquals(expected, result);
    }
}
