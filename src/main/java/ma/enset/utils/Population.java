package ma.enset.utils;

import java.util.*;

public class Population {
    List<Individual> individuals=new ArrayList<>();
    Individual firstFitness;
    Individual secondFitness;
    Random rnd=new Random();
    public void initialaizePopulation(){
        for (int i=0;i<GAUtils.POPULATION_SIZE;i++){
            Individual individual = new Individual();
            individual.setGenes(generateRandomGenes());
//            System.out.println("individual : "+i +" :  "+Arrays.toString(individual.getGenes()));
//            System.out.println(Arrays.toString(individual.getGenes()));
            individuals.add(individual);
        }
    }
    /**======================================**/
    public char[] generateRandomGenes() {
        char[] randomGenes = new char[GAUtils.MAX_FITNESS];
        Random rnd = new Random();

        for (int i = 0; i < GAUtils.MAX_FITNESS; i++) {
            int randomIndex = rnd.nextInt(GAUtils.CHARATERS.length());
            randomGenes[i] = GAUtils.CHARATERS.charAt(randomIndex);
        }

        return randomGenes;
    }
    /** ========================================**/
    public void calculateIndFintess(){
        for (int i=0;i<GAUtils.POPULATION_SIZE;i++){
            individuals.get(i).calculateFitness();
        }
    }
    public void selection(){
        firstFitness=individuals.get(0);
        secondFitness=individuals.get(1);
    }
    //croisement
    public void crossover(){

        int pointCroisment=rnd.nextInt(5);
        pointCroisment++;
        Individual individual1=new Individual();
        Individual individual2=new Individual();
        for (int i=0;i<individual1.getGenes().length;i++) {
            individual1.getGenes()[i]=firstFitness.getGenes()[i];
            individual2.getGenes()[i]=secondFitness.getGenes()[i];
        }
        for (int i=0;i<pointCroisment;i++) {
            individual1.getGenes()[i]=secondFitness.getGenes()[i];
            individual2.getGenes()[i]=firstFitness.getGenes()[i];
        }
        System.out.println(Arrays.toString(individual1.getGenes()));
        System.out.println(Arrays.toString(individual2.getGenes()));

        individuals.set(individuals.size()-2,individual1);
        individuals.set(individuals.size()-1,individual2);
    }
    public void mutation(){
        int index=rnd.nextInt(GAUtils.MAX_FITNESS);
        if (rnd.nextDouble()<GAUtils.MUTATION_PROB){
            individuals.get(individuals.size()-2).getGenes()[index]=GAUtils.CHARATERS.charAt(rnd.nextInt(GAUtils.CHARATERS.length()));
        }
        index=rnd.nextInt(GAUtils.MAX_FITNESS);
        if (rnd.nextDouble()<GAUtils.MUTATION_PROB){
            individuals.get(individuals.size()-1).getGenes()[index]=GAUtils.CHARATERS.charAt(rnd.nextInt(GAUtils.CHARATERS.length()));
        }


    }

    public List<Individual> getIndividuals() {
        return individuals;
    }
    public void sortPopulation(){
        Collections.sort(individuals,Collections.reverseOrder());
    }
    public Individual getFitnessIndivd(){
        return individuals.get(0);
    }

}
