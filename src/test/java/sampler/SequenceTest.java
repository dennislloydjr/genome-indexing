package sampler;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class SequenceTest {
    @Test
    public void sequenceSortedInStringOrder() {
        List<Sequence> sequences = new Vector<>();
        sequences.add(new Sequence("tta"));
        sequences.add(new Sequence("ttc"));
        sequences.add(new Sequence("gac"));
        sequences.add(new Sequence("actg"));
        
        Collections.sort(sequences);

        List<Sequence> expected = new Vector<>();
        expected.add(new Sequence("actg"));
        expected.add(new Sequence("gac"));
        expected.add(new Sequence("tta"));
        expected.add(new Sequence("ttc"));

        assertEquals(expected, sequences);
        
    }
}
