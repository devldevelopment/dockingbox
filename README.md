dockingbox
==========

A Biojava based utility for selecting docking boxes based on amino acids and atom positions. Used for tools like iDock, Autodock Vina etc.


To use the tool, simply instantiate the reader and input the PDBID, Chain and residue (positions) around which you want to place the bounding box

```
PDBReader reader = new PDBReader();
double[] box = PDBStructureUtils.getBoundingBox(reader.readPDB("2GF0"), "A", new int[]{4, 5, 6, 7, 8});

or 

double[] box = PDBStructureUtils.getBoundingBoxWithPadding(reader.readPDB("2GF0"), "A", new int[]{4, 5, 6, 7, 8},10.0);

which adds 10A of padding to the surrounding box for ligand translation and rotation
```
In the example above we are asking for a bounding box for protein 2GF0 with chain A and residues 4 through to 8.

The resulting box double array contains:

```
box[0] is the width of the box in the x plane
box[1] is the height of the box in the y plane
box[2] is the depth of the box in the z plane
box[3] is the centre of the box in the x plane
box[4] is the centre of the box in the y plane
box[5] is the centre of the box in the z plane
```

Which looks like (without padding):
![alt text][logo1]
[logo1]: https://github.com/devldevelopment/dockingbox/blob/master/src/test/resources/2GF0.png "2GFO Bounding Box"


Which looks like (with padding):
![alt text][logo]
[logo]: https://github.com/devldevelopment/dockingbox/blob/master/src/test/resources/2GF0Padding.png "2GFO Bounding Box with 10A padding"

TODO: Allow boxes from residues across chains.
