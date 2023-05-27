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

import javax.security.auth.callback.TextInputCallback;
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

        sq.addSubBehaviour(new Behaviour() {
            boolean exit= false;
            @Override
            public void action() {
                if (exit){



                    System.out.println("exit");
                    return;
                }
                System.out.println("atending ...");
                ACLMessage message = receive();

                if (message != null  ) {
                    try {
                        // Deserialize the content into List<Individual>
                        String json = message.getContent();
                        Gson gson = new Gson();
                        MessageIndivuidial[] messageIndivuidials = gson.fromJson(json, MessageIndivuidial[].class);

                        for (MessageIndivuidial messageIndivuidial : messageIndivuidials) {
                            population.getIndividuals().add(new Individual(messageIndivuidial.getGenes().toCharArray(),messageIndivuidial.getFitness()));
                        }
                        System.out.println(population.getIndividuals().size());
                        exit =true;



                    } catch (Exception e) {

                    }
                } else {
                    block();
                }
            }

            @Override
            public boolean done() {
                if (exit){
                    System.out.println(".......................");
                    return true;
                }
                return false;

            }


        });


        sq.addSubBehaviour(new Behaviour() {
            int it=0;
            @Override
            public void action() {
                population.calculateIndFintess();
                population.sortPopulation();



                System.out.println("Chromosome :"+ Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());

                    population.selection();
                    population.crossover();
                    population.mutation();
                    population.calculateIndFintess();
                    population.sortPopulation();
                    System.out.println("It :"+it+"Chromosome :"+Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());
                    it++;



            }

            @Override
            public boolean done() {
                if (it==GAUtils.MAX_IT || population.getFitnessIndivd().getFitness()==GAUtils.MAX_FITNESS){


                    return true;
                }
                return false;
            }
        });
        addBehaviour(sq);
















    }

}