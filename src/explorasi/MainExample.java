/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package explorasi;

import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author M. Reza Irvanda
 */
public class MainExample {

    public static void main(String[] args) throws ProtegeReasonerException {
        JFrame frame = new GameJFrame();
        frame.pack();                                       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    
    }

}
