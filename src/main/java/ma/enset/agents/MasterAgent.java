package ma.enset.agents;
import com.fasterxml.jackson.core.JsonProcessingException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ma.enset.utils.GAUtils;
import ma.enset.utils.Population;
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
                        switch (receiveMsg1.getConversationId()) {
                            case "demende":
                                System.out.print(receiveMsg1.getSender().getName()+" : ");
                                System.out.println(receiveMsg1.getContent());


                                ObjectMapper objectMapper = new ObjectMapper();
                                String json = null;
                                try {
                                    int start = 0;
                                    int end = start + GAUtils.POPULATION_SIZE * 1 / 3;
                                    json = objectMapper.writeValueAsString(population.getIndividuals().subList(start, end));

                                    start = end;
                                    end = end * 2;
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
                                break;
                            case  "done" :
                                System.out.println(receiveMsg1.getSender().getName());
                                System.out.println(receiveMsg1.getContent());
                                break;
                            default:
                                System.out.println("default :"); break;
                        }
                    } else {
                        block();
                    }

                }
            });


        /**=================================================**/




    }

}