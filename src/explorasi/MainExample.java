/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package explorasi;

import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;

/**
 *
 * @author M. Reza Irvanda
 */
public class MainExample {
    public static final String NAMESPACE = "http://www.semanticweb.org/lenovo/ontologies/2015/10/untitled-ontology-9#";
    public static void main(String[] args) throws ProtegeReasonerException{
//        if(args.length < 1){
//            System.out.println("Arguments needed : <owl_location>");
//            return;
//        }
        TransportationUtil tUtil = TransportationUtil.getInstance();
    }
}
