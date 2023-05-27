package ma.enset.agents;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ma.enset.utils.GAUtils;
import ma.enset.utils.Individual;
import ma.enset.utils.MessageIndivuidial;
import ma.enset.utils.Population;
import java.util.Arrays;

public class IslandAgent extends Agent {
    private Stage stage;
    private Label fitnessLabel;
    private Label chromosomeLabel;
    Population population = new Population();
    @Override
    protected void setup() {
        ACLMessage aclMessage1 = new ACLMessage(ACLMessage.REQUEST);

        aclMessage1.setContent("give me my part");
        aclMessage1.setConversationId("demende");
//        aclMessage1.addReceiver(new AID(MasterAgent.class.getName()));
        aclMessage1.addReceiver(new AID("MasterAgent",AID.ISLOCALNAME));
        send(aclMessage1);
//        System.out.println(aclMessage1.getContent());
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
//                        System.out.println("-------------------------");
//                        System.out.println("size :   "+population.getIndividuals().size());
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
                    System.out.println("exit.......................");
                    return true;
                }
                return false;

            }


        });


        sq.addSubBehaviour(new Behaviour() {
            int it=0;
            @Override
            public void action() {
                population.calculateIndFintess(population.getIndividuals().size());
                population.sortPopulation();



//                System.out.println("Chromosome :"+ Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());

                    population.selection();
                    population.crossover();
                    population.mutation();
                    population.calculateIndFintess(population.getIndividuals().size());
                    population.sortPopulation();
//                    System.out.println("It :"+it+"Chromosome :"+Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());
                    it++;



            }

            @Override
            public boolean done() {
                if (it==GAUtils.MAX_IT || population.getFitnessIndivd().getFitness()==GAUtils.MAX_FITNESS){
                    System.out.println(myAgent.getName()+"---->"+Arrays.toString(population.getFitnessIndivd().getGenes()));
                    System.out.println(population.getFitnessIndivd().getFitness());
                    sendingBest(myAgent.getAID(),String.valueOf(population.getFitnessIndivd().getGenes()));
                    return true;
                }
                return false;
            }
        });
        addBehaviour(sq);
    }

//    @Override
//    protected void onGuiEvent(GuiEvent guiEvent) {
//
//    }
    private void sendingBest(AID reciverAid,String msg){
            ACLMessage responseGAMsg = new ACLMessage(ACLMessage.INFORM);
            responseGAMsg.setConversationId("done");
            responseGAMsg.setContent(msg);
            responseGAMsg.addReceiver(new AID("MasterAgent",AID.ISLOCALNAME));
            send(responseGAMsg);
    }


}