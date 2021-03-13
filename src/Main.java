/*
Overall process:
- User inputs the field operators being contracted
- Program works out all possible contractions of the field operators
    - The set of all possible contractions is represented as an ArrayList
    - Each element of the ArrayList is a Diagram object
    - Each Diagram object contains an ArrayList of Propagator objects
    - Each propagator object refers to a single contraction of two field operators
- Duplicate diagrams are compressed so that each Diagram knows how many identical Diagrams it has
- ArrayList of all possible Diagrams is printed for the user, so that the physics may continue
*/

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Getting the field operators from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the field operators");
        String operators = scanner.nextLine();
        String[][] contractions = getPerms(operators);

        // Creating all of the proper Diagrams and Propagators
        ArrayList<Diagram> diagrams = new ArrayList<Diagram>();
        for(int i = 0; i < contractions.length; i++) {
            Diagram diagram = new Diagram();
            for(int j = 0; j < contractions[i].length; j++) {
                diagram.add(new Propagator(contractions[i][j].charAt(0), contractions[i][j].charAt(1)));
            }
            diagrams.add(diagram);
        }

        // Eliminate bad diagrams
        eliminateDupes(diagrams);
        cullNonphysical(diagrams);

        System.out.println(diagrams);

    }

    // This method was graciously provided by Ryan Crist
    public static String[][] getPerms(String operators) {
        String[][] allContractions = new String[2][operators.length()/2];
        for(int i = 0; i < operators.length()/2; i++) {
            allContractions[0][i] = "" + operators.charAt(2 * i) + operators.charAt(2 * i + 1);
            allContractions[1][i] = "" + operators.charAt(operators.length() - 2*i - 1) + operators.charAt(operators.length() - 2*i - 2);
        }
        return allContractions;
    }

    // Eliminates duplicate diagrams and increments the copies of the remaning version
    public static void eliminateDupes(ArrayList<Diagram> list) {
        ArrayList<Diagram> dupes = new ArrayList<Diagram>(); // Stores all of the diagrams to be eliminated
        for(int i = 0; i < list.size(); i++) { // Finds all of the repeat diagrams
            for(int j = i; j < list.size(); j++) {
                if(i != j) {
                    if(list.get(i).equals(list.get(j))) {
                        list.get(i).incrementCopies(); // Increments the copies of the remaining diagram
                        list.get(j).incrementCopies();
                        list.get(j).mark(); // Marks the duplicate for deletion
                    }
                }
            }
        }
        for(int i = 0; i < list.size(); i++) { // Puts all of the marked methods into the duplicates ArrayList
            if(list.get(i).getMarked())
                dupes.add(list.get(i));
        }
        for(int i = 0; i < dupes.size(); i++) { // Removes all of the duplicates
            list.remove(dupes.get(i));
        }
        for(int i = 0; i < list.size(); i++) {
            list.get(i).unMark();
        }
    }

    // Removes all impossible Diagrams, which are those meeting the following non-physical conditions
    //  - Propagator from point to itself
    public static void cullNonphysical(ArrayList<Diagram> list) {
        ArrayList<Diagram> nonPhysical = new ArrayList<Diagram>();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).isNonPhysical()) {
                list.get(i).mark();
            }
        }
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getMarked()) {
                nonPhysical.add(list.get(i));
            }
        }
        for(int i = 0; i < nonPhysical.size(); i++) {
            list.remove(nonPhysical.get(i));
        }
        for(int i = 0; i < list.size(); i++) {
            list.get(i).unMark();
        }
    }
}
