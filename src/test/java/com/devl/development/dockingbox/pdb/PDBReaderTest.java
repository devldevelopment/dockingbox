package com.devl.development.dockingbox.pdb;

import org.biojava.bio.structure.Structure;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Dev Lakhani on 21/10/14.
 */
public class PDBReaderTest {

    private PDBReader reader;

    @Before
    public void before()
    {
        reader=new PDBReader();
    }


    @Test
    public void readPDB() throws PDBFileReaderException {
        Structure structure = reader.readPDB("2RLJ");
        assertNotNull(structure);
    }



}
