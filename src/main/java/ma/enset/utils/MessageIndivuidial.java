package ma.enset.utils;

public class MessageIndivuidial {
    public MessageIndivuidial() {
    }

    public MessageIndivuidial(String genes, int fitness) {
        this.genes = genes;
        this.fitness = fitness;
    }

    private String genes;
        private int fitness;

    public String getGenes() {
        return genes;
    }

    public void setGenes(String genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
