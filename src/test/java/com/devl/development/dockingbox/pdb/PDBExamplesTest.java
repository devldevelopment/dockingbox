package com.devl.development.dockingbox.pdb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lapop on 21/10/14.
 */
public class PDBExamplesTest {

    @Test
    public void test2RLJ() throws PDBFileReaderException {
        PDBReader reader= new PDBReader();
        double[] box = PDBStructureUtils.getBoundingBox(reader.readPDB("2RLJ"), "A", new int[]{8, 12});
        assertEquals(12.661000000000001,box[0],10e-4);//xsize
        assertEquals(7.428,box[1],10e-4);//ysize
        assertEquals(6.140000000000001,box[2],10e-4); //zsize
        assertEquals(2.5375000000000005,box[3],10e-4);//xcentre
        assertEquals(-5.366,box[4],10e-4);//ycentre
        assertEquals(0.1200000000000001,box[5],10e-4);//zcentre
    }

}
