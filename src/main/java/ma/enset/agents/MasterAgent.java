package ma.enset.agents;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import ma.enset.utils.Population;
public class MasterAgent extends Agent {
    Population population = new Population();
    @Override
    protected void setup() {
        population.initialaizePopulation();
        System.out.println(population.getIndividuals().size());
        Population population=new Population();
        population.initialaizePopulation();




        // recoi une demende de l'agent
        // give me my part to work on it




    }

}