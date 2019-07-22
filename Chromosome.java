import java.util.ArrayList;
import java.util.List;

/**
 * Chromosome represents a solution to a problem that the genetic algorithm is trying to solve.
 */
public class Chromosome implements Comparable<Chromosome>{
    static int MAX_CHAR = 26;
    private String gnome;
    static int frequency[] = new int[MAX_CHAR];

    private double fitness;

    /**
     * Constructs and initializes a Chromosome with gnome and fitness.
     * @param gnome
     */
    public Chromosome(String gnome) {
        this.gnome = gnome;
        this.fitness = calculateFitness();
    }

    /**
     * Gets gnome.
     * @return genome
     */
    public String getGnome() {
        return gnome;
    }

    /**
     * Gets fitness.
     * @return fitness
     */
    public double getFitness() {
        return fitness;
    }

    /***
     *Calculates the difference between real and assumed best case variances.
     * @return relative fitness
     */
    public double calculateFitness(){
        double idealVar = calc_Variance(true);
        double realVar = calc_Variance(false);

        return Math.abs(realVar-idealVar);
    }

    /***
     * Calculate the average of position variances of all symbols in string.
     * @param idealCase
     * @return variance
     */
    public double calc_Variance(boolean idealCase){
        List<Integer>[] indexes = getIndexes(idealCase);
        double variance = 0.0;
        int symbol_number = 0;
        for (int i = 0; i < indexes.length; ++i){
            double var = 0.0;
            if (indexes[i].size()!=0){
                symbol_number++;
                double average = calc_Average(indexes[i]);
                var += calc_Particular_Variance(indexes[i], average);
            }
            variance += var;
        }
        return variance/symbol_number;
    }

    /**
     * Stores the indexes of all same symbols in list
     * @param idealCase indicates if the calculation is done for ideal case.
     * @return array of list of indexes.
     */
    private List<Integer>[] getIndexes(boolean idealCase){
        List<Integer>[] indexes = new List[MAX_CHAR];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new ArrayList<Integer>();
        }
        String bestPermutation;
        if(idealCase){
            bestPermutation = Algorithm.bestString(gnome);
        } else
            bestPermutation = gnome;

        for (int i = 0; i < bestPermutation.length(); ++i)
        {
            int ind = bestPermutation.charAt(i)-97;
            indexes[ind].add(i);
        }
        return indexes;
    }

    /**
     * Calculates the average position of a given symbol
     * @param indexList
     * @return average position
     */
    public double calc_Average(List<Integer> indexList){
        double average = 0.0;
        for (int i = 0; i < indexList.size(); ++i ){
            average += indexList.get(i);
        }
        return average/indexList.size();
    }

    /**
     * Calculates variance of positions for a given symbols.
     * @param indexList
     * @param average
     * @return
     */
    public double calc_Particular_Variance(List<Integer> indexList, double average){
        double var = 0.0;
        for (int i = 0; i < indexList.size(); ++i){
            var += Math.pow((indexList.get(i)-average), 2);
        }
        return var/indexList.size();
    }

    /**
     * Fills array with symbols frequencies.
     * @param gnome
     */
    public  static  void fillFrequency(String gnome){
        for(int i = 0; i < gnome.length(); ++i)
            frequency[gnome.charAt(i)-'a']++;
    }

    /**
     * Compares this Chromosome with other one.
     * @param o
     * @return
     */
    @Override
    public int compareTo(Chromosome o) {
        if(fitness == o.fitness)
            return 0;
        else if (fitness < o.fitness)
            return -1;
        else
            return 1;
    }
}
