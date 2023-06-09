package ma.enset.conatiners;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ma.enset.agents.IslandAgent;
import ma.enset.utils.GAUtils;

public class SimpleContainer3 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();

        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController islandAgent = null;
        System.out.println(GAUtils.POPULATION_SIZE*2/3);
        System.out.println(GAUtils.POPULATION_SIZE);
        for (int i = GAUtils.POPULATION_SIZE*2/3; i< GAUtils.POPULATION_SIZE; i++){
            islandAgent = agentContainer.createNewAgent(String.valueOf(i),IslandAgent.class.getName(), new Object[]{});
            islandAgent.start();
        }
    }
}