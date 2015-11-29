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

/**
 *
 * @author M. Reza Irvanda
 */
public class MainExample {
    
    public static void main(String[] args) throws ProtegeReasonerException{
        TransportationUtil tUtil = TransportationUtil.getInstance();
        String nama_kendaraan;/* "Undefined";*/
        Map<String,String> aturan = new HashMap<>();
        nama_kendaraan = cmdInterface(aturan);
        List<String> hsl = tUtil.conclude(nama_kendaraan.replaceAll(" ", "_"),aturan); //path nya ga boleh mengandung spasi , gatau kenapa? -_-
       
        //List<String> hsl = tUtil.tesConclude();
        System.out.println(nama_kendaraan + " adalah: ");
        for(String s : hsl){
            System.out.println(s);
        }
//        System.out.println(nama_kendaraan);
//        for(Map.Entry<String,String> e : aturan.entrySet()){
//            System.out.println(e.getKey()+" : "+e.getValue());
//        }
    }

    private static String cmdInterface(Map<String, String> aturan) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Nama kendaraan : ");
        String nama_kendaraan = scan.nextLine();
        String value;
        int tmp;
        
        // HasModaQuestion
        System.out.println("Dimana kendaraan berjalan?");
        System.out.println("1. Darat");
        System.out.println("2. Laut");
        System.out.println("3. Udara");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasModa", tmp==3 ? "Udara" : tmp ==2 ? "Laut" : "Darat");
        // End of HasModaQuestion
        
        // HasKeperluan
        System.out.println("Digunakan untuk apakah kendaran tersebut?");
        System.out.println("1. Perang");
        System.out.println("2. Transportasi");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasKeperluan", tmp==1 ? "Perang" : "Transportasi" );
        // End of HasKeperluan
        
        // HasAkses
        System.out.println("Apakah kendaraan ini bersifat Publik atau Pribadi?");
        System.out.println("1. Publik");
        System.out.println("2. Pribadi");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasAkses", tmp==1 ? "Publik" : "Pribadi" );
        // End of HasAkses
        
        // HasLoad
        System.out.println("Secara umum, Apakah kendaraan ini membawa Penumpang atau Barang?");
        System.out.println("1. Penumpang");
        System.out.println("2. Barang");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasLoad", tmp==1 ? "Penumpang" : "Barang" );
        // End of HasLoad
        
        // HasJumlahRoda
        System.out.println("Secara umum, Berapa jumlah roda pada kendaraan ini?");
        System.out.println("1. Tidak memiliki roda");
        System.out.println("2. Dua");
        System.out.println("3. Tiga");
        System.out.println("4. Empat");
        System.out.println("5. Lebih dari Empat");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasJumlahRoda", tmp==1 ? "NULL" : tmp==2 ? "Dua" : tmp==3 ? "Tiga" : tmp==4 ? "Empat" : "LebihDariEmpat" );
        // End of HasJumlahRoda
        
        // HasSumberTenaga
        System.out.println("Bahan bakar apakah yang digunakan oleh kendaraan ini?");
        System.out.println("1. Angin");
        System.out.println("2. Solar");
        System.out.println("3. Avtur");
        System.out.println("4. Manusia");
        System.out.println("5. Batubara");
        System.out.println("6. Listrik");
        System.out.println("7. Bensin");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasSumberTenaga", tmp==1 ? "Angin" : tmp==2 ? "Solar" : tmp==3 ? "Avtur" : tmp==4 ? "Manusia" : tmp==5 ? "BatuBara" : tmp==6 ? "Listrik" : "Bensin");
        // End of HasSumberTenaga
        
        // HasModeTransmisi
        System.out.println("Mode Transmisi seperti apa yang digunakan pada kendaran ini?");
        System.out.println("1. Tidak diketahui");
        System.out.println("2. Manual");
        System.out.println("3. Semi-automatic");
        System.out.println("4. Automatic");
        tmp = Integer.valueOf(scan.nextInt());
        aturan.put("hasModeTransmisi", tmp==2 ? "Manual" : tmp==3 ? "Semi-Automatic" : tmp==4 ? "Automatic" : "NULL");
        // End of HasModeTransmisi
        
        return nama_kendaraan;
    }
    
    
}
