package fi.muni.pv168;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

/**
 * shit
 *
 */
public class App {
    
    private static final Logger logger = (Logger) LoggerFactory.getLogger(App.class);
 
    public static void main(String[] args)
    {
        logger.info("==STARTING APPLICATION==");
        
        System.out.println("Hello Recipebook!" );
        System.out.println("I don't know, who's Milos Lukacka, but I heard, that that guy is awesome!");
        System.out.println("We have 30 minutes to upload our homework and Milan had commited NOTHING to the GIT hub HAHA");
        System.out.println("I'M A TRAIN! CHOOCHOO");
        
        logger.debug("==END OF SHIT==");
    }
}
