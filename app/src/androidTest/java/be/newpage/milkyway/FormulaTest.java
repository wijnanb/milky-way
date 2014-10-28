package be.newpage.milkyway;

import junit.framework.TestCase;

import java.util.Date;

import be.newpage.milkyway.fragments.GraphFragment;

public class FormulaTest extends TestCase {

    public void testFormula() {
        Date today = new Date("28 october 2014 22:04");
        assertEquals(876, GraphFragment.getLenaVolumePerDay(new Date("6 june 2014"), 7300, today));
        assertEquals(876, GraphFragment.getLenaVolumePerDay(new Date("25 june 2014 18:00"), 7300, today));

        assertEquals(450, GraphFragment.getLenaVolumePerDay(new Date("28 october 2012"), 20000, today));
        assertEquals(540, GraphFragment.getLenaVolumePerDay(new Date("28 january 2014"), 10000, today));
        assertEquals(800, GraphFragment.getLenaVolumePerDay(new Date("28 april 2014"), 8000, today));

        assertEquals(630, GraphFragment.getLenaVolumePerDay(new Date("28 august 2014"), 4500, today));
    }
}
