import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LogAnalyzer {

    private final Log log;    // The log we're analyzing

    /**
     * Create a new analyzer object to analyze the given log
     */
    public LogAnalyzer(Log log) {
        this.log = log;
    }

        //write this after you have figured out how to store your data
        //make sure that you understand the problem
    void printAverageViewsWithoutPurchase() {
        /* add printing */
    }

        //write this after you have figured out how to store your data
        //make sure that you understand the problem
    void printSessionPriceDifference() {
        System.out.println("Price Difference for Purchased Product by Session");

        /* add printing */
        // Hint:  ArrayList.sort() is a good way to sort a list.  See also
        //        https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html#ArrayList-java.util.Collection-
    }

        //write this after you have figured out how to store your data
        //make sure that you understand the problem
    void printCustomerItemViewsForPurchase() {
        System.out.println("Number of Views for Purchased Product by Customer");

        /* add printing */
    }


    /*
     * This method traverses your Log once it is populated.
     */
    private void printOutExample() {
        //
        // Iterate through the customers, in any order
        //
        for (Customer customer : log.customerByID.values()) {
            System.out.println(customer);
            for (Session session : customer.getSessions()) {
                System.out.println("    in " + session);
                for (View view : session.views) {
                    System.out.println("        looked at " + view.product);
                }
            }
        }
    }

    /**
     * The main analyzing function
     */
    public void analyze() {
        if (Constants.DEBUG) {
            System.out.println();
            System.out.println("*******  printOutExample() results:  ************");
            System.out.println();
            printOutExample();
            System.out.println();
            System.out.println();
        }
        printSessionPriceDifference();
        printCustomerItemViewsForPurchase();
        printAverageViewsWithoutPurchase();

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Log file(s) not specified.");
            System.exit(1);
        }

        try {
            for (String fileName : args) {
                LogParser parser = new LogParser(new File(fileName));
                LogAnalyzer analyzer = new LogAnalyzer(parser.parse());
                analyzer.analyze();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
