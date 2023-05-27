package ma.enset.agents;
import com.fasterxml.jackson.core.JsonProcessingException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
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





            addBehaviour(new CyclicBehaviour() {

                @Override
                public void action() {
                    ACLMessage  receiveMsg1 = receive();


                    if (receiveMsg1 != null) {
                        System.out.println(receiveMsg1.getContent());
                        System.out.println(receiveMsg1.getSender());

                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = null;
                        try {
                            json = objectMapper.writeValueAsString(population.getIndividuals());
                            System.out.println(json);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                        message.setConversationId("chroms");
                        message.addReceiver(receiveMsg1.getSender());
                        message.setContent(json);
                        message.setLanguage("JSON");
                        send(message);
                    } else {
                        block();
                    }

                }
            });


        /**=================================================**/




    }

}