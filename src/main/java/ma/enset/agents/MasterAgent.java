package ma.enset.agents;
import com.fasterxml.jackson.core.JsonProcessingException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import ma.enset.utils.Population;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
public class MasterAgent extends Agent {
    Population population = new Population();
    @Override
    protected void setup() {
        population.initialaizePopulation();
        System.out.println(population.getIndividuals().size());
        Population population=new Population();
        population.initialaizePopulation();





            addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {

                    ACLMessage receiveMsg1 = receive();
                    if (receiveMsg1 != null) {
//                     Convert individuals to JSON
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = null;
                        try {
                            json = objectMapper.writeValueAsString(population.getIndividuals());
                            System.out.println(json);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        // Create ACLMessage
                        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                        message.addReceiver(new AID(IslandAgent.class.getName()));
                        message.setContent(json);
                        message.setLanguage("JSON");
                        send(message);
                        System.out.println(message.getContent());
                    } else {
                        block();
                    }

                }
            });


        /**=================================================**/



               /**===============================**/





        // recoi une demende de l'agent
        // give me my part to work on it




    }

}