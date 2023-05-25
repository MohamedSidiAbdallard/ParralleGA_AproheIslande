package ma.enset.conatiners;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class MainContainer {
    public static void main(String[] args) throws StaleProxyException{
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"true");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);

        AgentController masterAgent = agentContainer.createNewAgent("MasterAgent", "ma.enset.agents.MasterAgent",
                    new Object[]{});
        masterAgent.start();

    }
}
