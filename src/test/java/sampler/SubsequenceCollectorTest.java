package sampler;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubSequenceCollectorTest {
    @Test
    public void noResultsReturnedFromEmptySequence() {
        Sequence input = new Sequence("");
        SubSequenceCollector collector = new SubSequenceCollector(1, 1, 1);
        List<Sequence> results = collector.collect(input);
        assertEquals(0, results.size());
    }

    @Test
    public void lastSubSequenceReturnedIfStepSizeIsSequenceSize() {
        Sequence input = new Sequence("abc");
        SubSequenceCollector collector = new SubSequenceCollector(3, 1, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("c"));
        assertEquals(expected, results);
    }

    @Test
    public void evenlySpacedSubSequencesReturned() {
        Sequence input = new Sequence("1234567890");
        int stepSize = 3;
        SubSequenceCollector collector = new SubSequenceCollector(stepSize, 1, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("3"), new Sequence("6"), new Sequence("9"));
        assertEquals(expected, results);
    }

    @Test
    public void subSequencesHaveMinLength() {
        Sequence input = new Sequence("1234567890");
        int stepSize = 3;
        int minLength = 2;
        SubSequenceCollector collector = new SubSequenceCollector(stepSize, minLength, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("34"), new Sequence("67"), new Sequence("90"));
        assertEquals(expected, results);
    }

    @Test
    public void minLengthAndStepSizeDoNotCauseEndOfStringOverflow() {
        Sequence input = new Sequence("123456789");
        int stepSize = 3;
        int minLength = 2;
        SubSequenceCollector collector = new SubSequenceCollector(stepSize, minLength, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("34"), new Sequence("67"));
        assertEquals(expected, results);
    }

    @Test
    public void subSequencesHaveMinUniqueCharsAndContinuesWithRunOfSameChars() {
        Sequence input = new Sequence("111222333444555666777888999000");
        int stepSize = 5;
        int minLength = 2;
        int minUniqueChars = 2;

        SubSequenceCollector collector = new SubSequenceCollector(stepSize, minLength, minUniqueChars);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("22333"), new Sequence("444555"), new Sequence("5666"), new Sequence("77888"), new Sequence("999000"));
        assertEquals(expected, results);
    }
}
