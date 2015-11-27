/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package explorasi;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author M. Reza Irvanda
 */
public class MainExample {

    public static void main(String[] args) throws OntologyLoadException, FileNotFoundException {
//        if(args.length < 1){
//            System.out.println("Arguments needed : <owl_location>");
//            return;
//        }
        OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream("transportation.owl"));
        printClassAndIndividuals(owlModel);
        printAllAttributes(owlModel);
    }
    
    public static void printClassAndIndividuals(OWLModel owlModel){
        Collection classes = owlModel.getUserDefinedOWLNamedClasses();
        for (Iterator it = classes.iterator(); it.hasNext();) {
            OWLNamedClass cls = (OWLNamedClass) it.next();
            Collection instances = cls.getInstances(false);
            System.out.println("Class " + cls.getBrowserText() + " (" + instances.size() + ")");
            for (Iterator jt = instances.iterator(); jt.hasNext();) {
                OWLIndividual individual = (OWLIndividual) jt.next();
                System.out.println(" - " + individual.getBrowserText());
            }
        }
    }
    
    public static void printAllAttributes(OWLModel owlModel){
        Collection properties = owlModel.getUserDefinedOWLProperties();
        for (Iterator it = properties.iterator(); it.hasNext();) {
            OWLObjectProperty property = (OWLObjectProperty) it.next();
            System.out.println("Property : " + property.getName());
        }
    }
    
    
}
