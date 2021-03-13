import java.util.ArrayList;
import java.util.Collections;

public class Diagram {

    private ArrayList<Propagator> propagators;
    private int copies;
    private boolean marked;

    public Diagram() {
        propagators = new ArrayList<Propagator>();
        copies = 1;
        marked = false;
    }

    public ArrayList<Propagator> getPropagators() {
        return propagators;
    }

    public boolean getMarked() {
        return marked;
    }

    public void mark() {
        marked = true;
    }

    public void unMark() {
        marked = false;
    }

    public void alphabetize() {
        for(int i = 0; i < propagators.size(); i++) {
            propagators.get(i).alphabetize();
        }

        while(!isAlphabetized()) {
            for(int i = 0; i < propagators.size(); i++) {
                for(int j = i; j < propagators.size(); j++) {
                    if(i != j) {
                        if((int)propagators.get(i).getStart() > (int)propagators.get(j).getStart()) {
                            Collections.swap(propagators, i, j);
                        }
                    }
                }
            }
        }
    }

    public boolean isAlphabetized() {
        for(int i = 0; i < propagators.size()-1; i++) {
            if((int)propagators.get(i).getStart() > (int)propagators.get(i+1).getStart()) {
                return false;
            }
        }
        return true;
    }

    public void add(Propagator... propagator) {
        for(Propagator prop : propagator) {
            propagators.add(prop);
        }
    }

    public void remove(Propagator... propagator) {
        for(Propagator prop : propagator) {
            propagators.remove(prop);
        }
    }

    public void incrementCopies() {
        copies++;
    }

    @Override
    public String toString() {
        this.alphabetize();
        return "" + copies + ": " + propagators;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Diagram))
            return false;
        this.alphabetize();
        ((Diagram) obj).alphabetize();
        if(this.getPropagators().size() != ((Diagram) obj).getPropagators().size())
            return false;
        for(int i = 0; i < this.getPropagators().size(); i++) {
            if(!this.getPropagators().get(i).equals(((Diagram) obj).getPropagators().get(i)))
                return false;
        }
        return true;
    }

    public boolean isNonPhysical() {
        for(int i = 0; i < propagators.size(); i++) {
            if(propagators.get(i).isZeroPropagator())
                return true;
        }
        return false;
    }
}
