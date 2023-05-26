package ma.enset.conatiners;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ma.enset.agents.IslandAgent;
import ma.enset.agents.MasterAgent;

public class MainContainer {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.GUI,"true");
        AgentContainer agentContainer = runtime.createMainContainer(profile);
        AgentController masterAgent = agentContainer.createNewAgent("MasterAgent",
                MasterAgent.class.getName(), new Object[]{});
        masterAgent.start();





    }
}
