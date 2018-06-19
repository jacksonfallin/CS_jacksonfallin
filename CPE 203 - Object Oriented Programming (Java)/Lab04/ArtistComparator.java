import java.util.*;
import java.lang.*;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class ArtistComparator implements Comparator<Song>
	{
		public int compare(Song obj1, Song obj2)
		{
			int compare = obj1.getArtist().compareTo(obj2.getArtist()); 

			if (compare < 0)
			{
				return -1;
			}

			if (compare > 0)
			{
				return 1;
			}

			else
			{
				return 0;
			}
		}
	}