package com.devl.development.dockingbox.pdb;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.align.util.AtomCache;
import org.biojava.bio.structure.io.FileParsingParameters;
import org.biojava.bio.structure.io.PDBFileReader;
import org.biojava3.core.util.InputStreamProvider;

import java.io.IOException;

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
public class PDBReader {

    private static final String PDB_FILE_PATH = "/tmp/";
    private PDBFileReader reader;

    public PDBReader() {
        reader = new PDBFileReader();
        // the path to the local PDB installation
        reader.setPath(PDB_FILE_PATH);
        // are all files in one directory, or are the files split,
        // as on the PDB ftp servers?
        reader.setPdbDirectorySplit(true);
        // should a missing PDB id be fetched automatically from the FTP servers?
        reader.setAutoFetch(true);
        // configure the parameters of file parsing
        FileParsingParameters params = new FileParsingParameters();
        // should the ATOM and SEQRES residues be aligned when creating the internal data model?
        params.setAlignSeqRes(true);
        // should secondary structure get parsed from the file
        params.setParseSecStruc(false);
        params.setLoadChemCompInfo(true);
        reader.setFileParsingParameters(params);
    }

    /**
     * Returns a structure object by first fetching the PDB File from the online repository, parsing it and returnn
     * <p/>
     * http://www.biojava.org/docs/api/index.html?org/biojava/bio/structure/io/
     *
     * @param pdbId
     * @return
     * @throws PDBFileReaderException
     * @see <a href="http://www.biojava.org/docs/api/org/biojava/bio/structure/Structure.html">Biojava Structure Hierarchy</a>
     */
    public synchronized Structure readPDB(String pdbId) throws PDBFileReaderException {
        try {

            return reader.getStructureById(pdbId);
        } catch (IOException e) {
            throw new PDBFileReaderException(e);
        }
    }


}
