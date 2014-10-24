package com.devl.development.dockingbox.pdb;

import org.biojava.bio.structure.*;

import java.util.List;
import java.util.Vector;

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
            System.out.println("Atom ligands: " + c.getAtomSequence());
            if (c.getChainID().equals(chainId)) {

                return c;
            }
        }

        return null;
    }

    public static List<Atom> getAminoAtomsFromChain(Chain chain, int[] residuePositions) throws PDBStructureUtilsException {
        List<Group> groups = chain.getAtomGroups("amino");
        List<Atom> allAtoms = new Vector<Atom>();
        for (Group group : groups) {

            if (group instanceof AminoAcidImpl) {
                AminoAcidImpl aa = (AminoAcidImpl) group;

                for (int position : residuePositions) {
                    //only select atoms for the residues we are interesed in
                    if (aa.getResidueNumber().getSeqNum() == position) {
                        allAtoms.addAll(aa.getAtoms());
                        System.out.println("Selecting Amino Acid " + aa.getAminoType() + " in chain" + chain.getChainID());
                    }
                }
            } else if (group instanceof AminoAcid) {
                AminoAcid aa = (AminoAcid) group;

                for (int position : residuePositions) {
                    //only select atoms for the residues we are interesed in
                    if (aa.getResidueNumber().getSeqNum() == position) {
                        allAtoms.addAll(aa.getAtoms());
                        System.out.println("Selecting Amino Acid " + aa.getPDBName() + " in chain" + chain.getChainID());
                    }
                }
            }
        }
        if (allAtoms.isEmpty()) {
            throw new PDBStructureUtilsException("No matching atoms found ");
        }
        return allAtoms;
    }

    /**
     * Entry point to locate bounding box for a PDB structure given residue positions and a chain id.
     * The returned value is the width, height, depth of the box and its centre coordinates. Applys nt "padding" to
     * the box, the box is strictly bound to the selected amino acids
     *
     * @param structure
     * @param chainId
     * @param residues
     * @return double[] in the form of [maxX - minX, maxY - minY, maxZ - minZ, centreX, centreY, centreZ]
     */
    public static double[] getBoundingBox(Structure structure, String chainId, int[] residues) throws PDBStructureUtilsException {
        return getBoundingBox(getAminoAtomsFromChain(getChainFromStructure(structure, chainId), residues),0 );
    }

    /**
     * Entry point to locate bounding box for a PDB structure given residue positions and a chain id.
     * The returned value is the width, height, depth of the box and its centre coordinates.
     *
     * @param structure
     * @param chainId
     * @param residues
     * @param padding the extra room (in Angstroms) to leave for ligand translation and rotation
     * @return double[] in the form of [maxX - minX, maxY - minY, maxZ - minZ, centreX, centreY, centreZ]
     */
    public static double[] getBoundingBoxWithPadding(Structure structure, String chainId, int[] residues,double padding) throws PDBStructureUtilsException {
        return getBoundingBox(getAminoAtomsFromChain(getChainFromStructure(structure, chainId), residues),padding );
    }

    /**
     * Locates the maximum and minimum atomic positions and derives a bounding box centered around the residues of interest
     *
     * @param paddingInAngstroms the additional space to leave to allow ligand rotation, translation
     * @param atoms
     * @return
     */
    public static double[] getBoundingBox(List<Atom> atoms, double paddingInAngstroms) {

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

        minX-=paddingInAngstroms;
        minY-=paddingInAngstroms;
        minZ-=paddingInAngstroms;
        maxX+=paddingInAngstroms;
        maxY+=paddingInAngstroms;
        maxZ+=paddingInAngstroms;

        double centreX = ((maxX - minX) / 2.0) + minX;
        double centreY = ((maxY - minY) / 2.0) + minY;
        double centreZ = ((maxZ - minZ) / 2.0) + minZ;
        double width = maxX - minX;
        double height = maxY - minY;
        double depth = maxZ - minZ;
        return new double[]{width, height, depth, centreX, centreY, centreZ};

    }
}
