import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    private MolecularData parseMolecularData(NodeList moleculeList) {
        List<Molecule> molecules = new ArrayList<>();
    
        for (int i = 0; i < moleculeList.getLength(); i++) {
            Node moleculeNode = moleculeList.item(i);
    
            if (moleculeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element moleculeElement = (Element) moleculeNode;
    
                String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
    
                NodeList bondList = moleculeElement.getElementsByTagName("Bonds").item(0).getChildNodes();
                List<String> bonds = new ArrayList<>();
                for (int j = 0; j < bondList.getLength(); j++) {
                    Node bondNode = bondList.item(j);
    
                    if (bondNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bondElement = (Element) bondNode;
                        bonds.add(bondElement.getTextContent());
                    }
                }
    
                molecules.add(new Molecule(id, bondStrength, bonds));
            }
        }
    
        return new MolecularData(molecules);
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {

        /* YOUR CODE HERE */ 
        try {
            File input = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();
    
            NodeList humanMoleculeList = doc.getElementsByTagName("HumanMolecularData").item(0).getChildNodes();
            NodeList vitalesMoleculeList = doc.getElementsByTagName("VitalesMolecularData").item(0).getChildNodes();
    
            this.molecularDataHuman = parseMolecularData(humanMoleculeList);
            this.molecularDataVitales = parseMolecularData(vitalesMoleculeList);
        } 

        catch (Exception e) {int nop = 0; }
        
    }
}
