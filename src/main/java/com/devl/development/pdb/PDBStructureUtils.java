package com.devl.development.pdb;

import org.biojava.bio.structure.*;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by lapop on 21/10/14.
 */
public class PDBStructureUtils {

    /**
     * Returns a given chain in a structure assuming that the chain ID's are unique
     *
     * @param structure
     * @param chainId
     */
    public static Chain getChainFromStructure(Structure structure, String chainId) {
        for (Chain c : structure.getChains()) {
            System.out.println("Chain " + c.getName() + " details:");
            System.out.println("Atom ligands: " + c.getAtomLigands());
            if (c.getChainID().equals(chainId)) {

                return c;
            }
        }

        return null;
    }

    public static List<Atom> getAminoAtomsFromChain(Chain chain, int[] residuePositions) {
        List<Group> groups = chain.getAtomGroups("amino");
        List<Atom> allAtoms = new Vector<Atom>();
        for (Group group : groups) {
            if (group instanceof AminoAcid) {
                AminoAcid aa = (AminoAcid) group;
                for (int position : residuePositions) {
                    //only select atoms for the residues we are interesed in
                    if (aa.getResidueNumber().getSeqNum() == position) {
                        allAtoms.addAll(aa.getAtoms());
                    }
                }
            }
        }
        return allAtoms;
    }

    /**
     * Entry point to locate bounding box for a PDB structure given residue positions and a chain id
     * @param structure
     * @param chainId
     * @param residues
     * @return
     */
    public static double[] getBoundingBox(Structure structure, String chainId, int[] residues) {
        return getBoundingBox(getAminoAtomsFromChain(getChainFromStructure(structure, chainId), residues));
    }

    /**
     * Locates the maximum and minimum atomic positions and derives a bounding box centered around the residues of interest
     *
     * @param atoms
     * @return
     */
    public static double[] getBoundingBox(List<Atom> atoms) {

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Atom atom : atoms) {
            if (atom.getX() < minX) {
                minX = atom.getX();
            }
            if (atom.getX() > maxX) {
                maxX = atom.getX();
            }

            if (atom.getY() < minY) {
                minY = atom.getY();
            }
            if (atom.getY() > maxY) {
                maxY = atom.getY();
            }

            if (atom.getZ() < minZ) {
                minZ = atom.getZ();
            }
            if (atom.getZ() > maxZ) {
                maxZ = atom.getZ();
            }
        }
        double centreX = ((maxX - minX) / 2.0) + minX;
        double centreY = ((maxY - minY) / 2.0) + minY;
        double centreZ = ((maxZ - minZ) / 2.0) + minZ;
        return new double[]{maxX - minX, maxY - minY, maxZ - minZ, centreX, centreY, centreZ};

    }
}
