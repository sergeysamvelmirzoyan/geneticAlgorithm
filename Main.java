
public class Main {

    public static void main(String[] args) {
        String gnome = "aabbbbccyftyu";

        Population population = new Population(gnome, 10);

        Algorithm.genetic_algorithm(population.getChromosomes());
    }
}
