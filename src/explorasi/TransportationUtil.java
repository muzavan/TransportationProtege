/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package explorasi;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.inference.pellet.ProtegePelletOWLAPIReasoner;
import edu.stanford.smi.protegex.owl.inference.protegeowl.ReasonerManager;
import edu.stanford.smi.protegex.owl.inference.reasoner.ProtegeReasoner;
import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import edu.stanford.smi.protegex.owl.model.OWLClass;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author M. Reza Irvanda
 */
public class TransportationUtil {
    private static TransportationUtil instance;
    public Map<String,OWLNamedClass> clss = new HashMap<>();
    public Map<String,OWLObjectProperty> properties = new HashMap<>();
    public Map<String,OWLIndividual> individuals = new HashMap<>();
    static final String NAMESPACE = "http://www.semanticweb.org/lenovo/ontologies/2015/10/untitled-ontology-9#";
    private static final String OWL = "transportation.owl";
    private static final String[] CLASSES = 
    {
        "Akses","Angkot","Bus",
        "Jumlah_roda","Kapal","KapalBarang",
        "KapalLayar","KapalPenumpang","Kendaraan",
        "Keperluan","Load","Mobil",
        "Moda","ModeTransmisi","Motor",
        "MotorBebek","MotorManual","MotorMatic",
        "Pesawat","PesawatKomersil","PesawatTempur",
        "Sepeda","Sumber_tenaga","Tank","Truk"
    };
    
    private static final String[] PROPERTIES = 
    {
        "hasAkses","hasJumlahRoda","hasKeperluan",
        "hasLoad","hasModa","hasModeTransmisi",
        "hasSumberTenaga"
    };
    
    private static final String[] INDIVIDUALS = 
    {
        "Pribadi","Publik", // hasAkses
        "Dua","Tiga","Empat","LebihDariEmpat", //hasJumlahRoda
        "Perang","Transportasi", //hasKeperluan
        "Barang","Penumpang", //hasLoad
        "Laut","Udara","Darat", //hasModa
        "Semi-Automatic","Manual","Automatic", //hasModeTransmisi
        "Angin","Solar","Avtur","Manusia","BatuBara","Listrik","Bensin", //hasSumberTenaga
    };
    
    private TransportationUtil() throws ProtegeReasonerException {
        try{
            OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream(OWL));
            loadClasses(owlModel);
            loadProperties(owlModel);
            loadIndividuals(owlModel);
            ProtegeReasoner reasoner = createPelletOWLAPIReasoner(owlModel);
            
            // -------- Contoh kode buat reasoner -----------
//            // Get the VegetarianPizza OWLNamedClass from the OWLModel 
//            OWLClass vegetarianPizza = owlModel.getOWLNamedClass("http://www.semanticweb.org/lenovo/ontologies/2015/10/untitled-ontology-9#Pesawat"); 
//            System.out.println("Searching for subclass.....");
//            if(vegetarianPizza != null) { 
//                // Get the number of asserted subclasses of VegetarianPizza 
//                Collection assertedSubclasses = vegetarianPizza.getNamedSubclasses(); 
//                System.out.println("Number of asserted VegetarianPizzas: " + assertedSubclasses.size()); 
//
//                // Now get the inferred subclasses of VegetarianPizza 
//                Collection inferredSubclasses = reasoner.getSubclasses(vegetarianPizza); 
//                System.out.println("Number of inferred VegetarianPizzas: " + inferredSubclasses.size()); 
//                System.out.println("VegetarianPizzas:"); 
//                for(Iterator it = inferredSubclasses.iterator(); it.hasNext();) { 
//                        OWLNamedClass curClass = (OWLNamedClass) it.next(); 
//                        System.out.println("\t" + curClass.getName()); 
//                } 
//            }
        }
        catch(FileNotFoundException | OntologyLoadException e){
                System.out.println(e);
        }
    }

    private void loadClasses(OWLModel owlModel) {
        for(String s : CLASSES){
          clss.put(s, (OWLNamedClass) owlModel.getOWLNamedClass(NAMESPACE+s));
          System.out.println(clss.get(s));
        }
    }

    private void loadProperties(OWLModel owlModel) {
        for(String s : PROPERTIES){
          properties.put(s, (OWLObjectProperty) owlModel.getOWLObjectProperty(NAMESPACE+s));
          //System.out.println(properties.get(s));
        }
    }

    private void loadIndividuals(OWLModel owlModel) {
       for(String s : INDIVIDUALS){
           individuals.put(s, (OWLIndividual) owlModel.getOWLIndividual(NAMESPACE+s));
           //System.out.println(individuals.get(s));
       } 
    }
    
    public static TransportationUtil getInstance() throws ProtegeReasonerException{
        if(instance == null){
            instance = new TransportationUtil();
        }
        return instance;
    }
    
    public ProtegeReasoner createPelletOWLAPIReasoner(OWLModel owlModel) {
        
        ReasonerManager reasonerManager = ReasonerManager.getInstance();
        ProtegeReasoner reasoner = reasonerManager.createProtegeReasoner(owlModel, ProtegePelletOWLAPIReasoner.class);
        
        return reasoner;
    }
}
