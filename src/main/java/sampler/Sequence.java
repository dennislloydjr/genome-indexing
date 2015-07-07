package sampler;

import org.apache.commons.lang.StringUtils;

public class Sequence {
    private final String sequence;

    public Sequence(final String sequence) {
        this.sequence = StringUtils.defaultString(sequence);
    }

    public String getSequence() {
        return sequence;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Sequence) {
            final Sequence rhs = (Sequence)o;
            return sequence.equals(rhs.sequence);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return getSequence();
    }
}
