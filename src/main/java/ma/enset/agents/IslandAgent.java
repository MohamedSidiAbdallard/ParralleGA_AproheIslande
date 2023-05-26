package ma.enset.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import ma.enset.utils.GAUtils;
import ma.enset.utils.Population;

import java.util.Arrays;

public class IslandAgent extends Agent {
    Population population = new Population();
    @Override
    protected void setup() {
        population.initialaizePopulation();
        ACLMessage aclMessage1 = new ACLMessage(ACLMessage.REQUEST);
        aclMessage1.setConversationId("demande");
        aclMessage1.addReceiver(new AID(MasterAgent.class.getName()));
        ACLMessage receive = receive();

//        population = receive.getContent();
        population.calculateIndFintess();
        population.sortPopulation();
        int it=0;
        System.out.println("Chromosome :"+ Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());

        while (it< GAUtils.MAX_IT && population.getFitnessIndivd().getFitness()<GAUtils.MAX_FITNESS){
            population.selection();
            population.crossover();
            population.mutation();
            population.calculateIndFintess();
            population.sortPopulation();
            System.out.println("It :"+it+"Chromosome :"+Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());
            it++;
        }

    }


}