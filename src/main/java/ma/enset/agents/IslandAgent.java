package ma.enset.agents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.PlatformController;
import ma.enset.utils.GAUtils;
import ma.enset.utils.Individual;
import ma.enset.utils.MessageIndivuidial;
import ma.enset.utils.Population;

import java.util.Arrays;
import java.util.List;

public class IslandAgent extends Agent {
    Population population = new Population();
    @Override
    protected void setup() {
        ACLMessage aclMessage1 = new ACLMessage(ACLMessage.REQUEST);

        aclMessage1.setContent("give me my part");
        aclMessage1.setConversationId("demande");
        aclMessage1.addReceiver(new AID(MasterAgent.class.getName()));
        aclMessage1.addReceiver(new AID("MasterAgent",AID.ISLOCALNAME));
        send(aclMessage1);
        System.out.println(aclMessage1.getContent());
        SequentialBehaviour sq = new SequentialBehaviour();

        sq.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();

                if (message != null) {
                    try {
                        // Deserialize the content into List<Individual>
                        String json = message.getContent();
                        Gson gson = new Gson();
                        MessageIndivuidial[] messageIndivuidials = gson.fromJson(json, MessageIndivuidial[].class);

                        for (MessageIndivuidial messageIndivuidial : messageIndivuidials) {
                            population.getIndividuals().add(new Individual(messageIndivuidial.getGenes().toCharArray(),messageIndivuidial.getFitness()));
                        }
                        System.out.println(population.getIndividuals().size());



                    } catch (Exception e) {

                    }
                } else {
                    block();
                }
            }
        });

        sq.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {

            }
        });

        addBehaviour(sq);













        /**
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