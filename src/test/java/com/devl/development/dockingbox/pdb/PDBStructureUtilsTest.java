package com.devl.development.dockingbox.pdb;

import org.biojava.bio.structure.*;
import org.junit.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Dev Lakhani on 21/10/14.
 */
public class PDBStructureUtilsTest {

    @Test
    public void extractChains() throws PDBFileReaderException {
        Structure structure = mock(Structure.class);
        Chain chainA = mock(Chain.class);
        Vector<Chain> chains= new Vector<Chain>();
        chains.add(chainA);
        when(chainA.getChainID()).thenReturn("A");
        when(structure.getChains()).thenReturn(chains);
        Chain a = PDBStructureUtils.getChainFromStructure(structure, "A");
        assertNotNull(a);
        assertEquals("A", a.getChainID());
    }

    @Test
    public void extractAtoms() throws PDBFileReaderException {
        Chain chainA = mock(Chain.class);

        AminoAcid group1= mock(AminoAcid.class);
        ResidueNumber res1=mock(ResidueNumber.class);
        when(res1.getSeqNum()).thenReturn(new Integer(1));
        when(group1.getResidueNumber()).thenReturn(res1);
        Vector<Atom> atoms = new Vector<Atom>();
        atoms.add(new AtomImpl());
        when(group1.getAtoms()).thenReturn(atoms);


        AminoAcid group2= mock(AminoAcid.class);
        ResidueNumber res2=mock(ResidueNumber.class);
        when(res2.getSeqNum()).thenReturn(new Integer(2));
        when(group2.getResidueNumber()).thenReturn(res2);
        when(group2.getAtoms()).thenReturn(atoms);


        AminoAcid group3= mock(AminoAcid.class);
        ResidueNumber res3=mock(ResidueNumber.class);
        when(res3.getSeqNum()).thenReturn(new Integer(9));
        when(group3.getResidueNumber()).thenReturn(res3);
        when(group3.getAtoms()).thenReturn(atoms);

        AminoAcid group4= mock(AminoAcid.class);
        ResidueNumber res4=mock(ResidueNumber.class);
        when(res4.getSeqNum()).thenReturn(new Integer(44));
        when(group4.getResidueNumber()).thenReturn(res4);
        when(group4.getAtoms()).thenReturn(atoms);

        List<Group> groups = new Vector<Group>();
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);
        when(chainA.getAtomGroups("amino")).thenReturn(groups);

        List<Atom> a = PDBStructureUtils.getAminoAtomsFromChain(chainA, new int[]{1, 2, 9});
        assertNotNull(a);
        assertEquals(3, a.size());
    }


    private AtomImpl createAtom(double x, double y , double z)
    {
        AtomImpl atom= new AtomImpl();
        atom.setX(x);
        atom.setY(y);
        atom.setZ(z);
        return atom;
    }

    @Test
    public void extractBox() throws PDBFileReaderException {

        Chain chainA = mock(Chain.class);
        when(chainA.getChainID()).thenReturn("A");



        AminoAcid group1= mock(AminoAcid.class);
        ResidueNumber res1=mock(ResidueNumber.class);
        when(res1.getSeqNum()).thenReturn(new Integer(1));
        when(group1.getResidueNumber()).thenReturn(res1);
        Vector<Atom> atoms = new Vector<Atom>();
        atoms.add(createAtom(-10,10,-10));
        atoms.add(createAtom(10,10,10));
        when(group1.getAtoms()).thenReturn(atoms);


        AminoAcid group2= mock(AminoAcid.class);
        ResidueNumber res2=mock(ResidueNumber.class);
        when(res2.getSeqNum()).thenReturn(new Integer(2));
        when(group2.getResidueNumber()).thenReturn(res2);
        Vector<Atom> atoms2 = new Vector<Atom>();
        atoms2.add(createAtom(-30,10,-30));
        atoms2.add(createAtom(30,30,30));
        when(group2.getAtoms()).thenReturn(atoms2);


        AminoAcid group3= mock(AminoAcid.class);
        ResidueNumber res3=mock(ResidueNumber.class);
        when(res3.getSeqNum()).thenReturn(new Integer(9));
        when(group3.getResidueNumber()).thenReturn(res3);
        Vector<Atom> atoms3 = new Vector<Atom>();
        atoms3.add(createAtom(0,0,0));
        atoms3.add(createAtom(0,0,0));
        when(group3.getAtoms()).thenReturn(atoms3);


        List<Group> groups = new Vector<Group>();
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        when(chainA.getAtomGroups("amino")).thenReturn(groups);


        List<Chain> chains= new Vector<Chain>();
        chains.add(chainA);
        Structure structure = mock(Structure.class);
        when(structure.getChains()).thenReturn(chains);

        double[] a = PDBStructureUtils.getBoundingBox(structure, "A", new int[]{1, 2, 9});
        assertNotNull(a);
        assertEquals(60, a[0],10e-4);//width
        assertEquals(30, a[1],10e-4);//height
        assertEquals(60, a[2],10e-4);//depth
        assertEquals(0, a[3],10e-4);//centrex
        assertEquals(15, a[4],10e-4);//centrey
        assertEquals(0, a[5],10e-4);//centrez
    }
}
