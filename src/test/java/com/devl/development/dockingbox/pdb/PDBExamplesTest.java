package com.devl.development.dockingbox.pdb;
/**
 * Copyright 2014 Dev Lakhani
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
/**
 * Copyright 2014 Dev Lakhani
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PDBExamplesTest {

    @Test
    public void test2RLJ() throws PDBFileReaderException, PDBStructureUtilsException {
        PDBReader reader = new PDBReader();
        double[] box = PDBStructureUtils.getBoundingBox(reader.readPDB("2RLJ"), "A", new int[]{8, 12});
        assertEquals(12.661000000000001, box[0], 10e-4);//xsize
        assertEquals(7.428, box[1], 10e-4);//ysize
        assertEquals(6.140000000000001, box[2], 10e-4); //zsize
        assertEquals(2.5375000000000005, box[3], 10e-4);//xcentre
        assertEquals(-5.366, box[4], 10e-4);//ycentre
        assertEquals(0.1200000000000001, box[5], 10e-4);//zcentre
    }

    @Test
    public void test2GFO() throws PDBFileReaderException, PDBStructureUtilsException {
        PDBReader reader = new PDBReader();
        double[] box = PDBStructureUtils.getBoundingBox(reader.readPDB("2GF0"), "A", new int[]{4, 5, 6, 7, 8});
        assertEquals(6.625, box[0], 10e-4);
        assertEquals(12.758, box[1], 10e-4);
        assertEquals(15.7, box[2], 10e-4);
        assertEquals(19.5345, box[3], 10e-4);
        assertEquals(0.1459999999999999, box[4], 10e-4);
        assertEquals(-5.965, box[5], 10e-4);
    }
}
