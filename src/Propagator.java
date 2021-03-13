public class Propagator {

    private char start;
    private char end;

    public Propagator(char start, char end) {
        this.start = start;
        this.end = end;
    }

    public char getStart() {
        return start;
    }

    public char getEnd() {
        return end;
    }

    public void alphabetize() {
        if((int)start > (int)end) {
            char temp = start;
            start = end;
            end = temp;
        }
    }

    public boolean isZeroPropagator() {
        return start == end;
    }

    @Override
    public String toString() {
        this.alphabetize();
        return "" + start + end;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Propagator))
            return false;
        this.alphabetize();
        ((Propagator) obj).alphabetize();
        if(this.getStart() == ((Propagator) obj).getStart() && this.getEnd() == ((Propagator) obj).getEnd()) {
            return true;
        }
        return false;
    }
}
