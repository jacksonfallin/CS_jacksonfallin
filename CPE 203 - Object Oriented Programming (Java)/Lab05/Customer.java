import java.util.List;
import java.util.LinkedList;

// TODO:  Fill this in

public class Customer 
{
	CustomerID customerID;
	List<Session> sessions;

	public Customer(CustomerID customerID)
	{
		this.customerID = customerID;
		this.sessions = new LinkedList<Session>();
	}

    public List<Session> getSessions() 
    {
        return sessions;
    }

    public void addSession(Session session) 
    {
    	sessions.add(session);
    }
}
