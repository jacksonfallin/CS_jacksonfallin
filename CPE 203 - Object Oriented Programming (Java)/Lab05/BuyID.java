import java.util.*;
import java.lang.Object;

// TODO:  Fill this in
public class BuyID 
{
	String product;
	int productPrice;
	int itemsBought;

	public BuyID(String product, int productPrice, int itemsBought)
	{
		this.product = product;
		this.productPrice = productPrice;
		this.itemsBought = itemsBought;
	}
	
	public int hashCode()
	{
		int hash = 1;
		int hash2 = 1;
		int hash3 = 0;
		int hash4 = 0;
		hash = hash * 31 + product.hashCode();
		hash2 = hash2 * 31 + productPrice;
		hash3 = hash3 * 31 + itemsBought;
		hash4 = hash3 + hash2 + hash;
        return hash4;
	}
}