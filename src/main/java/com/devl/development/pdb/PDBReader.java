package com.devl.development.pdb;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.FileParsingParameters;
import org.biojava.bio.structure.io.PDBFileReader;

import java.io.IOException;

/**
 * Created by devldevelopment on 21/10/14.
 */
public class PDBReader {

    private PDBFileReader reader;

    public PDBReader() {
        reader = new PDBFileReader();
        // the path to the local PDB installation
        reader.setPath("/tmp");
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
     * Returns a structure object by first fetching the PDB File from the repository, parsing it and returnn
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
