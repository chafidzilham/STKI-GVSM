/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfdata;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import jdk.nashorn.internal.parser.TokenStream;
import org.apache.lucene.analysis.id.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.snowball.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author sutri
 */
public class PdfFile extends javax.swing.JFrame {
    String path = " ";
    String term;
    
    DefaultTableModel model;
    String [] hapus = {"ada","adalah","adanya","adapun","agak","agaknya","agar","akan","akankah","akhir",
    "akhiri","akhirnya","aku","akulah","amat","amatlah","anda","andalah","antar","antara","antaranya","apa",
    "apaan","apabila","apakah","apalagi","apatah","artinya","asal","asalkan","atas","atau","ataukah","ataupun",
    "awal","awalnya","bagai","bagaikan","bagaimana","bagaimanakah","bagaimanapun","bagi","bagian","bahkan",
    "bahwa","bahwasanya","baik","bakal","bakalan","balik","banyak","bapak","baru","bawah","beberapa","begini",
    "beginian","beginikah","beginilah","begitu","begitukah","begitulah","begitupun","bekerja",
    "belakang","belakangan","belum","belumlah","benar","benarkah","benarlah","berada","berakhir","berakhirlah",
    "berakhirnya","berapa","berapakah","berapalah","berapapun","berarti","berawal","berbagai","berdatangan","beri",
    "berikan","berikut","berikutnya","berjumlah","berkali-kali","berkata","berkehendak","berkeinginan","berkenaan","berlainan",
    "berlalu","berlangsung","berlebihan","bermacam","bermacam-macam","bermaksud","bermula","bersama","bersama-sama","bersiap","bersiap-siap","bertanya",
    "bertanya-tanya","berturut","berturut-turut","bertutur","berujar","berupa","besar","betul","betulkah","biasa",
    "biasanya","bila","bilakah","bisa","bisakah","boleh","bolehkah","bolehlah","buat","bukan","bukankah","bukanlah","bukannya","bulan",
    "bung","cara","caranya","cukup","cukupkah","cukuplah","cuma","dahulu","dalam","dan","dapat","dari","daripada","datang","dekat","demi","demikian","demikianlah",
    "dengan","depan","di","dia","diakhiri","diakhirinya","dialah","diantara","diantaranya","diberi","diberikan","diberikannya",
    "dibuat","dibuatnya","didapat","didatangkan","digunakan","diibaratkan","diibaratkannya","diingat","diingatkan ","diinginkan",
    "dijawab","dijelaskan","dijelaskannya","dikarenakan","dikatakan","dikatakannya","dikerjakan","diketahui","diketahuinya","dikira",
    "dilakukan","dilalui","dilihat","dimaksud","dimaksudkan","dimaksudkannya","dimaksudnya","diminta","dimintais","dimisalkan",
    "dimulai","dimulailah","dimulainya","dimungkinkan","dini","dipastikan","diperbuat","diperbuatnya","dipergunakan","diperkirakan",
    "diperlihatkan","diperlukan","diperlukannya","dipersoalkan","dipertanyakan","dipunyai","diri","dirinya","disampaikan","disebut",
    "disebutkan","disebutkannya","disini","disinilah","ditambahkan","ditandaskan","ditanya","ditanyai","ditanyakan","ditegaskan",
    "ditujukan","ditunjuk","ditunjuki","ditunjukkan","ditunjukkannya","ditunjuknya","dituturkan","dituturkannya","diucapkan","diucapkannya",
    "diungkapkan","dong","dua","dulu","empat","enggak","enggaknya","entah","entahlah","guna","gunakan","hal","hampir","hanya",
    "hanyalah","hari","harus","haruslah","harusnya","hendak","hendaklah","hendaknya","hingga","ia","iala","ibarat",
    "ibaratkan","ibaratnya","ibu","ikut","ingat","ingat-ingat","ingin","inginkah","inginkan","ini","inikah","inilah","itu",
    "itukah","itulah","jadi","jadilah","jadinya","jangan","jangankan","janganlah","jauh","jawab",
    "jawaban","jawabnya","jelas","jelaskan","jelaslah","jelasnya","jika","jikalau","juga","jumlah","jumlahnya","justru",
    "kala","kalau","kalaulah","kalaupun","kalian","kami","kamilah","kamu","kamulah","kan","kapan","kapankah","kapanpun","karena","karenanya","kasus",
    "kata","katakan","katakanlah","katanya","ke","keadaan","kebetulan","kecil","kedua","keduanya","keinginan","kelamaan","kelihatan","kelihatannya",
    "kelima","keluar","kembali","kemudian","kemungkinan","kemungkinannya","kenapa","kepada","kepadanya","kesampaian",
    "keseluruhan","keseluruhannya","keterlaluan","ketika","khususnya","kini","kinilah","kira","kira-kira","kiranya",
    "kita","kitalah","kok","kurang","lagi","lagian","lah","lain","lainnya","lalu","lama","lamanya","lanjut","lanjutnya",
    "lebih","lewat","lima","luar","macam","maka","makanya","makin","malah","malahan","mampu","mampukah","mana","manakala",
    "manalagi","masa","masalah","masalahnya","masih","masihkah","masing","masing-masing","mau","maupun","melainkan",
    "melakukan","melalui","melihat","melihatnya","memang","memastikan","memberi","memberikan","membuat","memerlukan","memihak","meminta",
    "memintakan","memisalkan","memperbuat","mempergunakan","memperkirakan","memperlihatkan","mempersiapkan","mempersoalkan",
    "mempertanyakan","'mempunyai","memulai","memungkinkan","menaiki","menambahkan","menandaskan","menanti",
    "menanti-nanti","menantikan","menanya","menanyai","menanyakan","mendapat","mendapatkan","mendatang ","mendatangi","mendatangkan",
    "menegaskan","mengakhiri","mengapa","mengatakan","mengatakannya","mengenai","mengerjakan","mengetahui","menggunakan","menghendaki",
    "mengibaratka","mengibaratkannya","mengingat","mengingatkan","menginginkan","mengira","mengucapkan","mengucapkannya",
    "mengungkapkan","menjadi","menjawab","menjelaskan","menuju","menunjuk","menunjuki","menunjukkan","menunjuknya","menurut",
    "menuturkan","menyampaikan","menyangkut","menyatakan","menyebutkan","menyeluruh","menyiapkan","merasa","mereka","merekalah",
    "merupakan","meski","meskipun","meyakini","meyakinkan","minta","mirip","misal","misalkan","misalnya","mula","mulai",
    "mulailah","mulanya","mungkin","mungkinkah","nah","naik","namun","nanti","nantinya","nyaris","nyatanya","oleh",
    "olehnya","pada","padahal","padanya","pak","paling","panjang","pantas","para","pasti","pastilah","penting","pentingnya", "per",
    "percuma","perlu","perlukah","perlunya","pernah","persoalan","pertama","pertama-tama","pertanyaan","pertanyakan","pihak",
    "pihaknya","pukul","pula","pun","punya","rasa","rasanya","rata","rupanya","saat","saatnya","saja","sajalah","saling",
    "sama","sama-sama","sambil","sampai","sampai-sampai","sampaikan","sana","sangat","sangatlah","satu","saya","sayalah",
    "se","sebab","sebabnya","sebagai","sebagaimana","sebagainya","sebagian","sebaik","sebaik-baiknya","sebaiknya","sebaliknya","sebanyak",
    "sebegini","sebegitu","sebelum","sebelumnya","sebenarnya","seberapa","sebesar","sebetulnya","sebisanya","sebuah",
    "sebut","sebutlah","sebutnya","secara","secukupnya","sedang","sedangkan","sedemikiam","sedikit","sedikitnya",
    "seenaknya","segala","segalanya","segera","seharusnya","sehingga","seingat","sejak","sejauh","sejenak","sejumlah","sekadar",
    "sekadarnya","sekali","sekali-kali","sekalian","sekaligus","sekalipun","sekarang","sekarang","sekecil","seketika","sekiranya","sekitar",
    "sekitarnya","sekurang-kurangnya","sekurangnya","sela","selain","selaku","selalu","selama","selama-lamanya","selamanya","selanjutnya","seluruh",
    "seluruhnya","semacam","semakin","semampu","semampunya","semasa","semasih","semata","semata-mata","semaunya","sementara","semisal",
    "semisalnya","sempat","semua","semuanya","semula","sendiri","sendirian","sendirinya","seolah","seolah-olah","seorang",
    "sepanjang","sepantasnya","sepantasnyalah","seperlunya","seperti","sepertinya","sepihak","sering","seringnya","serta","serupa","sesaat",
    "sesama","sesampai","sesegera","sesekali","seseorang","sesuatu","sesuatunya","sesudah","sesudahnya","setelah","setempat ","setengah",
    "seterusnya","setiap","setiba","setibanya","setidak-tidaknya","setidaknya","setinggi","seusai","sewaktu","siap","siapa","siapakah",
    "siapapun","sini","sinilah","soal","soalnya","suatu","sudah","sudahkah","sudahlah","supaya","tadi","tadinya","tahu","tahun","tak","tambah",
    "tambahnya","tampak","tampaknya","tandas","tandasnya","tanpa","tanya","tanyakan","tanyanya","tapi","tegas","tegasnya","telah","tempat",
    "tengah","tentang","tentu","tentulah","tentunya","tepat","terakhir","terasa","terbanyak","terdahulu","terdapat","terdiri","terhadap","terhadapnya",
    "teringat","teringat-ingat","terjadi","terjadilah","terjadinya","terkira","terlalu","terlebih","terlihat","termasuk","ternyata",
    "tersampaikan","tersebut","tersebutlah","tertentu","tertuju","terus","terutama","tetap","tetapi","tiap","tiba","tiba-tiba","tidak",
    "tidakkah","tidaklah","tiga","tinggi","toh","tunjuk","turut","tutur","tuturnya","ucap","ucapnya","ujar","ujarnya","umum","umumnya","ungkap",
    "ungkapnya","untuk","usah","usai","waduh","wah","wahai","waktu","waktunya","walau","walaupun","wong","yaitu","yakin","yakni","yang"};
    /**
     * Creates new form PdfFile
     */
    ArrayList<String>  wordList = new ArrayList<String>();
    public PdfFile() {
        initComponents();
    }
    /*public void StemmingFile(String fileIn, String fileOut) {
        try {
            System.out.println("Proses stemming file: " + fileIn + ", silakan tunggu...");
            IndonesianStemmer stemmer = new IndonesianStemmer();
            String teks = new Scanner(new File(fileIn)).useDelimiter("\\A").next();
            PrintWriter pw = new PrintWriter(fileOut);
            String temp = "";
            String[] baris = teks.split("\n");
            for (String b : baris) {
                String kata[] = b.split(" ");
                for (int i = 0; i < kata.length; i++) {
                    //stemmer.setCurrent(kata[i]);
                    stemmer.
                    if (stemmer.stem()) {
                        temp = temp + " " + stemmer.getCurrent();
                    } else {
                        temp = temp + " " + kata[i];
                    }
                }
                temp = temp.substring(1, temp.length());
                pw.println(temp);
                pw.flush();
                temp = "";
            }
            System.out.println("Stemming file selesai, hasilnya disimpan di file: " + fileOut);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        stopword = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        teksarea2 = new javax.swing.JTextArea();
        tokenizing = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        teksarea3 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Upload");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        stopword.setText("Stopword");
        stopword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopwordActionPerformed(evt);
            }
        });

        teksarea2.setColumns(20);
        teksarea2.setRows(5);
        jScrollPane3.setViewportView(teksarea2);

        tokenizing.setText("Tokenizing");
        tokenizing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tokenizingActionPerformed(evt);
            }
        });

        teksarea3.setColumns(20);
        teksarea3.setRows(5);
        jScrollPane4.setViewportView(teksarea3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tokenizing)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopword)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(stopword)
                        .addComponent(tokenizing)))
                .addContainerGap())
        );

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kata"
            }
        ));
        jScrollPane2.setViewportView(tabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(new File(path));
            ParseContext pcontext = new ParseContext();
            PDFParser pdfparser = new PDFParser(); 
            pdfparser.parse(inputstream, handler, metadata, pcontext);
            //PDDocument doc = PDDocument.load(new File(path));
            //String text = new PDFTextStripper().getText(doc);;
            //System.out.println("Text in PDF\n---------------------------------");
            //System.out.println(text);
            String teks = handler.toString();
            textArea.setText(teks);
            } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException ex) {
            Logger.getLogger(PdfFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PdfFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void stopwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopwordActionPerformed
        // TODO add your handling code here:
        /*int k=0,i,j;
        String [] hasil = new String[590000];
        
        String sCurrentLine;
        String[]stopwords = new String[590000]*/
        try{
            
            BodyContentHandler handler = new BodyContentHandler();
            teksarea2.append(handler.toString());
            FileWriter tulis = null;
            tulis = new FileWriter ("E:\\simpan.txt");
            BufferedWriter bw = new BufferedWriter(tulis);
            teksarea2.write(bw);
            bw.close();
            teksarea2.requestFocus();
            tulis.close();
            //Scanner coba;
            /*String coba = new Scanner(new File("simpan.txt")).useDelimiter("\\A").next();
            coba = coba.trim().replaceAll("\\s+"," ");
            System.out.println("Before : "+coba);
            String []words = coba.split(" ");
            for (String word : words){
                wordList.add(word);  
            }
            System.out.println("After : "+wordList);
            */
            Scanner baca = new Scanner(new File("E:\\simpan.txt"));
            FileOutputStream out = new FileOutputStream("Hasil.txt");
            //FileReader fr = new FileReader("E:\\stopword.txt");
            //BufferedReader br = new BufferedReader(fr);
            /*while((sCurrentLine = br.readLine())!=null){
                stopwords[k]=sCurrentLine;
                k++;
            }*/   
            //String s = teksarea2.getText();
            String [] header = {"Kata"};
            //StringBuilder builder = new StringBuilder(s);
            //String [] words = builder.toString().split("\\s+");
            while(baca.hasNext()){
                int ls = 1;
                String kata = baca.next();
                //kata = baca.next();
                //kata = kata.toLowerCase();
                for(int a=0;a<hapus.length;a++){
                    if(kata.equals(hapus[a])){
                        ls = 0;
                    }
                }
                    
                if (ls!=0){
                   teksarea3.append(kata +"\n");
                   //PrintStream p = new PrintStream(out);
                   //p.println(kata);
                }      
            } 
            String huruf = teksarea3.getText();
            String[] ambil = huruf.split("\n");
            Object data[][] = new Object[590000][1];
            for (int a = 0; a < ambil.length; a++) {
                data[a][0] = ambil[a];
            }
            model = new DefaultTableModel(data, header);
            tabel.setModel(model);  
            
            //PdfFile t = new PdfFile();
            //t.StemmingFile("baru3.txt", "corpus_stemming.txt");
            String printstemming = new Scanner(new File("baru3.txt")).useDelimiter("\\A").next();
            teksarea2.append(printstemming);
           
            /*for (String word : words){
                wordList.add(word);  
            }
            for(int ii = 0; ii<wordList.size();ii++){
                for (int jj =0; jj< k;jj++){
                    if (stopwords[jj].contains(wordList.get(ii))){
                        wordList.remove(ii);
                        break;
                    }
                }
                
            }
            int aa=0;
            for (String str: wordList){
                hasil[aa] = str;
                aa++;
            }
            //String kata [] = hasil[aa].split(" ");
            Object data [][] = new Object [590000][1];
            for(int a=0;a<hasil.length;a++){
                data[a][0]=hasil[a];
            }
            model = new DefaultTableModel(data,header);
            tabel.setModel(model);*/
        }catch(Exception ex){
            
        }
        /*/String kalimat = teksarea2.getText();
        //StringTokenizer st = new StringTokenizer(kalimat, ", ");
        //String delims = "[ \t.,:;()&*]+";
        //String [] st = kalimat.split(delims);
        kalimat = kalimat.replaceAll("E:\\stopword.txt","");
        String st = kalimat;
        String kata [] = st.split(" ");
        String header [] = {"Kata"};
        Object data [][] = new Object [590000][1];
        for(int a=0;a<kata.length;a++){
            data[a][0]=kata[a];
        }/*/
        //model = new DefaultTableModel(data,header);
        //tabel.setModel(model);   
    }//GEN-LAST:event_stopwordActionPerformed

    private void tokenizingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tokenizingActionPerformed
        // TODO add your handling code here:
        String kalimat = textArea.getText();
        kalimat = Normalizer.normalize(kalimat, Normalizer.Form.NFD);
       
        //StringTokenizer st = new StringTokenizer(kata, ",.[]()*&#=+-_?");
        /*while(st.hasMoreTokens()){
            System.out.println(st.nextToken());
            String a = st.nextToken();
            teksarea2.setText(teksarea2.getText()+a+" ");
        }*/
        String kata1 = kalimat.replaceAll("[^a-zA-Z0-9]", " ");
        String kata2 = kata1.replaceAll("( )+", " ");
        String kata = kata2.toLowerCase();
        //.setText(teksarea2.getText()+kata2+" ");
        //String baru4 = baru3.replaceAll(" ", "|");
        //System.out.println("sblm ;" + baru4);
        //String delims = ("[ =_\t.,:;()/&-*+?]");
        //String delims = ("[ _]");
        String [] st = kata.split(" ");
        //String header [] = {"Kata"};
        //Object data [][] = new Object [590000][1];
        for(String kataStr:st){
            teksarea2.setText(teksarea2.getText()+kataStr+"\n");
        }
        
        //model = new DefaultTableModel(data,header);
        //tabel.setModel(model);
    }//GEN-LAST:event_tokenizingActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PdfFile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton stopword;
    private javax.swing.JTable tabel;
    private javax.swing.JTextArea teksarea2;
    private javax.swing.JTextArea teksarea3;
    private javax.swing.JTextArea textArea;
    private javax.swing.JButton tokenizing;
    // End of variables declaration//GEN-END:variables
}
