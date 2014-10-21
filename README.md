dockingbox
==========

A Biojava based utility for selecting docking boxes based on amino acids and atom positions. Used for tools like iDock, Autodock Vina etc.


To use the tool, simply instantiate the reader and input the PDBID, Chain and residue (positions) around which you want to place the bounding box

```
PDBReader reader = new PDBReader();
double[] box = PDBStructureUtils.getBoundingBox(reader.readPDB("2RLJ"), "A", new int[]{8, 12});
```

The resulting box object contains:

box[0] is the width of the box in the x plane
box[1] is the height of the box in the y plane
box[2] is the depth of the box in the z plane
box[3] is the centre of the box in the x plane
box[4] is the centre of the box in the y plane
box[5] is the centre of the box in the z plane
