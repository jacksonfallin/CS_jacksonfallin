import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
    private static final Song[] songs = new Song[] {
            //0
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            //1
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
            //2
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            //3
            new Song("Gerry Rafferty", "Baker Street", 1998),
            //4
            new Song("City and Colour", "Sleeping Sickness", 2007),
            //5
            new Song("Foo Fighters", "Baker Street", 1997),
            //6
            new Song("Queen", "Bohemian Rhapsody", 1975),
            //7
            new Song("Gerry Rafferty", "Baker Street", 1978)
        };

    @Test
    public void testArtistComparator()
    {
        Song[] test = new Song[] { songs[6], songs[7] };
        Arrays.sort(test, new ArtistComparator());
        Song[] ans = new Song[] { songs[7], songs[6] };
        assertArrayEquals(test, ans);

        Song[] test2 = new Song[] { songs[7], songs[0] };
        Arrays.sort(test2, new ArtistComparator());
        Song[] ans2 = new Song[] { songs[0], songs[7] };
        assertArrayEquals(test2, ans2);
    }

    @Test
    public void testLambdaTitleComparator()
    {
        Comparator<Song> Title = (Song obj1, Song obj2) -> obj1.getTitle().compareTo(obj2.getTitle());
        Comparator<Song> Year = Comparator.comparing(Song::getYear, (obj1, obj2) -> obj2.compareTo(obj1));

        Song[] test = new Song[] { songs[6], songs[7] };
        Arrays.sort(test, Title);
        Song[] ans = new Song[] { songs[7], songs[6] };
        assertArrayEquals(test, ans);

        Song[] test2 = new Song[] { songs[2], songs[3] };
        Arrays.sort(test2, Title);
        Song[] ans2 = new Song[] { songs[3], songs[2] };
        assertArrayEquals(test2, ans2);

        Song[] test3 = new Song[] { songs[3], songs[7] };
        Arrays.sort(test3, Year);
        Song[] ans3 = new Song[] { songs[3], songs[7] };
        assertArrayEquals(test3, ans3);
    }

    @Test
    public void testYearExtractorComparator()
    {
        Comparator<Song> Year = Comparator.comparing(Song::getYear, (obj1, obj2) -> obj2.compareTo(obj1));

        Song[] test = new Song[] { songs[5], songs[7] };
        Arrays.sort(test, Year);
        Song[] ans = new Song[] { songs[5], songs[7] };
        assertArrayEquals(test, ans);

        Song[] test2 = new Song[] { songs[3], songs[2] };
        Arrays.sort(test2, Year);
        Song[] ans2 = new Song[] { songs[2], songs[3] };
        assertArrayEquals(test2, ans2);

        // For this test, please make a comparator that orders songs
        // by the year.  You can use any mechanism you like to "extract"
        // the year.  You might find that java.util.Comparator contains
        // convenience methods that arguably make it easier to compare
        // using a single field.  On the other hand, learning one, more
        // general way to create comparators is simpler in a different way.
        //
        // If you want, you can use this test function for the "Comparing
        // two fields" part of the assignment (in which case you wouldn't
        // just be extracting the year).  Or, if you prefer, you can make
        // a new test function for "Comparing Two Fields."
    }

    @Test
    public void testComposedComparator()
    {
      Comparator<Song> Year = Comparator.comparing(Song::getYear, (obj1,obj2) -> obj1.compareTo(obj2));
      Comparator<Song> Title = (Song obj1, Song obj2) -> obj1.getTitle().compareTo(obj2.getTitle());

      Comparator<Song> YearTitle = new ComposedComparator(Year, Title);
      Song[] test = new Song[] {songs[1], songs[0]};
      Arrays.sort(test, YearTitle);
      Song[] ans2 = new Song[] {songs[1], songs[0]};
      assertArrayEquals(ans2, test);
    
      Comparator<Song> ArtistYear = new ComposedComparator(new ArtistComparator(), Year);
      Song[] test2 = new Song[] {songs[3], songs[7]};
      Arrays.sort(test2, ArtistYear);
      Song[] ans = new Song[] {songs[7], songs[3]};
      assertArrayEquals(ans, test2);
    }

    @Test
    public void runSort()
    {
        Comparator<Song> test = Comparator.comparing(Song::getArtist, (obj1,obj2) -> obj1.compareTo(obj2)).thenComparing(Song::getTitle, (c1,c2) -> c1.compareTo(c2)).thenComparing(Song::getYear, (c1,c2) -> c1.compareTo(c2));

        List<Song> testArray = new ArrayList<>(Arrays.asList(songs));
        List<Song> ans = Arrays.asList(
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Gerry Rafferty", "Baker Street", 1978),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
            );

        testArray.sort(test);

        assertEquals(testArray, ans);
    }
}
