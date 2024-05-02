import java.util.*;
import java.util.stream.Collectors;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Molecule> selectedMolecules = new ArrayList<>();
        
        for (MolecularStructure structure : humanStructures) {
            selectedMolecules.add(structure.getMoleculeWithWeakestBondStrength());
        }
        
        for (MolecularStructure structure : diffStructures) {
            selectedMolecules.add(structure.getMoleculeWithWeakestBondStrength());
        }
    
        List<Bond> potentialBonds = new ArrayList<>();
        for (int i = 0; i < selectedMolecules.size(); i++) {
            for (int j = i + 1; j < selectedMolecules.size(); j++) {
                Molecule molecule1 = selectedMolecules.get(i);
                Molecule molecule2 = selectedMolecules.get(j);
                double bondStrength = ((double) molecule1.getBondStrength() + molecule2.getBondStrength()) / 2; // WHY PRECISION LOSS
                Bond bond = new Bond(molecule1, molecule2, bondStrength);
                potentialBonds.add(bond);
            }
        }
    
        Collections.sort(potentialBonds, Comparator.comparing(Bond::getWeight));
    
        List<Bond> serum = new ArrayList<>();
        int[] parent = new int[selectedMolecules.size()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
    
        for (Bond bond : potentialBonds) {
            int i = selectedMolecules.indexOf(bond.getFrom());
            int j = selectedMolecules.indexOf(bond.getTo());
            int rootI = find(parent, i);
            int rootJ = find(parent, j);
            if (rootI != rootJ) {
                parent[rootI] = rootJ;
                serum.add(bond);
            }
        }
    
        return serum;
    }
    
    private int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        List<Molecule> selectedHumanMolecules = humanStructures.stream()
            .map(MolecularStructure::getMoleculeWithWeakestBondStrength)
            .collect(Collectors.toList());

        List<Molecule> selectedDiffMolecules = diffStructures.stream()
            .map(MolecularStructure::getMoleculeWithWeakestBondStrength)
            .collect(Collectors.toList());

        System.out.println("Typical human molecules selected for synthesis: " + selectedHumanMolecules);
        System.out.println("Vitales molecules selected for synthesis: " + selectedDiffMolecules);
        System.out.println("Synthesizing the serum...");

        double totalStrength = 0;

        for (Bond bond : serum) {
            System.out.printf("Forming a bond between %s - %s with strength %.2f%n", bond.getFrom(), bond.getTo(), bond.getWeight());
            totalStrength += bond.getWeight();
        }
        
        System.out.printf("The total serum bond strength is %.2f%n", totalStrength);
    }
}