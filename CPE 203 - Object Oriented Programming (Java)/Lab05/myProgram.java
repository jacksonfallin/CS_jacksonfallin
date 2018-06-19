import java.util.*;
import java.lang.Object;
import java.io.*;

public class myProgram
{
	public static void main() throws FileNotFoundException
		{
			String filename = "small.log";
			File file = new File(filename);
			Log log = new Log();
	
			Scanner scanner = new Scanner(file);

			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String array[] = line.split(" ");

				if(array[0].equals("START"))
				{
					CustomerID customerID = new CustomerID(array[2], array[3]);
					Customer customer = new Customer(customerID);
					Session session = new Session(array[1], customer);
					customer.addSession(session);
					log.sessionByID.put(array[1], session);
					log.customerByID.put(customerID, customer);
				}

				if(array[0].equals("VEIW"))
				{
					ProductID productID = new ProductID(array[2], Integer.parseInt(array[3]));
					Product product = new Product();
					Session session = log.sessionByID.get(array[1]);
				}

				if(array[0].equals("BUY"))
				{
					BuyID buyID = new BuyID(array[2], Integer.parseInt(array[3]), Integer.parseInt(array[4]));
					Buy buy = new Buy(buyID);
					Session session = log.sessionByID.get(array[1]);
				}

				if(array[0].equals("END"))
				{
					Session session = log.sessionByID.get(array[1]);
				}

			}
		}
}