package ma.enset.agents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import ma.enset.utils.GAUtils;
import ma.enset.utils.Individual;
import ma.enset.utils.Population;

import java.util.Arrays;
import java.util.List;

public class IslandAgent extends Agent {
    Population population = new Population();
    @Override
    protected void setup() {
        ACLMessage aclMessage1 = new ACLMessage(ACLMessage.REQUEST);
        aclMessage1.setConversationId("demande");
        aclMessage1.addReceiver(new AID(MasterAgent.class.getName()));
        send(aclMessage1);
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();

                if (message != null) {
                    try {
                        // Deserialize the content into List<Individual>
                        String json = message.getContent();
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Individual> individuals = objectMapper.readValue(json, new TypeReference<List<Individual>>() {});

                        // Process the received individuals
                        // ...

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
                    block();
                }
            }
        });





        /**
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
         **/
    }
}