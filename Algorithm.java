import java.util.*;

/**
 * Algorithm class provides the logic of the algorithm.
 */
public class Algorithm {

    private static final double uniformRate = 0.4;

    /**
     * Mates two selected chromosomes to generate completely new chromosome.
     * @param chromosome1
     * @param chromosome2
     * @return
     */
    public static Chromosome crossover(Chromosome chromosome1, Chromosome chromosome2){
        String offspring = "";
        int[] counter = Chromosome.frequency.clone();
        int len = chromosome1.getGnome().length();
        Random random = new Random();
        for (int i = 0; i < len; ++i){
            double probability = random.nextDouble();

                if (probability < uniformRate
                        && counter[chromosome1.getGnome().charAt(i)-97]>0
                        && isNonRepetingCharachter(offspring, chromosome1.getGnome().charAt(i))) {
                    offspring += chromosome1.getGnome().charAt(i);
                    counter[chromosome1.getGnome().charAt(i)-97]--;
                }
                else if(probability < 2*uniformRate
                        && counter[chromosome2.getGnome().charAt(i)-97]>0
                        && isNonRepetingCharachter(offspring, chromosome2.getGnome().charAt(i))) {
                    offspring += chromosome2.getGnome().charAt(i);
                    counter[chromosome2.getGnome().charAt(i) - 97]--;
                }
                else {
                    char newSymbol = mutate(chromosome1.getGnome(),
                            offspring.isEmpty() ? ' ' : offspring.charAt(offspring.length() - 1), counter);

                    if(newSymbol == '#')
                        return null;
                    offspring += newSymbol;
                    counter[offspring.charAt(offspring.length() - 1) - 97]--;
                }

        }
        return new Chromosome(offspring);
    }

    /**
     * Entry point method. Called from main.
     * @param chromosomes
     */
    public static void genetic_algorithm(List<Chromosome> chromosomes){
        int generation = 0;

        boolean found = false;

        while(! found){
            if(!chromosomes.isEmpty()) {
                Collections.sort(chromosomes);
                if (chromosomes.get(0).getFitness() == 0) {
                    found = true;
                    break;
                }
            }
            List<Chromosome> newGeneration = new ArrayList<>();

            int num_of_best_chrom = (int) Math.ceil((10*chromosomes.size())/100);

            for(int i = 0;i < num_of_best_chrom; i++){
                newGeneration.add(chromosomes.get(i));
            }
            int rest = chromosomes.size() - num_of_best_chrom;

            Random random = new Random();
            for(int i = 0;i<rest;i++)
            {
                int len = chromosomes.size();
                int r1 = random.nextInt(chromosomes.size()/2);
                Chromosome parent1 = chromosomes.get(r1);
                int r2;
                do {
                    r2 = random.nextInt(chromosomes.size()/2);
                } while (r2==r1);

                Chromosome parent2 = chromosomes.get(r2);
                Chromosome child = Algorithm.crossover(parent1, parent2);
                if(child!=null)
                newGeneration.add(child);
                else i--;
            }
            if(!newGeneration.isEmpty())
            chromosomes = newGeneration;

            generation++;
        }
        System.out.print("Generation: " + generation + "    ");
        System.out.print("String: " + chromosomes.get(0).getGnome() + "    ");
        System.out.print("Fitness: " + chromosomes.get(0).getFitness() + "    ");

        }

    /**
     * Checks if two symbols are same.
     * @param offSpring
     * @param c
     * @return
     */
    private static boolean isNonRepetingCharachter(String offSpring, char c){

        if(offSpring.length() == 0)
            return true;

        int last = offSpring.length()-1;

        return offSpring.charAt(last) != c;
    }

    /**
     * Generates the best permutation of the string. The new string serves as a criterion
     * in the means of its elements variances.
     * @param str
     * @return
     */
    static String bestString(String str){
        int len = str.length();

        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String newStr = new String(chars);
        char[] result = new char[len];

        if(len % 2 == 0){
            for(int i = 0; i < len/2; ++i){
                result[2*i] = newStr.charAt(i);
            }
            for(int i = 0 ; i < len/2; ++i){
                result[2*i + 1] = newStr.charAt(i+len/2);
            }
        } else {
            for(int i = 0; i < len/2+1; ++i){
                result[2*i] = newStr.charAt(i);
            }
            for(int i = 0 ; i < len/2; ++i){
                result[2*i + 1] = newStr.charAt(i+len/2+1);
            }
        }
        return new String(result);
    }

    /**
     * Mutates a chromosome.
     * @param gnome
     * @param c
     * @param count
     * @return
     */
    private static char mutate(String gnome, char c, int[] count){
        List<Integer> symbolIndexes = new ArrayList<>();
        for (int i = 0; i < gnome.length(); ++i){
            int ind = gnome.charAt(i)-97;
            if(!symbolIndexes.contains(ind))
            symbolIndexes.add(ind);
        }
        int typeCount = 0;
        for (int i = 0; i < Chromosome.MAX_CHAR; ++i){
            if(count[i] != 0)
                ++typeCount;
        }
        int len = symbolIndexes.size();

        Random random = new Random();
        int index;
        char ch;
        do {
            index = random.nextInt(len);
            ch = (char)(symbolIndexes.get(index)+97);
            if(typeCount == 1 && count[ch-97]>=1 && ch == c)
                return '#';

        } while (ch == c || count[ch-97] < 1);
        return ch;
    }

}
