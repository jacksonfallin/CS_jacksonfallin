import java.util.*;
import java.lang.Object;

// TODO:  Fill this in
public class CustomerID 
{
	String region;
	String customer;

	public CustomerID(String region, String customer)
	{
		this.region = region;
		this.customer = customer;
	}
	
	public int hashCode()
	{
		int hash = 1;
		int hash2 = 1;
		int hash3 = 0;
		hash = hash * 31 + region.hashCode();
		hash2 = hash2 * 31 + customer.hashCode();
		hash3 = hash2 + hash;
        return hash3;
	}
}
