/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package explorasi;

import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author M. Reza Irvanda
 */
public class MainExample {
    
    public static void main(String[] args) throws ProtegeReasonerException{
        TransportationUtil tUtil = TransportationUtil.getInstance();
        Scanner scan = new Scanner(System.in);
        String nama_kendaraan;
        Map<String,String> aturan = new HashMap<>();
        cmdInterface(nama_kendaran,aturan);
        conclude(nama_kendaraan,aturan);
    }

    private static void cmdInterface(Map<String, String> aturan) {
        
    }
    
    
}
