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
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M. Reza Irvanda
 */
public class TransportationUtil {

    private static TransportationUtil instance;
    public Map<String, OWLNamedClass> clss = new HashMap<>();
    public Map<String, OWLObjectProperty> properties = new HashMap<>();
    public Map<String, OWLIndividual> individuals = new HashMap<>();
    private JenaOWLModel owlModel;
    static final String NAMESPACE = "http://www.semanticweb.org/lenovo/ontologies/2015/10/untitled-ontology-9#";
    private static final String OWL = "transportation.owl";
    private static final String[] CLASSES
            = {
                "Akses", "Angkot", "Becak", "Bemo", "Bus", "BusKotaMini",
                "Jumlah_roda", "Kapal", "KapalBarang", "KapalDayung",
                "KapalLayar", "KapalPenumpang", "KapalFeri", "Yacht","KapalPerang","Kendaraan",
                "Karavan","KendaraanPerangDarat","Humvee","Tronton","Keperluan", "Load", "Mobil",
                "Moda", "ModeTransmisi", "Motor", "MobilListrik", "MobilManual","MobilMatic",
                "MotorBebek", "MotorManual", "MotorMatic","MotorViar",
                "Ojek",
                "Pesawat", "HumanPoweredAircraft","LippischP.13a","PesawatKomersil", "PesawatTempur",
                "Sepeda", "Sumber_tenaga", "Tank", "Truk"
            };

    private static final String[] PROPERTIES
            = {
                "hasAkses", "hasJumlahRoda", "hasKeperluan",
                "hasLoad", "hasModa", "hasModeTransmisi",
                "hasSumberTenaga"
            };

    private static final String[] INDIVIDUALS
            = {
                "Pribadi", "Publik", // hasAkses
                "Dua", "Tiga", "Empat", "LebihDariEmpat", //hasJumlahRoda
                "Perang", "Transportasi", //hasKeperluan
                "Barang", "Penumpang", //hasLoad
                "Laut", "Udara", "Darat", //hasModa
                "Semi-Automatic", "Manual", "Automatic", //hasModeTransmisi
                "Angin", "Solar", "Avtur", "Manusia", "BatuBara", "Listrik", "Bensin", //hasSumberTenaga
            };

    private TransportationUtil() throws ProtegeReasonerException {
        try {
            owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream(OWL));
            loadClasses();
            loadProperties();
            loadIndividuals();

        } catch (FileNotFoundException | OntologyLoadException e) {
            System.out.println(e);
        }
    }

    private void loadClasses() {
        for (String s : CLASSES) {
            clss.put(s, (OWLNamedClass) owlModel.getOWLNamedClass(NAMESPACE + s));
            //System.out.println(clss.get(s));
        }
    }

    private void loadProperties() {
        for (String s : PROPERTIES) {
            properties.put(s, (OWLObjectProperty) owlModel.getOWLObjectProperty(NAMESPACE + s));
            //System.out.println(properties.get(s));
        }
    }

    private void loadIndividuals() {
        for (String s : INDIVIDUALS) {
            individuals.put(s, (OWLIndividual) owlModel.getOWLIndividual(NAMESPACE + s));
            //System.out.println(individuals.get(s));
        }
    }

    public static TransportationUtil getInstance() throws ProtegeReasonerException {
        if (instance == null) {
            instance = new TransportationUtil();
        }
        return instance;
    }

    public ProtegeReasoner createPelletOWLAPIReasoner() {

        ReasonerManager reasonerManager = ReasonerManager.getInstance();
        ProtegeReasoner reasoner = reasonerManager.createProtegeReasoner(owlModel, ProtegePelletOWLAPIReasoner.class);

        return reasoner;
    }

    public List<String> conclude(String nama_kendaraan, Map<String, String> aturan) {
        // str_kendaraan adalah nama kendaraan yang didefinisikan
        // aturan adalah pasangan antara property dengan nilainya, misalkan <"hasAkses", "Publik">
        ProtegeReasoner reasoner = createPelletOWLAPIReasoner();
        //OWLNamedClass kendaraan = owlModel.createOWLNamedClass(str_kendaraan);
        OWLNamedClass kendaraans = clss.get(("Kendaraan"));
        OWLIndividual kendaraan = kendaraans.createOWLIndividual(nama_kendaraan);
        System.out.println("Melakukan inferensi dari ciri-ciri " + nama_kendaraan);

        for (Map.Entry<String, String> entry : aturan.entrySet()) {
            String key, value;
            key = entry.getKey();
            value = entry.getValue();
            //System.out.println(key + " : " + value);
            if (!value.equals("NULL")) {
                kendaraan.addPropertyValue(properties.get(key), individuals.get(value));
            }
        }

        //Mendapatkan Tipe Kendaraan yang Dimaksud berdasarkan ciri-ciri yang didefinsikan pada aturan
        Collection inferredSuperclasses;
        List<String> tipes = new ArrayList<>();
        try {
            inferredSuperclasses = reasoner.getIndividualTypes(kendaraan);
            //System.out.println(nama_kendaraan + " adalah:");
            for (Iterator it = inferredSuperclasses.iterator(); it.hasNext();) {
                OWLNamedClass curClass = (OWLNamedClass) it.next();
                String class_name = curClass.getName().contains("Thing") ? "- Thing" : curClass.getName().replaceAll(NAMESPACE, "- ");
                //System.out.println("\t" + curClass.getName().replaceAll(NAMESPACE, "- "));
                tipes.add(class_name);
            }

        } catch (ProtegeReasonerException ex) {
            //Logger.get//Logger(TransportationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipes;

    }
    
    public List<String> tesConclude() {
        // str_kendaraan adalah nama kendaraan yang didefinisikan
        // aturan adalah pasangan antara property dengan nilainya, misalkan <"hasAkses", "Publik">
        String nama_kendaraan = "Sepeda_Wim_Cycle";
        ProtegeReasoner reasoner = createPelletOWLAPIReasoner();
        //OWLNamedClass kendaraan = owlModel.createOWLNamedClass(str_kendaraan);
        OWLNamedClass kendaraans = clss.get(("Kendaraan"));
        OWLIndividual kendaraan = kendaraans.createOWLIndividual(nama_kendaraan);
        System.out.println("Melakukan inferensi dari ciri-ciri " + nama_kendaraan);
        kendaraan.addPropertyValue(properties.get("hasSumberTenaga"), individuals.get("Manusia"));
        kendaraan.addPropertyValue(properties.get("hasAkses"), individuals.get("Pribadi"));
        kendaraan.addPropertyValue(properties.get("hasKeperluan"), individuals.get("Transportasi"));
        kendaraan.addPropertyValue(properties.get("hasJumlahRoda"), individuals.get("Dua"));
        kendaraan.addPropertyValue(properties.get("hasModa"), individuals.get("Darat"));
        kendaraan.addPropertyValue(properties.get("hasLoad"), individuals.get("Penumpang"));
        //Mendapatkan Tipe Kendaraan yang Dimaksud berdasarkan ciri-ciri yang didefinsikan pada aturan
        Collection inferredSuperclasses;
        List<String> tipes = new ArrayList<>();
        try {
            inferredSuperclasses = reasoner.getIndividualTypes(kendaraan);
            //System.out.println(nama_kendaraan + " adalah:");
            for (Iterator it = inferredSuperclasses.iterator(); it.hasNext();) {
                OWLNamedClass curClass = (OWLNamedClass) it.next();
                String class_name = curClass.getName().contains("Thing") ? "- Thing" : curClass.getName().replaceAll(NAMESPACE, "- ");
                //System.out.println("\t" + curClass.getName().replaceAll(NAMESPACE, "- "));
                tipes.add(class_name);
            }

        } catch (ProtegeReasonerException ex) {
            ////Logger.get//Logger(TransportationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipes;

    }
    
}
