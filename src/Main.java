import utility.UserSession;
import javax.xml.bind.JAXBException;

public class Main {
    public static void main(String[] args)  {
        UserSession userSession = new UserSession();
        try {
            userSession.run("LABA");
        } catch (JAXBException e) {
            System.out.println("xml error");
        }
    }
}
