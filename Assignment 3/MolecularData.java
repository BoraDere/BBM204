import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    public List<MolecularStructure> identifyMolecularStructures() {
        Map<String, Molecule> moleculeMap = new HashMap<>();
        Map<String, MolecularStructure> structureMap = new HashMap<>();
        List<MolecularStructure> structures = new ArrayList<>();
        Set<String> visited = new HashSet<>();
    
        for (Molecule molecule : this.molecules) {
            moleculeMap.put(molecule.getId(), molecule);
        }
    
        for (Molecule molecule : this.molecules) {
            if (!visited.contains(molecule.getId())) {
                MolecularStructure structure = new MolecularStructure();
                structures.add(structure);
                dfs(molecule, moleculeMap, visited, structure, structureMap, structures);
            }
        }
    
        return structures;
    }
    
    private void dfs(Molecule molecule, Map<String, Molecule> moleculeMap, Set<String> visited, MolecularStructure structure, Map<String, MolecularStructure> structureMap, List<MolecularStructure> structures) {
        visited.add(molecule.getId());
        structure.addMolecule(molecule);
        structureMap.put(molecule.getId(), structure);
    
        for (String bondedMoleculeId : molecule.getBonds()) {
            Molecule bondedMolecule = moleculeMap.get(bondedMoleculeId);
            if (bondedMolecule != null && !visited.contains(bondedMolecule.getId())) {
                dfs(bondedMolecule, moleculeMap, visited, structure, structureMap, structures);
            } 
            
            else if (bondedMolecule != null && structureMap.get(bondedMoleculeId) != structure) {
                structures.remove(structureMap.get(bondedMoleculeId));
                structure.getMolecules().addAll(structureMap.get(bondedMoleculeId).getMolecules());
                
                for (Molecule m : structureMap.get(bondedMoleculeId).getMolecules()) {
                    structureMap.put(m.getId(), structure);
                }
            }
        }
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
    
        for (int i = 0; i < molecularStructures.size(); i++) {
            MolecularStructure structure = molecularStructures.get(i);
            System.out.println("Molecules in Molecular Structure " + (i + 1) + ": " + structure.toString());
        }
    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        for (MolecularStructure targetStructure : targetStructures) {
            if (!sourceStructures.contains(targetStructure)) {
                anomalyList.add(targetStructure);
            }
        }
        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (int i = 0; i < molecularStructures.size(); i++) {
            MolecularStructure structure = molecularStructures.get(i);
            System.out.println(structure.toString());
        }
    }
}
