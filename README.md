dockingbox
==========

A Biojava based utility for selecting docking boxes based on amino acids and atom positions. Used for tools like iDock, Autodock Vina etc.


To use the tool, simply instantiate the reader and input the PDBID, Chain and residues around which you want to place the bounding box

```
PDBReader reader = new PDBReader();
double[] box = PDBStructureUtils.getBoundingBox(reader.readPDB("2RLJ"), "A", new int[]{8, 12});
```
