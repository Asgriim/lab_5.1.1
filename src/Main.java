import exeptions.ReaderInterruptionException;
import utility.UserSession;

import javax.xml.bind.JAXBException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ReaderInterruptionException, JAXBException {
        UserSession userSesion = new UserSession();
        userSesion.run("LABA");
    }
}
