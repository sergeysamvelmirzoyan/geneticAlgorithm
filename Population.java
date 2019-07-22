import java.util.ArrayList;
import java.util.List;

/**
 * Population is the set of all solutions (Chromosomes).
 */
public class Population {

    private int counter;
    private List<Chromosome> chromosomes = new ArrayList<>();
    private  String gnome;

    /**
     * Gets the list of chromosomes.
     * @return chromosomes
     */
    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    /**
     * Constructs a Population with size number of permutations of gnome string.
     * @param gnome
     * @param size
     */
    public Population(String gnome, int size) {
        this.gnome = gnome;
        counter = size;
        Chromosome.fillFrequency(gnome);
        generatePermutation(gnome, "");
    }

    /**
     * Checks if the input string has adjacent elements same.
     * @param str
     * @return
     */
    private  boolean isNotRepeating(String str){
        for(int i = 0; i < str.length()-1; ++i)
            if(str.charAt(i) == str.charAt(i+1))
                return false;
        return true;
    }

    /**
     * Generates permutation of a given string that has no adjacent elements same.
     * @param str
     * @param ans
     */
    private void generatePermutation(String str, String ans)
    {
        if(counter <= 0)
            return;

        if (str.length() == 0) {
            if(isNotRepeating(ans)){

                chromosomes.add(new Chromosome(ans));
                counter--;
            }
            return;
        }

        for (int i = 0; i < str.length(); i++) {

            char ch = str.charAt(i);

            String ros = str.substring(0, i) +
                    str.substring(i + 1);

            generatePermutation(ros, ans + ch);
        }
    }
}
