package sampler;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubSequenceCollectorTest {
    @Test
    public void emptyReturnedFromEmptySequence() {
        Sequence input = new Sequence("");
        SubSequenceCollector collector = new SubSequenceCollector(1, 1, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence(""));
        assertEquals(expected, results);
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
        List<Sequence> expected = Arrays.asList(new Sequence("4"), new Sequence("7"), new Sequence("0"));
        assertEquals(expected, results);
    }

    @Test
    public void subSequencesHaveMinLength() {
        Sequence input = new Sequence("1234567890");
        int stepSize = 3;
        int minLength = 2;
        SubSequenceCollector collector = new SubSequenceCollector(stepSize, minLength, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("45"), new Sequence("78"), new Sequence("0"));
        assertEquals(expected, results);
    }

    @Test
    public void minLengthAndStepSizeDoNotCauseEndOfStringOverflow() {
        Sequence input = new Sequence("123456789");
        int stepSize = 3;
        int minLength = 2;
        SubSequenceCollector collector = new SubSequenceCollector(stepSize, minLength, 1);
        List<Sequence> results = collector.collect(input);
        List<Sequence> expected = Arrays.asList(new Sequence("45"), new Sequence("78"));
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
        List<Sequence> expected = Arrays.asList(new Sequence("2333"), new Sequence("44555"), new Sequence("666777"), new Sequence("7888"), new Sequence("99000"));
        assertEquals(expected, results);
    }
}
