import java.util.*;
import java.lang.*;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class ComposedComparator implements Comparator<Song>
{
	private Comparator<Song> c1;
	private Comparator<Song> c2;

	public ComposedComparator(Comparator<Song> c1, Comparator<Song> c2)
	{
		this.c1 = c2;
		this.c2 = c2;
	}

	public int compare(Song obj1, Song obj2)
	{
		int comp = c1.compare(obj1,obj2);

		if(comp == 0)
		{
			return c2.compare(obj1,obj2);
		}
		else
		{
			return comp;
		}
	}

}