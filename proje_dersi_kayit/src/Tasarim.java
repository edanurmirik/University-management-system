import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Tasarim extends JFrame {

    //String[] dersListesi = {"Atatürk Ilkeleri ve Inkılap Tarihi I","Türk Dili 1","İngilizce l", "Fizik I","Lineer Cebir","Matematik 1","Bilgisayar Laboratuvarı I","Bilgisayar Mühendisliğine Giriş","Programlama I"};
    Connection connection = null;

    boolean asama_atlama = false;
    ArrayList<String> harfNotlari = new ArrayList<>();
    ArrayList<String> alinanDersler = new ArrayList<>();
    int deger = 0;
    String hoca_ad, hoca_soyad;
    String secilenIlgiAlani1, secilenIlgiAlani2, secilenIlgiAlani3;
    String ders1, ders2, ders3,ders4,ders5;
    int katsayi1, katsayi2, katsayi3,katsayi4,katsayi5;
    String mesaj_icerik;
    ArrayList<Integer> pders_id = new ArrayList<>();
    String sorgu[] = {"SELECT * FROM yonetici", "SELECT * FROM hoca", "SELECT * FROM ogrenci", "SELECT * FROM ogrenci_ders", "SELECT * FROM dersler"};

    String isim[] = {"Ahmet", "Mehmet", "Mustafa", "Ali", "İbrahim", "Yusuf", "Serkan", "Barış", "Emir", "Cem", "Onur", "Hakan", "Furkan", "Burak", "Uğur", "Eren", "Can", "Deniz", "Kaan", "Gökhan", "Selim", "Tarık", "İsmail", "Ferhat", "Ayşe", "Fatma", "Zeynep", "Hacer", "Elif", "Esra", "Melek", "Selin", "Nazlı", "Ceren", "Nil", "Gamze", "Dilara", "Yasmin", "Deniz", "Berfin", "Pınar", "Aylin", "Aslı", "Sibel", "Ela", "Tuğçe", "Sevim", "Şule", "Merve"};
    String soyisim[] = {"Kaya", "Şimşek", "Yıldırım", "Aksoy", "Demir", "Arslan", "Güler", "Çelik", "Öztürk", "Aydın", "Erdoğan", "Yılmaz", "Karahan", "Toprak", "Gürsoy", "Şen", "Polat", "Özdemir", "Aslan", "Kocaman", "Tekin", "Doğan", "Bulut", "Özkan", "Saraçoğlu", "Duran", "Özgür", "Akgül", "Özbek", "Çetin", "Ayhan", "Şahin", "Tuncer", "Taşkın", "Aydemir", "Özmen", "Gül", "Şanlı", "Kılıç", "Çınar", "Karadeniz", "Şeker", "Özgün", "Yalçın", "Gündoğdu", "Şahbaz", "Aydoğan", "Yüksel", "Gültekin", "Korkmaz"};
    String harfNot[] = {"AA", "BA", "BB", "CB", "CC", "DC", "FF"};

    void ekran() {
        JFrame ekran = new JFrame();
        ekran.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ekran.getContentPane().setBackground(new Color(30, 157, 76));
        JButton yonetici = new JButton("YÖNETİCİ");
        JButton ogretim_uyesi = new JButton("ÖĞRETİM ÜYESİ");
        JButton ogrenci = new JButton("ÖĞRENCİ");

        ekran.add(yonetici);
        yonetici.setBounds(700, 250, 150, 60);
        yonetici.setBackground(new Color(238, 238, 209));
        ekran.add(ogretim_uyesi);
        ogretim_uyesi.setBounds(700, 350, 150, 60);
        ogretim_uyesi.setBackground(new Color(238, 238, 209));
        ekran.add(ogrenci);
        ogrenci.setBounds(700, 450, 150, 60);
        ogrenci.setBackground(new Color(238, 238, 209));

        yonetici.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deger = 1;
                giris_ekrani();
            }
        });

        ogretim_uyesi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deger = 2;
                giris_ekrani();
            }
        });

        ogrenci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deger = 3;
                giris_ekrani();
            }
        });

        ekran.setSize(2000, 1000);
        ekran.setResizable(true);
        ekran.setLayout(null);
        ekran.setVisible(true);
    }

    void giris_ekrani() {
        JFrame giris_ekrani = new JFrame();
        giris_ekrani.getContentPane().setBackground(new Color(30, 157, 76));
        JTextField id_text = new JTextField();
        JPasswordField sifre_text = new JPasswordField();
        JLabel id_label = new JLabel("ID");
        JLabel sifre_label = new JLabel("ŞİFRE");
        JButton giris = new JButton("GİRİŞ");

        giris_ekrani.setVisible(true);
        giris_ekrani.setSize(2000, 1000);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\Eda Nur\\IdeaProjects\\yazlab1\\src\\indir.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBounds(675, 50, 184, 184);
        giris_ekrani.add(label);

        giris_ekrani.add(id_label);
        id_label.setBounds(650, 300, 50, 30);
        giris_ekrani.add(sifre_label);
        sifre_label.setBounds(650, 350, 50, 30);
        giris_ekrani.add(id_text);
        id_text.setBounds(700, 300, 130, 30);
        giris_ekrani.add(sifre_text);
        sifre_text.setBounds(700, 350, 130, 30);
        giris_ekrani.add(giris);
        giris.setBounds(700, 420, 130, 30);
        giris.setBackground(new Color(238, 238, 209));

        giris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet resultSet;
                int id;
                String sifre;
                String ad, soyad;

                try {
                    if (deger == 1) {
                        resultSet = sorgulama(sorgu[0]);
                        while (resultSet.next()) {
                            id = Integer.parseInt(id_text.getText());
                            sifre = sifre_text.getText();
                            if (resultSet.getInt("yonetici_id") == id && resultSet.getString("yonetici_sifre").equals(sifre)) {
                                System.out.println("BRAAVVOOOO");
                                yonetici_ekrani(id);
                            }
                        }
                    } else if (deger == 2) {
                        resultSet = sorgulama(sorgu[1]);
                        while (resultSet.next()) {
                            id = Integer.parseInt(id_text.getText());
                            sifre = sifre_text.getText();
                            if (resultSet.getInt("hoca_id") == id && resultSet.getString("hoca_sifre").equals(sifre)) {
                                System.out.println("BRAAVVOOOO1111");
                                ad = resultSet.getString("hoca_ad");
                                soyad = resultSet.getString("hoca_soyad");
                                ogretim_uyesi_ekrani(id, ad, soyad);
                            }
                        }
                    } else if (deger == 3) {
                        resultSet = sorgulama(sorgu[2]);
                        while (resultSet.next()) {
                            id = Integer.parseInt(id_text.getText());
                            sifre = sifre_text.getText();
                            if (resultSet.getInt("ogrenci_id") == id && resultSet.getString("ogrenci_sifre").equals(sifre)) {
                                System.out.println("BRAAVVOOOO22222");
                                ad = resultSet.getString("ogrenci_ad");
                                soyad = resultSet.getString("ogrenci_soyad");
                                ogrenci_ekrani(id, ad, soyad);
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Okuma Yapılamadı.");
                }
            }
        });

        giris_ekrani.setResizable(true);
        giris_ekrani.setLayout(null);
        giris_ekrani.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void yonetici_ekrani(int id) {
        JFrame yonetici_paneli = new JFrame("YÖNETİCİ PANELİ");
        JButton ikinciAsama = new JButton("İkinci Aşamaya Geç");
        JButton kontenjanVeDersBelirle = new JButton("Ders ve Kontenjan Bilgileri");
        JButton mesajSiniri = new JButton("Mesaj Sınırı Belirle");
        JButton hocaSiniri = new JButton("Öğretim Üyesi Kısıtı Koy");
        JButton ogrenci_olustur = new JButton("Öğrenci Oluştur");

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\Eda Nur\\IdeaProjects\\yazlab1\\src\\yonetici.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);

        //Icon
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBounds(30, 30, 200, 200);

        //Numara
        JLabel numara = new JLabel(String.valueOf(id));
        numara.setBounds(85, 200, 200, 40);
        numara.setFont(new Font("Arial", Font.BOLD, 15));

        yonetici_paneli.add(label);
        yonetici_paneli.add(numara);

        ikinciAsama.setBounds(500, 50, 200, 50);
        ikinciAsama.setBackground(new Color(238, 216, 174));
        kontenjanVeDersBelirle.setBounds(500, 150, 200, 50);
        kontenjanVeDersBelirle.setBackground(new Color(238, 216, 174));
        hocaSiniri.setBounds(500, 250, 200, 50);
        hocaSiniri.setBackground(new Color(238, 216, 174));
        mesajSiniri.setBounds(500, 350, 200, 50);
        mesajSiniri.setBackground(new Color(238, 216, 174));
        ogrenci_olustur.setBounds(500, 450, 200, 50);
        ogrenci_olustur.setBackground(new Color(238, 216, 174));

        yonetici_paneli.add(ikinciAsama);
        yonetici_paneli.add(mesajSiniri);
        yonetici_paneli.add(hocaSiniri);
        yonetici_paneli.add(kontenjanVeDersBelirle);
        yonetici_paneli.add(ogrenci_olustur);


        ikinciAsama.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asama_atlama = true;
                JFrame atama_ekrani = new JFrame("ATAMA İŞLEMLERİ");
                JButton rastgeleAtama = new JButton("RASTGELE ATAMA");
                JButton notOrtAtama = new JButton("NOT ORTALAMASINA GÖRE ATAMA");
                JButton belirliDersAtama = new JButton("BELİRLİ DERSLERE GÖRE ATAMA");

                atama_ekrani.add(rastgeleAtama);
                rastgeleAtama.setBounds(50, 50, 250, 50);
                rastgeleAtama.setBackground(new Color(238, 216, 174));
                atama_ekrani.add(notOrtAtama);
                notOrtAtama.setBounds(50, 130, 250, 50);
                notOrtAtama.setBackground(new Color(238, 216, 174));
                atama_ekrani.add(belirliDersAtama);
                belirliDersAtama.setBounds(50, 210, 250, 50);
                belirliDersAtama.setBackground(new Color(238, 216, 174));

                rastgeleAtama.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String sor = "SELECT pders_id FROM proje_dersler";
                        ResultSet rs = sorgulama(sor);

                        try {
                            while (rs.next()) {
                                pders_id.add(rs.getInt("pders_id"));
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        String sorgu = "SELECT o.ogrenci_id, o.ogrenci_ad, o.ogrenci_soyad, COUNT(a.anlasma_talep_durumu) AS kabul_edilen_anlasma_sayisi\n" +
                                "FROM ogrenci o\n" +
                                "LEFT JOIN anlasma a ON o.ogrenci_id = a.ogrenci_id AND a.anlasma_talep_durumu = 'kabul edildi'\n" +
                                "GROUP BY o.ogrenci_id, o.ogrenci_ad, o.ogrenci_soyad ";

                        ResultSet resultSet = sorgulama(sorgu);
                        Random random = new Random();
                        try {
                            while (resultSet.next()) {
                                if (resultSet.getInt("kabul_edilen_anlasma_sayisi") == 0) {
                                    int adet = random.nextInt(pders_id.size());

                                    String s = "SELECT * FROM proje_dersler WHERE pders_id = " + pders_id.get(adet);
                                    ResultSet r = sorgulama(s);

                                    if (r.next()) {
                                        if (r.getInt("pders_kontenjan") > r.getInt("alan_ogrenci_sayisi")) {

                                            int hoca_id = r.getInt("hoca_id");
                                            int ogrenci_id = resultSet.getInt("ogrenci_id");

                                            String sorgu2 = "UPDATE proje_dersler\n" +
                                                    "SET alan_ogrenci_sayisi = alan_ogrenci_sayisi + 1\n" +
                                                    "WHERE pders_id = " + pders_id.get(adet) + " and hoca_id = " + hoca_id;
                                            update(sorgu2);

                                            String a = "INSERT INTO anlasma(ogrenci_id,hoca_id,ders_id,anlasma_talep_durumu,talep_eden_kisi_id) VALUES (" + ogrenci_id + "," + hoca_id + "," + pders_id.get(adet) + ", 'kabul edildi' ," + id + ")";
                                            update(a);
                                        }
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });

                belirliDersAtama.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame formul_ekrani = new JFrame();
                        JButton kaydet = new JButton("KAYDET");
                        JLabel label = new JLabel("FORMÜL");
                        label.setBounds(275, 50, 150, 50);

                        JComboBox<String> comboBox = new JComboBox<>();
                        comboBox.setBounds(50, 100, 250, 30);

                        JComboBox<String> comboBox1 = new JComboBox<>();
                        comboBox1.setBounds(50, 200, 250, 30);

                        JComboBox<String> comboBox2 = new JComboBox<>();
                        comboBox2.setBounds(50, 300, 250, 30);

                        JComboBox<String> comboBox3 = new JComboBox<>();
                        comboBox3.setBounds(50, 400, 250, 30);

                        JComboBox<String> comboBox4 = new JComboBox<>();
                        comboBox4.setBounds(50, 500, 250, 30);

                        JComboBox<Integer> comboBoxInt = new JComboBox<>();
                        comboBoxInt.setBounds(350, 100, 150, 30);

                        JComboBox<Integer> comboBoxInt1 = new JComboBox<>();
                        comboBoxInt1.setBounds(350, 200, 150, 30);

                        JComboBox<Integer> comboBoxInt2 = new JComboBox<>();
                        comboBoxInt2.setBounds(350, 300, 150, 30);

                        JComboBox<Integer> comboBoxInt3 = new JComboBox<>();
                        comboBoxInt3.setBounds(350, 400, 150, 30);

                        JComboBox<Integer> comboBoxInt4 = new JComboBox<>();
                        comboBoxInt4.setBounds(350, 500, 150, 30);

                        kaydet.setBounds(250, 550, 100, 50);

                        formul_ekrani.add(comboBox);
                        formul_ekrani.add(comboBox1);
                        formul_ekrani.add(comboBox2);
                        formul_ekrani.add(comboBox3);
                        formul_ekrani.add(comboBox4);
                        formul_ekrani.add(comboBoxInt);
                        formul_ekrani.add(comboBoxInt1);
                        formul_ekrani.add(comboBoxInt2);
                        formul_ekrani.add(comboBoxInt3);
                        formul_ekrani.add(comboBoxInt4);
                        formul_ekrani.add(kaydet);
                        formul_ekrani.add(label);

                        String sorgu2 = "SELECT ders_ad FROM dersler";
                        ResultSet resultSet1 = sorgulama(sorgu2);
                        try {
                            while (resultSet1.next()) {
                                comboBox.addItem(resultSet1.getString("ders_ad"));
                                comboBox1.addItem(resultSet1.getString("ders_ad"));
                                comboBox2.addItem(resultSet1.getString("ders_ad"));
                                comboBox3.addItem(resultSet1.getString("ders_ad"));
                                comboBox4.addItem(resultSet1.getString("ders_ad"));
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        for (int i = 1; i < 10; i++) {
                            comboBoxInt.addItem(i);
                            comboBoxInt1.addItem(i);
                            comboBoxInt2.addItem(i);
                            comboBoxInt3.addItem(i);
                            comboBoxInt4.addItem(i);
                        }

                        comboBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ders1 = (String) comboBox.getSelectedItem();
                            }
                        });
                        comboBoxInt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                katsayi1 = (int) comboBoxInt.getSelectedItem();
                            }
                        });
                        comboBox1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ders2 = (String) comboBox1.getSelectedItem();
                            }
                        });
                        comboBoxInt1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                katsayi2 = (int) comboBoxInt1.getSelectedItem();
                            }
                        });
                        comboBox2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ders3 = (String) comboBox2.getSelectedItem();
                            }
                        });
                        comboBoxInt2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                katsayi3 = (int) comboBoxInt2.getSelectedItem();
                            }
                        });
                        comboBox3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ders4 = (String) comboBox3.getSelectedItem();
                            }
                        });
                        comboBoxInt3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                katsayi4 = (int) comboBoxInt3.getSelectedItem();
                            }
                        });
                        comboBox4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ders5 = (String) comboBox4.getSelectedItem();
                            }
                        });
                        comboBoxInt4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                katsayi5 = (int) comboBoxInt4.getSelectedItem();
                            }
                        });

                        kaydet.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame sirala = new JFrame();
                                JTable siralama = new JTable();
                                DefaultTableModel model = new DefaultTableModel();
                                siralama.setModel(model);
                                double formul = 0;

                                model.addColumn("Öğrenci Id");
                                model.addColumn("Ad");
                                model.addColumn("Soyad");
                                model.addColumn("Formül Puanı");

                                String sorgu = "SELECT o.ogrenci_id, o.ogrenci_ad, o.ogrenci_soyad, COUNT(a.anlasma_talep_durumu) AS kabul_edilen_anlasma_sayisi\n" +
                                        "FROM ogrenci o\n" +
                                        "LEFT JOIN anlasma a ON o.ogrenci_id = a.ogrenci_id AND a.anlasma_talep_durumu = 'kabul edildi'\n" +
                                        "GROUP BY o.ogrenci_id, o.ogrenci_ad, o.ogrenci_soyad ";
                                ResultSet resultSet = sorgulama(sorgu);
                                try {
                                    while (resultSet.next()) {

                                        if (resultSet.getInt("kabul_edilen_anlasma_sayisi") == 0) {

                                            int ogrenci_id = resultSet.getInt("ogrenci_id");
                                            double a = harfNotAl(ogrenci_id, ders1);
                                            double b = harfNotAl(ogrenci_id, ders2);
                                            double c = harfNotAl(ogrenci_id, ders3);
                                            double d = harfNotAl(ogrenci_id, ders4);
                                            double x = harfNotAl(ogrenci_id, ders5);
                                            formul = ((a * (double) katsayi1) + (b * (double) katsayi2) + (c * (double) katsayi3) + (d * (double) katsayi4) + (x * (double) katsayi5)) / (double) (katsayi1 + katsayi2 + katsayi3 + katsayi4 + katsayi5);

                                            model.addRow(new Object[]{resultSet.getString("ogrenci_id"), resultSet.getString("ogrenci_ad"), resultSet.getString("ogrenci_soyad"), formul});

                                            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                                            siralama.setRowSorter(sorter);
                                            // Formül puanına göre sıralamayı ayarlayın (yüksekten düşüğe)
                                            sorter.setComparator(3, new Comparator<String>() {
                                                @Override
                                                public int compare(String s1, String s2) {
                                                    Double puan1 = Double.parseDouble(s1);
                                                    Double puan2 = Double.parseDouble(s2);
                                                    return puan2.compareTo(puan1);
                                                }
                                            });
                                        }
                                    }
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                                sirala.add(new JScrollPane(siralama));
                                sirala.setSize(600, 500);
                                sirala.setLocationRelativeTo(null);
                                sirala.setVisible(true);
                            }
                        });

                        formul_ekrani.setLayout(null);
                        formul_ekrani.setSize(600, 650);
                        formul_ekrani.setLocationRelativeTo(null);
                        formul_ekrani.setVisible(true);
                    }
                });

                atama_ekrani.setLayout(null);
                atama_ekrani.pack();
                atama_ekrani.setSize(360, 350);
                atama_ekrani.getContentPane().setBackground(Color.WHITE);
                atama_ekrani.setLocationRelativeTo(null);
                atama_ekrani.setVisible(true);
                atama_ekrani.setResizable(true);
            }
        });

        kontenjanVeDersBelirle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kontenjanVeDersBelirle();
            }
        });

        hocaSiniri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame sinir_koy = new JFrame("SINIR BELİRLE");
                JLabel hoca_sayisi = new JLabel("Öğrencilerin talepte bulunabileceği öğretim üyesi sayısı:");
                JTextField hoca_sayisi_text = new JTextField();
                JButton onay_butonu = new JButton("ONAYLA");

                hoca_sayisi.setBounds(50, 50, 350, 50);
                hoca_sayisi_text.setBounds(125, 100, 150, 30);
                onay_butonu.setBounds(150, 150, 100, 30);

                sinir_koy.add(hoca_sayisi);
                sinir_koy.add(hoca_sayisi_text);
                sinir_koy.add(onay_butonu);

                onay_butonu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int sinir = Integer.parseInt(hoca_sayisi_text.getText());
                        String sorgu = "INSERT INTO hoca_kisit VALUES(" + sinir + ")";
                        update(sorgu);
                        JOptionPane.showMessageDialog(null, "İşlem Onaylandı.");
                    }
                });

                sinir_koy.setLayout(null);
                sinir_koy.pack();
                sinir_koy.setSize(425, 300);
                sinir_koy.getContentPane().setBackground(Color.WHITE);
                sinir_koy.setLocationRelativeTo(null);
                sinir_koy.setVisible(true);
                sinir_koy.setResizable(true);
            }
        });

        mesajSiniri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame mesaj_sinir = new JFrame("MESAJ SINIR BELİRLE");
                JLabel karakter_sayisi = new JLabel("Mesajlaşma karakter sayısı:");
                JTextField karakter_sayisi_text = new JTextField();
                JButton onay_butonu = new JButton("ONAYLA");

                karakter_sayisi.setBounds(125, 50, 200, 50);
                karakter_sayisi_text.setBounds(125, 100, 150, 30);
                onay_butonu.setBounds(150, 150, 100, 30);

                mesaj_sinir.add(karakter_sayisi);
                mesaj_sinir.add(karakter_sayisi_text);
                mesaj_sinir.add(onay_butonu);

                onay_butonu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int sinir = Integer.parseInt(karakter_sayisi_text.getText());
                        String sorgu = "INSERT INTO mesaj_kisit(mesaj_karakter_sayisi) VALUES(" + sinir + ")";
                        update(sorgu);
                        JOptionPane.showMessageDialog(null, "İşlem Onaylandı.");
                    }
                });

                mesaj_sinir.setLayout(null);
                mesaj_sinir.pack();
                mesaj_sinir.setSize(425, 300);
                mesaj_sinir.getContentPane().setBackground(Color.WHITE);
                mesaj_sinir.setLocationRelativeTo(null);
                mesaj_sinir.setVisible(true);
                mesaj_sinir.setResizable(true);
            }
        });

        ogrenci_olustur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ogr_olustur = new JFrame("ÖĞRENCİ OLUŞTUR");
                JLabel ogrenci_sayisi = new JLabel("Oluşturmak istediğiniz öğrenci sayısı:");
                JTextField ogrenci_sayisi_text = new JTextField();
                JButton onay_butonu = new JButton("ONAYLA");

                ogrenci_sayisi.setBounds(100, 50, 350, 50);
                ogrenci_sayisi_text.setBounds(125, 100, 150, 30);
                onay_butonu.setBounds(150, 150, 100, 30);

                ogr_olustur.add(ogrenci_sayisi);
                ogr_olustur.add(ogrenci_sayisi_text);
                ogr_olustur.add(onay_butonu);

                Random random = new Random();
                onay_butonu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int adet = Integer.parseInt(ogrenci_sayisi_text.getText());
                        int id = 0;
                        int sifre = 2;

                        String sorgu = "SELECT ogrenci_id,ogrenci_sifre FROM ogrenci ORDER BY ogrenci_id DESC LIMIT 1";
                        ResultSet resultSet = sorgulama(sorgu);
                        try {
                            if (resultSet.next()) {
                                id = resultSet.getInt("ogrenci_id") + 1;
                                sifre = resultSet.getInt("ogrenci_sifre") + 1;
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        for (int i = 0; i < adet; i++) {
                            int rastgeleIsim = random.nextInt(50);
                            int rastgeleSoyisim = random.nextInt(50);
                            System.out.println(isim[rastgeleIsim]);
                            String ekle = "INSERT INTO ogrenci VALUES(" + (id + i) + ", '" + isim[rastgeleIsim] + "' , '" + soyisim[rastgeleSoyisim] + "' , '" + (sifre + i) + "')";
                            update(ekle);

                            int rastgeleDersAdet = random.nextInt(10, 30);

                            for (int j = 0; j < rastgeleDersAdet; j++) {
                                int rastgeleHarfNot = random.nextInt(7);
                                int rastgeleDersId = random.nextInt(1, 30);

                                String sorgu5 = "SELECT * FROM ogrenci_ders WHERE ogrenci_id = " + (id + i) + " AND ders_id = " + rastgeleDersId;
                                ResultSet resultSet1 = sorgulama(sorgu5);
                                try {
                                    if (!resultSet1.next()) {
                                        String dersekle = "INSERT INTO ogrenci_ders(ogrenci_id,ders_id,ogrenci_harfnot) VALUES(" + (id + i) + "," + rastgeleDersId + ", '" + harfNot[rastgeleHarfNot] + "')";
                                        update(dersekle);
                                    }
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        }
                        JOptionPane.showMessageDialog(null, adet + " Adet Öğrenci Oluşturuldu.");
                    }
                });

                ogr_olustur.setLayout(null);
                ogr_olustur.pack();
                ogr_olustur.setSize(425, 300);
                ogr_olustur.getContentPane().setBackground(Color.WHITE);
                ogr_olustur.setLocationRelativeTo(null);
                ogr_olustur.setVisible(true);
                ogr_olustur.setResizable(true);
            }
        });

        yonetici_paneli.setLayout(null);
        yonetici_paneli.pack();
        yonetici_paneli.setSize(1000, 600);
        yonetici_paneli.getContentPane().setBackground(Color.WHITE);
        yonetici_paneli.setVisible(true);
        yonetici_paneli.setResizable(true);
    }

    void kontenjanVeDersBelirle() {

        JFrame kontenjanVeDersBelirle = new JFrame();
        JTextField projeDersAdi = new JTextField();
        JTextField kontenjanBilgisi = new JTextField();
        JLabel projeAdi = new JLabel("Proje Ders Adı:");
        JLabel kontenjansayisi = new JLabel("Kontenjan Bilgisi:");
        JLabel hoca_sec = new JLabel("Öğretim üyesi: ");

        projeAdi.setBounds(150, 45, 150, 20);
        projeDersAdi.setBounds(150, 65, 150, 30);
        kontenjansayisi.setBounds(150, 115, 150, 20);
        kontenjanBilgisi.setBounds(150, 135, 150, 30);
        hoca_sec.setBounds(150, 185, 150, 20);

        String sorgu = "SELECT hoca_ad,hoca_soyad FROM hoca";
        ResultSet resultSet = sorgulama(sorgu);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(150, 205, 150, 30);

        try {
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString("hoca_ad") + " " + resultSet.getString("hoca_soyad"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        JButton verileriKaydet = new JButton("Verileri Kaydet");
        verileriKaydet.setBounds(150, 300, 150, 50);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String secilen_hoca = (String) comboBox.getSelectedItem();

                verileriKaydet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String ders_ad = projeDersAdi.getText();
                        int kontenjan = Integer.parseInt(kontenjanBilgisi.getText());
                        hoca_ad = secilen_hoca.substring(0, secilen_hoca.indexOf(' '));
                        hoca_soyad = secilen_hoca.substring(secilen_hoca.indexOf(' ') + 1);

                        System.out.println(hoca_ad + hoca_soyad);

                        String s = "INSERT INTO proje_dersler(pders_ad,hoca_id,pders_kontenjan,alan_ogrenci_sayisi) VALUES ('" + ders_ad + "',(SELECT hoca_id FROM hoca WHERE hoca_ad = '" + hoca_ad + "' and hoca_soyad = '" + hoca_soyad + "')," + kontenjan + ", 0)";
                        update(s);

                        JOptionPane.showMessageDialog(null, "Veri Kaydedildi");
                    }
                });
            }
        });

        kontenjanVeDersBelirle.add(verileriKaydet);
        kontenjanVeDersBelirle.add(comboBox);
        kontenjanVeDersBelirle.add(projeDersAdi);
        kontenjanVeDersBelirle.add(kontenjanBilgisi);
        kontenjanVeDersBelirle.add(projeAdi);
        kontenjanVeDersBelirle.add(kontenjansayisi);
        kontenjanVeDersBelirle.add(hoca_sec);

        kontenjanVeDersBelirle.setLayout(null);
        kontenjanVeDersBelirle.pack();
        kontenjanVeDersBelirle.setSize(450, 500);
        kontenjanVeDersBelirle.getContentPane().setBackground(Color.WHITE);
        kontenjanVeDersBelirle.setLocationRelativeTo(null);
        kontenjanVeDersBelirle.setVisible(true);
        kontenjanVeDersBelirle.setResizable(true);
    }

    void ogretim_uyesi_ekrani(int id, String ad, String soyad) {
        JFrame hoca_paneli = new JFrame("ÖĞRETİM ÜYESİ PANELİ");
        JButton ilgiAlaniSec = new JButton("İLGİ ALANI BELİRLE");
        JButton ogrenci_talep = new JButton("ÖĞRENCİ TALEP ET");
        JButton gelen_talep = new JButton("GELEN TALEPLERİ GÖRÜNTÜLE");
        JButton mesaj_gonder = new JButton("MESAJ GÖNDER");
        JButton gelen_mesaj = new JButton("GELEN MESAJLARI GÖRÜNTÜLE");
        JButton verilen_dersler = new JButton("VERİLEN DERSLERİ GÖRÜNTÜLE");

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\Eda Nur\\IdeaProjects\\yazlab1\\src\\teacher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);

        //Icon
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBounds(30, 30, 200, 200);

        JLabel numara = new JLabel(String.valueOf(id));
        numara.setBounds(85, 200, 200, 40);
        numara.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel ad_soyad = new JLabel(ad + " " + soyad);
        ad_soyad.setBounds(85, 220, 200, 50);
        ad_soyad.setFont(new Font("Arial", Font.BOLD, 15));

        hoca_paneli.add(label);
        hoca_paneli.add(ad_soyad);
        hoca_paneli.add(numara);
        hoca_paneli.add(ilgiAlaniSec);
        hoca_paneli.add(ogrenci_talep);
        hoca_paneli.add(gelen_talep);
        hoca_paneli.add(mesaj_gonder);
        hoca_paneli.add(gelen_mesaj);
        hoca_paneli.add(verilen_dersler);

        ilgiAlaniSec.setBounds(300, 50, 230, 50);
        ilgiAlaniSec.setBackground(new Color(238, 216, 174));
        ogrenci_talep.setBounds(300, 150, 230, 50);
        ogrenci_talep.setBackground(new Color(238, 216, 174));
        gelen_talep.setBounds(300, 250, 230, 50);
        gelen_talep.setBackground(new Color(238, 216, 174));
        mesaj_gonder.setBounds(550, 50, 230, 50);
        mesaj_gonder.setBackground(new Color(238, 216, 174));
        gelen_mesaj.setBounds(550, 150, 230, 50);
        gelen_mesaj.setBackground(new Color(238, 216, 174));
        verilen_dersler.setBounds(550, 250, 230, 50);
        verilen_dersler.setBackground(new Color(238, 216, 174));

        if (asama_atlama == false) {

            ilgiAlaniSec.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame ilgiAlanlari = new JFrame();
                    JButton kaydet = new JButton("KAYDET");
                    kaydet.setBounds(150, 250, 100, 30);

                    String sorgu = "SELECT ilgi_alani_tur FROM ilgi_alani";
                    ResultSet resultSet = sorgulama(sorgu);

                    JComboBox<String> comboBox = new JComboBox<>();
                    comboBox.setBounds(100, 50, 200, 30);

                    JComboBox<String> comboBox1 = new JComboBox<>();
                    comboBox1.setBounds(100, 100, 200, 30);

                    JComboBox<String> comboBox2 = new JComboBox<>();
                    comboBox2.setBounds(100, 150, 200, 30);

                    ilgiAlanlari.add(comboBox);
                    ilgiAlanlari.add(comboBox1);
                    ilgiAlanlari.add(comboBox2);
                    ilgiAlanlari.add(kaydet);

                    try {
                        while (resultSet.next()) {
                            comboBox.addItem(resultSet.getString("ilgi_alani_tur"));
                            comboBox1.addItem(resultSet.getString("ilgi_alani_tur"));
                            comboBox2.addItem(resultSet.getString("ilgi_alani_tur"));
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    comboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            secilenIlgiAlani1 = (String) comboBox.getSelectedItem();
                        }
                    });

                    comboBox1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            secilenIlgiAlani2 = (String) comboBox1.getSelectedItem();
                        }
                    });

                    comboBox2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            secilenIlgiAlani3 = (String) comboBox2.getSelectedItem();
                        }
                    });

                    kaydet.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String sorgu = "INSERT INTO hoca_ilgi_alani VALUES (" + id + "," + "(SELECT ilgi_alani_id FROM ilgi_alani WHERE ilgi_alani_tur = '" + secilenIlgiAlani1 + "'))";
                            update(sorgu);
                            String sorgu1 = "INSERT INTO hoca_ilgi_alani VALUES (" + id + "," + "(SELECT ilgi_alani_id FROM ilgi_alani WHERE ilgi_alani_tur = '" + secilenIlgiAlani2 + "'))";
                            update(sorgu1);
                            String sorgu2 = "INSERT INTO hoca_ilgi_alani VALUES (" + id + "," + "(SELECT ilgi_alani_id FROM ilgi_alani WHERE ilgi_alani_tur = '" + secilenIlgiAlani3 + "'))";
                            update(sorgu2);

                            JOptionPane.showMessageDialog(null, "Veri Kaydedildi.");
                        }
                    });

                    ilgiAlanlari.setLayout(null);
                    ilgiAlanlari.pack();
                    ilgiAlanlari.setSize(400, 400);
                    ilgiAlanlari.getContentPane().setBackground(Color.WHITE);
                    ilgiAlanlari.setLocationRelativeTo(null);
                    ilgiAlanlari.setVisible(true);
                    ilgiAlanlari.setResizable(true);
                }
            });

            ogrenci_talep.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("ÖĞRENCİ SEÇ");
                    JButton talep = new JButton("TALEPTE BULUN");
                    JTable table = new JTable();
                    DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "ÖĞRENCİ AD", "ÖĞRENCİ SOYAD"}, 0);
                    table.setModel(model);

                    String sorgu = "SELECT ogrenci_id,ogrenci_ad,ogrenci_soyad FROM ogrenci ";

                    ResultSet resultSet = sorgulama(sorgu);
                    try {
                        while (resultSet.next()) {
                            model.addRow(new Object[]{resultSet.getString("ogrenci_id"), resultSet.getString("ogrenci_ad"), resultSet.getString("ogrenci_soyad")});
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(model);
                    table.setRowSorter(rowSorter);

                    JTextField filterField = new JTextField(10);
                    filterField.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            String text = filterField.getText();
                            if (text.trim().length() == 0) {
                                rowSorter.setRowFilter(null);
                            } else {
                                rowSorter.setRowFilter(RowFilter.regexFilter(text));
                            }
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            String text = filterField.getText();
                            if (text.trim().length() == 0) {
                                rowSorter.setRowFilter(null);
                            } else {
                                rowSorter.setRowFilter(RowFilter.regexFilter(text));
                            }
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            // Plain text components do not fire these events
                        }
                    });

                    JPanel panel = new JPanel();
                    panel.add(filterField);
                    frame.add(talep);
                    talep.setBounds(200, 300, 200, 30);

                    talep.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame ders_ad = new JFrame();
                            JTextField ad = new JTextField();
                            JLabel ad_label = new JLabel("Ders adı: ");
                            JButton sec = new JButton("SEÇ");

                            ad_label.setBounds(50, 50, 150, 20);
                            ad.setBounds(50, 70, 150, 30);
                            sec.setBounds(75, 120, 100, 50);

                            ders_ad.add(ad_label);
                            ders_ad.add(ad);
                            ders_ad.add(sec);

                            int secilenSatirIndeksi = table.getSelectedRow();
                            int ogrenci_id = Integer.parseInt((String) model.getValueAt(secilenSatirIndeksi, 0));

                            sec.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String dersAdi = ad.getText();

                                    try {
                                        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi", "postgres", "123456");
                                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO anlasma (ogrenci_id, hoca_id, ders_id, anlasma_talep_durumu,talep_eden_kisi_id) VALUES (?, ?, (SELECT pders_id FROM proje_dersler WHERE pders_ad = '" + dersAdi + "' AND hoca_id = ?), 'beklemede',?)");
                                        preparedStatement.setInt(1, ogrenci_id);
                                        preparedStatement.setInt(2, id);
                                        preparedStatement.setInt(3, id);
                                        preparedStatement.setInt(4, id);
                                        preparedStatement.executeUpdate();

                                        JOptionPane.showMessageDialog(null, "Talepte Bulundunuz.");

                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });

                            ders_ad.setLayout(null);
                            ders_ad.pack();
                            ders_ad.setSize(250, 250);
                            ders_ad.getContentPane().setBackground(Color.WHITE);
                            ders_ad.setLocationRelativeTo(null);
                            ders_ad.setVisible(true);
                            ders_ad.setResizable(true);
                        }
                    });

                    frame.add(panel, "North");
                    frame.add(new JScrollPane(table), "Center");
                    frame.setSize(600, 500);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });

            gelen_talep.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame ogrenci_bilgi = new JFrame();
                    JTable ogrenciListesi = new JTable();
                    JButton ogrenciyi_sec = new JButton("Öğrenci Bilgileri");
                    JButton derse_kaydet = new JButton("Derse Kaydet");
                    JButton reddet = new JButton("Reddet");
                    JButton formul = new JButton("Formül Uygula");

                    DefaultTableModel model = new DefaultTableModel(new Object[]{"ÖĞRENCİ ID", "ÖGRENCİ AD", "SOYAD", "DERS ID", "DERS", "KONTENJAN", "ALAN ÖĞRENCİ SAYISI", "TALEP DURUMU"}, 0);
                    ogrenciListesi.setModel(model);

                    String sorgu = "SELECT o.ogrenci_id, o.ogrenci_ad, o.ogrenci_soyad, p.pders_id, p.pders_ad, p.pders_kontenjan, p.alan_ogrenci_sayisi, a.anlasma_talep_durumu, a.talep_eden_kisi_id " +
                            "FROM anlasma AS a " +
                            "INNER JOIN ogrenci AS o ON a.ogrenci_id = o.ogrenci_id " +
                            "INNER JOIN proje_dersler AS p ON a.ders_id = p.pders_id " +
                            "INNER JOIN hoca AS h ON p.hoca_id = h.hoca_id " +
                            "WHERE h.hoca_id = " + id;

                    ResultSet resultSet = sorgulama(sorgu);
                    try {
                        while (resultSet.next()) {
                            if (resultSet.getInt("talep_eden_kisi_id") != id) {
                                model.addRow(new Object[]{resultSet.getInt("ogrenci_id"), resultSet.getString("ogrenci_ad"), resultSet.getString("ogrenci_soyad"), resultSet.getInt("pders_id"), resultSet.getString("pders_ad"), resultSet.getInt("pders_kontenjan"), resultSet.getInt("alan_ogrenci_sayisi"), resultSet.getString("anlasma_talep_durumu")});
                            }
                        }

                        resultSet.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    ogrenci_bilgi.add(ogrenciyi_sec);
                    ogrenciyi_sec.setBounds(50, 400, 150, 30);
                    ogrenci_bilgi.add(derse_kaydet);
                    derse_kaydet.setBounds(250, 400, 150, 30);
                    ogrenci_bilgi.add(reddet);
                    reddet.setBounds(450, 400, 150, 30);
                    ogrenci_bilgi.add(formul);
                    formul.setBounds(650, 400, 150, 30);

                    ogrenciyi_sec.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame ogrenciListe = new JFrame();
                            JTable dersListesi = new JTable();

                            DefaultTableModel model1 = new DefaultTableModel(new Object[]{"DERS", "HARF NOTU"}, 0);
                            dersListesi.setModel(model1);

                            int secilenSatirIndeksi = ogrenciListesi.getSelectedRow();
                            Object ogrenci_id = model.getValueAt(secilenSatirIndeksi, 0);

                            String sorgu1 = "SELECT d.ders_ad, od.ogrenci_harfnot\n" +
                                    "FROM ogrenci AS o\n" +
                                    "INNER JOIN ogrenci_ders AS od ON o.ogrenci_id = od.ogrenci_id\n" +
                                    "INNER JOIN dersler AS d ON od.ders_id = d.ders_id\n" +
                                    "WHERE o.ogrenci_id = " + Integer.parseInt(ogrenci_id.toString());

                            ResultSet resultSet1 = sorgulama(sorgu1);
                            try {
                                while (resultSet1.next()) {
                                    model1.addRow(new Object[]{resultSet1.getString("ders_ad"), resultSet1.getString("ogrenci_harfnot")});
                                }
                                resultSet1.close();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                            ogrenciListe.add(new JScrollPane(dersListesi));
                            ogrenciListe.setSize(600, 500);
                            ogrenciListe.setLocationRelativeTo(null);
                            ogrenciListe.setVisible(true);
                        }
                    });

                    derse_kaydet.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            int secilenSatirIndeksi = ogrenciListesi.getSelectedRow();
                            Object ogrenci_id = model.getValueAt(secilenSatirIndeksi, 0);
                            Object ders_id = model.getValueAt(secilenSatirIndeksi, 3);
                            Object kontenjan = model.getValueAt(secilenSatirIndeksi, 5);
                            Object alan_ogrenci_sayisi = model.getValueAt(secilenSatirIndeksi, 6);
                            Object talep_durumu = model.getValueAt(secilenSatirIndeksi, 7);

                            if (talep_durumu.toString().equals("beklemede") && ((int) kontenjan >= (int) alan_ogrenci_sayisi)) {
                                String sorgu = "UPDATE anlasma\n" +
                                        "SET anlasma_talep_durumu = 'kabul edildi'\n" +
                                        "WHERE ogrenci_id = " + ogrenci_id + " AND ders_id = " + ders_id;
                                update(sorgu);

                                String sorgu2 = "UPDATE proje_dersler\n" +
                                        "SET alan_ogrenci_sayisi = alan_ogrenci_sayisi + 1\n" +
                                        "WHERE pders_id = " + ders_id + " AND hoca_id = " + id;
                                update(sorgu2);

                                JOptionPane.showMessageDialog(null, "Öğrenci derse kaydedildi.");
                            } else if (kontenjan == alan_ogrenci_sayisi) {
                                JOptionPane.showMessageDialog(null, "Üzgünüm, dersin kontenjanı dolmuş :(");
                            }
                        }
                    });

                    reddet.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            int secilenSatirIndeksi = ogrenciListesi.getSelectedRow();
                            Object ogrenci_id = model.getValueAt(secilenSatirIndeksi, 0);
                            Object ders_id = model.getValueAt(secilenSatirIndeksi, 3);
                            Object talep_durumu = model.getValueAt(secilenSatirIndeksi, 7);

                            if (talep_durumu.toString().equals("beklemede")) {
                                String sorgu = "UPDATE anlasma\n" +
                                        "SET anlasma_talep_durumu = 'reddedildi'\n" +
                                        "WHERE ogrenci_id = " + ogrenci_id + " AND ders_id = " + ders_id;
                                update(sorgu);

                                JOptionPane.showMessageDialog(null, "Öğrenci reddedildi");
                            }
                        }
                    });

                    formul.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame formul_ekrani = new JFrame();
                            JButton kaydet = new JButton("KAYDET");
                            JLabel label = new JLabel("FORMÜL");
                            label.setBounds(275, 50, 150, 50);

                            JComboBox<String> comboBox = new JComboBox<>();
                            comboBox.setBounds(50, 100, 250, 30);

                            JComboBox<String> comboBox1 = new JComboBox<>();
                            comboBox1.setBounds(50, 200, 250, 30);

                            JComboBox<String> comboBox2 = new JComboBox<>();
                            comboBox2.setBounds(50, 300, 250, 30);

                            JComboBox<Integer> comboBoxInt = new JComboBox<>();
                            comboBoxInt.setBounds(350, 100, 150, 30);

                            JComboBox<Integer> comboBoxInt1 = new JComboBox<>();
                            comboBoxInt1.setBounds(350, 200, 150, 30);

                            JComboBox<Integer> comboBoxInt2 = new JComboBox<>();
                            comboBoxInt2.setBounds(350, 300, 150, 30);

                            kaydet.setBounds(250, 400, 100, 50);

                            formul_ekrani.add(comboBox);
                            formul_ekrani.add(comboBox1);
                            formul_ekrani.add(comboBox2);
                            formul_ekrani.add(comboBoxInt);
                            formul_ekrani.add(comboBoxInt1);
                            formul_ekrani.add(comboBoxInt2);
                            formul_ekrani.add(kaydet);
                            formul_ekrani.add(label);

                            String sorgu2 = "SELECT ders_ad FROM dersler";
                            ResultSet resultSet1 = sorgulama(sorgu2);
                            try {
                                while (resultSet1.next()) {
                                    comboBox.addItem(resultSet1.getString("ders_ad"));
                                    comboBox1.addItem(resultSet1.getString("ders_ad"));
                                    comboBox2.addItem(resultSet1.getString("ders_ad"));
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                            for (int i = 1; i < 10; i++) {
                                comboBoxInt.addItem(i);
                                comboBoxInt1.addItem(i);
                                comboBoxInt2.addItem(i);
                            }


                            comboBox.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ders1 = (String) comboBox.getSelectedItem();
                                }
                            });
                            comboBoxInt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    katsayi1 = (int) comboBoxInt.getSelectedItem();
                                }
                            });
                            comboBox1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ders2 = (String) comboBox1.getSelectedItem();
                                }
                            });
                            comboBoxInt1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    katsayi2 = (int) comboBoxInt1.getSelectedItem();
                                }
                            });
                            comboBox2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ders3 = (String) comboBox2.getSelectedItem();
                                }
                            });
                            comboBoxInt2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    katsayi3 = (int) comboBoxInt2.getSelectedItem();
                                }
                            });

                            kaydet.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ResultSet resultSet2 = sorgulama(sorgu);
                                    try {
                                        while (resultSet2.next()) {
                                            int ogrenci_id = resultSet2.getInt("ogrenci_id");
                                            double a = harfNotAl(ogrenci_id, ders1);
                                            double b = harfNotAl(ogrenci_id, ders2);
                                            double c = harfNotAl(ogrenci_id, ders3);

                                            double formul = ((a * (double) katsayi1) + (b * (double) katsayi2) + (c * (double) katsayi3)) / (double) (katsayi1 + katsayi2 + katsayi3);
                                            model.addColumn("FORMÜL PUANI", new Object[]{formul});
                                        }
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            });

                            formul_ekrani.setLayout(null);
                            formul_ekrani.setSize(600, 600);
                            formul_ekrani.setLocationRelativeTo(null);
                            formul_ekrani.setVisible(true);
                        }
                    });

                    ogrenci_bilgi.add(new JScrollPane(ogrenciListesi));
                    ogrenci_bilgi.setSize(850, 500);
                    ogrenci_bilgi.setLocationRelativeTo(null);
                    ogrenci_bilgi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ogrenci_bilgi.setVisible(true);
                }
            });

            mesaj_gonder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame mesaj = new JFrame();
                    JLabel kime_label = new JLabel("Gönderilen Öğrencinin Id Numarası:");
                    JTextField kime_text = new JTextField();
                    JLabel mesaj_label = new JLabel("Mesaj:");
                    JTextArea mesaj_text = new JTextArea();
                    JButton gonder = new JButton("GÖNDER");

                    mesaj.add(kime_label);
                    kime_label.setBounds(50, 50, 300, 30);
                    mesaj.add(kime_text);
                    kime_text.setBounds(50, 80, 150, 30);
                    mesaj.add(mesaj_label);
                    mesaj_label.setBounds(50, 150, 150, 30);
                    mesaj.add(mesaj_text);
                    mesaj_text.setBounds(50, 180, 300, 300);
                    mesaj.add(gonder);
                    gonder.setBounds(75, 520, 100, 30);

                    gonder.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int karakter_sayisi = 0;
                            String sorgu = "SELECT * FROM mesaj_kisit";
                            ResultSet resultSet = sorgulama(sorgu);

                            try {
                                while (resultSet.next()) {
                                    karakter_sayisi = resultSet.getInt("mesaj_karakter_sayisi");
                                }
                                resultSet.close();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                            int ogrenci_id = Integer.parseInt(kime_text.getText());
                            String mesaj_icerik = mesaj_text.getText();

                            if (mesaj_icerik.length() > karakter_sayisi) {
                                JOptionPane.showMessageDialog(null, "KARAKTER SAYISINI AŞTINIZ");
                            } else {
                                try {
                                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi", "postgres", "123456");
                                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mesaj(gonderen_id,alan_kisi_id,mesaj_icerik) VALUES(?,?,?)");
                                    preparedStatement.setInt(1, id);
                                    preparedStatement.setInt(2, ogrenci_id);
                                    preparedStatement.setString(3, mesaj_icerik);
                                    preparedStatement.executeUpdate();
                                    JOptionPane.showMessageDialog(null, "Mesaj Gönderimi Başarılı.");
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    });

                    mesaj.setLayout(null);
                    mesaj.pack();
                    mesaj.getContentPane().setBackground(Color.LIGHT_GRAY);
                    mesaj.setSize(600, 650);
                    mesaj.setLocationRelativeTo(null);
                    mesaj.setVisible(true);
                }
            });

            gelen_mesaj.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame gelen_mesajlar = new JFrame();
                    JTable kisiListesi = new JTable();
                    JButton mesaj_gor = new JButton("Mesajı Gör");

                    DefaultTableModel model = new DefaultTableModel();
                    kisiListesi.setModel(model);

                    model.addColumn("Mesaj Id");
                    model.addColumn("Öğrenci Ad");
                    model.addColumn("Soyad");

                    String sorgu = "SELECT m.mesaj_id,o.ogrenci_ad,o.ogrenci_soyad,m.mesaj_icerik " +
                            "FROM mesaj AS m " +
                            "INNER JOIN ogrenci AS o ON o.ogrenci_id = m.gonderen_id " +
                            "WHERE m.alan_kisi_id = " + id;
                    ResultSet resultSet = sorgulama(sorgu);
                    try {
                        while (resultSet.next()) {
                            model.addRow(new Object[]{resultSet.getString("mesaj_id"), resultSet.getString("ogrenci_ad"), resultSet.getString("ogrenci_soyad")});
                            mesaj_icerik = resultSet.getString("mesaj_icerik");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);

                    }
                    gelen_mesajlar.add(mesaj_gor);
                    mesaj_gor.setBounds(200, 300, 150, 30);

                    mesaj_gor.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame mesaj_ekrani = new JFrame();
                            JLabel yazi = new JLabel(mesaj_icerik);

                            mesaj_ekrani.add(yazi);
                            yazi.setBounds(50, 50, 250, 250);

                            mesaj_ekrani.setSize(300, 300);
                            mesaj_ekrani.setLocationRelativeTo(null);
                            mesaj_ekrani.setVisible(true);
                        }
                    });

                    gelen_mesajlar.add(new JScrollPane(kisiListesi));
                    gelen_mesajlar.setSize(600, 500);
                    gelen_mesajlar.setLocationRelativeTo(null);
                    gelen_mesajlar.setVisible(true);
                }
            });

            verilen_dersler.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame ders_ekrani = new JFrame();
                    JTable dersListesi = new JTable();

                    DefaultTableModel model = new DefaultTableModel();
                    dersListesi.setModel(model);

                    model.addColumn("Dersler");
                    model.addColumn("Kontenjan");

                    String sorgu = "SELECT pders_ad, pders_kontenjan FROM proje_dersler WHERE hoca_id = " + id;
                    ResultSet resultSet = sorgulama(sorgu);

                    try {
                        while (resultSet.next()) {
                            model.addRow(new Object[]{resultSet.getString("pders_ad"), resultSet.getString("pders_kontenjan")});
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);

                    }
                    ders_ekrani.add(new JScrollPane(dersListesi));
                    ders_ekrani.setSize(600, 500);
                    ders_ekrani.setLocationRelativeTo(null);
                    ders_ekrani.setVisible(true);
                }
            });

        } else if (asama_atlama == true) {
            JOptionPane.showMessageDialog(null, "İKİNCİ AŞAMAYA GEÇİLDİ İŞLEM YAPILAMAMAKTADIR!");
        }

        hoca_paneli.setLayout(null);
        hoca_paneli.pack();
        hoca_paneli.setSize(1200, 600);
        hoca_paneli.getContentPane().setBackground(Color.WHITE);
        hoca_paneli.setVisible(true);
        hoca_paneli.setResizable(true);
    }

    void ogrenci_ekrani(int id, String ad, String soyad) {
        JFrame ogrenci_paneli = new JFrame("ÖĞRENCİ PANELİ");
        ogrenci_paneli.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JButton pdf_yukle = new JButton("Transkript Yükle");
        JButton ders_gor = new JButton("Dersleri Listele");
        JButton ders_sec = new JButton("Proje Dersi Seç");
        JButton gelen_talep = new JButton("Gelen Talepler");
        JButton mesaj = new JButton("Mesaj Gönder");
        JButton gelen_mesaj = new JButton("Gelen Mesajları Gör");
        JButton islemler = new JButton("Ders İşlemlerini Göster");
        JButton alinan_dersler = new JButton("Alınan Dersler");

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\Eda Nur\\IdeaProjects\\yazlab1\\src\\ogrenci.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);

        //Icon
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBounds(30, 30, 200, 200);

        //Numara
        JLabel numara = new JLabel(String.valueOf(id));
        numara.setBounds(85, 200, 200, 40);
        numara.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel ad_soyad = new JLabel(ad + " " + soyad);
        ad_soyad.setBounds(85, 220, 200, 50);
        ad_soyad.setFont(new Font("Arial", Font.BOLD, 15));

        ogrenci_paneli.add(label);
        ogrenci_paneli.add(numara);
        ogrenci_paneli.add(ad_soyad);

        //Butonlar
        pdf_yukle.setBounds(300, 50, 200, 50);
        pdf_yukle.setBackground(new Color(238, 216, 174));
        ders_gor.setBounds(300, 150, 200, 50);
        ders_gor.setBackground(new Color(238, 216, 174));
        ders_sec.setBounds(300, 250, 200, 50);
        ders_sec.setBackground(new Color(238, 216, 174));
        gelen_talep.setBounds(300, 350, 200, 50);
        gelen_talep.setBackground(new Color(238, 216, 174));
        mesaj.setBounds(550, 50, 200, 50);
        mesaj.setBackground(new Color(238, 216, 174));
        gelen_mesaj.setBounds(550, 150, 200, 50);
        gelen_mesaj.setBackground(new Color(238, 216, 174));
        islemler.setBounds(550, 250, 200, 50);
        islemler.setBackground(new Color(238, 216, 174));
        alinan_dersler.setBounds(550, 350, 200, 50);
        alinan_dersler.setBackground(new Color(238, 216, 174));

        ogrenci_paneli.add(pdf_yukle);
        ogrenci_paneli.add(ders_gor);
        ogrenci_paneli.add(ders_sec);
        ogrenci_paneli.add(gelen_talep);
        ogrenci_paneli.add(mesaj);
        ogrenci_paneli.add(gelen_mesaj);
        ogrenci_paneli.add(islemler);
        ogrenci_paneli.add(alinan_dersler);

        if (asama_atlama == false) {
        pdf_yukle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame transkript_ad = new JFrame();
                JTextField ad = new JTextField();
                JLabel ad_label = new JLabel("Dosya adı: ");
                JButton yukle = new JButton("YÜKLE");

                ad_label.setBounds(50, 50, 150, 20);
                ad.setBounds(50, 70, 150, 30);
                yukle.setBounds(75, 120, 100, 50);

                transkript_ad.add(ad_label);
                transkript_ad.add(ad);
                transkript_ad.add(yukle);

                yukle.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String dosyaAdi = ad.getText();
                        transkriptOkuma(dosyaAdi);
                        ogrenciDersKaydet(id);
                    }
                });

                transkript_ad.setLayout(null);
                transkript_ad.pack();
                transkript_ad.setSize(250, 250);
                transkript_ad.getContentPane().setBackground(Color.WHITE);
                transkript_ad.setLocationRelativeTo(null);
                transkript_ad.setVisible(true);
                transkript_ad.setResizable(true);
            }
        });

        ders_gor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ders_ekrani = new JFrame();
                JTable dersListesi = new JTable();

                DefaultTableModel model = new DefaultTableModel();
                dersListesi.setModel(model);

                model.addColumn("Dersler");
                model.addColumn("Harf Notları");

                String sorgu = "SELECT d.ders_ad, od.ogrenci_harfnot FROM dersler d JOIN ogrenci_ders od ON d.ders_id = od.ders_id WHERE od.ogrenci_id = " + id;
                ResultSet resultSet = sorgulama(sorgu);

                try {
                    while (resultSet.next()) {
                        model.addRow(new Object[]{resultSet.getString("ders_ad"), resultSet.getString("ogrenci_harfnot")});
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
                ders_ekrani.add(new JScrollPane(dersListesi));
                ders_ekrani.setSize(600, 500);
                ders_ekrani.setLocationRelativeTo(null);
                ders_ekrani.setVisible(true);
            }
        });

        ders_sec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("PROJE DERSİ SEÇ");
                JButton talep = new JButton("TALEPTE BULUN");
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel(new Object[]{"AD-SOYAD", "DERS", "KONTENJAN", "İLGİ ALANLARI"}, 0);
                table.setModel(model);

                String sorgu = "SELECT hoca.hoca_ad, hoca.hoca_soyad, proje_dersler.pders_ad, pders_kontenjan, STRING_AGG(ilgi_alani.ilgi_alani_tur, ', ') AS ilgi_alanlari " +
                        "FROM hoca INNER JOIN hoca_ilgi_alani ON hoca.hoca_id = hoca_ilgi_alani.hoca_id  " +
                        "INNER JOIN ilgi_alani ON hoca_ilgi_alani.ilgi_alani_id = ilgi_alani.ilgi_alani_id " +
                        "INNER JOIN proje_dersler ON hoca.hoca_id = proje_dersler.hoca_id " +
                        "GROUP BY hoca.hoca_ad, hoca.hoca_soyad, proje_dersler.pders_ad,proje_dersler.pders_kontenjan";

                ResultSet resultSet = sorgulama(sorgu);
                try {
                    while (resultSet.next()) {
                        model.addRow(new Object[]{resultSet.getString("hoca_ad") + " " + resultSet.getString("hoca_soyad"), resultSet.getString("pders_ad"), resultSet.getInt("pders_kontenjan"), resultSet.getString("ilgi_alanlari")});
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(model);
                table.setRowSorter(rowSorter);

                JTextField filterField = new JTextField(10);
                filterField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        String text = filterField.getText();
                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        String text = filterField.getText();
                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        // Plain text components do not fire these events
                    }
                });

                JPanel panel = new JPanel();
                panel.add(filterField);
                frame.add(talep);
                talep.setBounds(650, 400, 200, 30);

                talep.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int secilenSatirIndeksi = table.getSelectedRow();
                        Object hocaAdSoyad = model.getValueAt(secilenSatirIndeksi, 0);
                        Object ders = model.getValueAt(secilenSatirIndeksi, 1);

                        String ad = hocaAdSoyad.toString().substring(0, hocaAdSoyad.toString().indexOf(" "));
                        String soyad = hocaAdSoyad.toString().substring(hocaAdSoyad.toString().indexOf(" ") + 1);

                        System.out.println(ad + soyad);

                        String sor = "SELECT hoca_id FROM hoca WHERE hoca_ad = '" + ad + "' and hoca_soyad = '" + soyad + "'";
                        ResultSet rs = sorgulama(sor);
                        int hoca_id = 0;
                        try {
                            while (rs.next()) {
                                hoca_id = rs.getInt("hoca_id");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        String s = "SELECT pders_id FROM proje_dersler WHERE pders_ad = '" + ders.toString() + "' AND hoca_id = " + hoca_id;
                        ResultSet resultSet1 = sorgulama(s);
                        int pders_id = 0;
                        try {
                            while (resultSet1.next()) {
                                pders_id = resultSet1.getInt("pders_id");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        if (secilenSatirIndeksi != -1) {
                            try {
                                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi", "postgres", "123456");
                                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO anlasma (ogrenci_id, hoca_id, ders_id, anlasma_talep_durumu,talep_eden_kisi_id) VALUES (?, ?, ?, 'beklemede',?)");
                                preparedStatement.setInt(1, id);
                                preparedStatement.setInt(2, hoca_id);
                                preparedStatement.setInt(3, pders_id);
                                preparedStatement.setInt(4, id);
                                preparedStatement.executeUpdate();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null, "Talepte Bulundunuz.");

                        } else {
                        }
                    }
                });

                frame.add(panel, "North");
                frame.add(new JScrollPane(table), "Center");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        gelen_talep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame gelen_talepler = new JFrame();
                JTable talepListesi = new JTable();
                JButton reddet = new JButton("TALEBİ REDDET");
                JButton kabul_et = new JButton("TALEBİ KABUL ET");

                DefaultTableModel model = new DefaultTableModel();
                talepListesi.setModel(model);

                gelen_talepler.add(reddet);
                reddet.setBounds(50, 400, 200, 30);
                gelen_talepler.add(kabul_et);
                kabul_et.setBounds(300, 400, 200, 30);

                model.addColumn("Talep Id");
                model.addColumn("Hoca Id");
                model.addColumn("Hoca Ad");
                model.addColumn("Hoca Soyad");
                model.addColumn("Proje Dersi Id");
                model.addColumn("Proje Dersi Adı");
                model.addColumn("Anlasma Durumu");

                String sorgu = "SELECT a.anlasma_talep_id, h.hoca_id, h.hoca_ad, h.hoca_soyad, pd.pders_id, pd.pders_ad, a.anlasma_talep_durumu, a.talep_eden_kisi_id  " +
                        "FROM anlasma a " +
                        "INNER JOIN hoca h ON a.hoca_id = h.hoca_id " +
                        "INNER JOIN proje_dersler pd ON a.ders_id = pd.pders_id " +
                        "WHERE a.ogrenci_id = " + id;

                ResultSet resultSet = sorgulama(sorgu);

                try {
                    while (resultSet.next()) {
                        if (resultSet.getInt("talep_eden_kisi_id") != id) {
                            model.addRow(new Object[]{resultSet.getString("anlasma_talep_id"), resultSet.getInt("hoca_id"), resultSet.getString("hoca_ad"), resultSet.getString("hoca_soyad"), resultSet.getInt("pders_id"), resultSet.getString("pders_ad"), resultSet.getString("anlasma_talep_durumu")});
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }

                reddet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int secilenSatirIndeksi = talepListesi.getSelectedRow();
                        Object anlasma_id = model.getValueAt(secilenSatirIndeksi, 0);
                        Object anlasma_durumu = model.getValueAt(secilenSatirIndeksi, 6);

                        if (anlasma_durumu.toString().equals("beklemede")) {
                            String sorgu = "UPDATE anlasma\n" +
                                    "SET anlasma_talep_durumu = 'reddedildi'\n" +
                                    "WHERE anlasma_talep_id = " + Integer.parseInt((String) anlasma_id);
                            update(sorgu);
                            JOptionPane.showMessageDialog(null, "Talep reddedildi");

                        } else if (anlasma_durumu.toString().equals("kabul edildi")) {
                            JOptionPane.showMessageDialog(null, "Üzgünüm talep kabul edilmiş, geri çekilemez");
                        }
                    }
                });

                kabul_et.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int secilenSatirIndeksi = talepListesi.getSelectedRow();
                        Object anlasma_id = model.getValueAt(secilenSatirIndeksi, 0);
                        Object hoca_id = model.getValueAt(secilenSatirIndeksi, 1);
                        Object ders_id = model.getValueAt(secilenSatirIndeksi, 4);
                        Object anlasma_durumu = model.getValueAt(secilenSatirIndeksi, 6);

                        if (anlasma_durumu.toString().equals("beklemede")) {
                            String sorgu = "UPDATE anlasma\n" +
                                    "SET anlasma_talep_durumu = 'kabul edildi'\n" +
                                    "WHERE anlasma_talep_id = " + anlasma_id;
                            update(sorgu);

                            String sorgu2 = "UPDATE proje_dersler\n" +
                                    "SET alan_ogrenci_sayisi = alan_ogrenci_sayisi + 1\n" +
                                    "WHERE pders_id = " + ders_id + " AND hoca_id = " + hoca_id;
                            update(sorgu2);

                            JOptionPane.showMessageDialog(null, "Derse kaydedildiniz.");

                        } else if (anlasma_durumu.toString().equals("reddedildi")) {
                            JOptionPane.showMessageDialog(null, "Üzgünüm talep reddedilmiş, kabul edilemez");
                        }
                    }
                });

                gelen_talepler.add(new JScrollPane(talepListesi));
                gelen_talepler.setSize(600, 550);
                gelen_talepler.setLocationRelativeTo(null);
                gelen_talepler.setVisible(true);
            }
        });

        mesaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame mesaj = new JFrame();
                JLabel kime_label = new JLabel("Gönderilen Öğretim Üyesinin Id Numarası:");
                JTextField kime_text = new JTextField();
                JLabel mesaj_label = new JLabel("Mesaj:");
                JTextArea mesaj_text = new JTextArea();
                JButton gonder = new JButton("GÖNDER");

                mesaj.add(kime_label);
                kime_label.setBounds(50, 50, 300, 30);
                mesaj.add(kime_text);
                kime_text.setBounds(50, 80, 150, 30);
                mesaj.add(mesaj_label);
                mesaj_label.setBounds(50, 150, 150, 30);
                mesaj.add(mesaj_text);
                mesaj_text.setBounds(50, 180, 300, 300);
                mesaj.add(gonder);
                gonder.setBounds(75, 520, 100, 30);

                gonder.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int karakter_sayisi = 0;
                        String sorgu = "SELECT * FROM mesaj_kisit";
                        ResultSet resultSet = sorgulama(sorgu);

                        try {
                            while (resultSet.next()) {
                                karakter_sayisi = resultSet.getInt("mesaj_karakter_sayisi");
                            }
                            resultSet.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        int hoca_id = Integer.parseInt(kime_text.getText());
                        String mesaj_icerik = mesaj_text.getText();

                        if (mesaj_icerik.length() > karakter_sayisi) {
                            JOptionPane.showMessageDialog(null, "KARAKTER SAYISINI AŞTINIZ");
                        } else {
                            try {
                                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi", "postgres", "123456");
                                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mesaj(gonderen_id,alan_kisi_id,mesaj_icerik) VALUES(?,?,?)");
                                preparedStatement.setInt(1, id);
                                preparedStatement.setInt(2, hoca_id);
                                preparedStatement.setString(3, mesaj_icerik);
                                preparedStatement.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Mesaj Gönderimi Başarılı.");
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });

                mesaj.setLayout(null);
                mesaj.pack();
                mesaj.getContentPane().setBackground(Color.LIGHT_GRAY);
                mesaj.setSize(600, 650);
                mesaj.setLocationRelativeTo(null);
                mesaj.setVisible(true);
            }
        });

        gelen_mesaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame gelen_mesajlar = new JFrame();
                JTable kisiListesi = new JTable();
                JButton mesaj_gor = new JButton("Mesajı Gör");

                DefaultTableModel model = new DefaultTableModel();
                kisiListesi.setModel(model);

                model.addColumn("Mesaj Id");
                model.addColumn("Hoca Ad");
                model.addColumn("Soyad");

                String sorgu = "SELECT m.mesaj_id,h.hoca_ad,h.hoca_soyad,m.mesaj_icerik " +
                        "FROM mesaj AS m " +
                        "INNER JOIN hoca AS h ON h.hoca_id = m.gonderen_id " +
                        "WHERE m.alan_kisi_id = " + id;
                ResultSet resultSet = sorgulama(sorgu);
                try {
                    while (resultSet.next()) {
                        model.addRow(new Object[]{resultSet.getString("mesaj_id"), resultSet.getString("hoca_ad"), resultSet.getString("hoca_soyad")});
                        mesaj_icerik = resultSet.getString("mesaj_icerik");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
                gelen_mesajlar.add(mesaj_gor);
                mesaj_gor.setBounds(200, 300, 150, 30);

                mesaj_gor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame mesaj_ekrani = new JFrame();
                        JLabel yazi = new JLabel(mesaj_icerik);

                        mesaj_ekrani.add(yazi);
                        yazi.setBounds(50, 50, 250, 250);

                        mesaj_ekrani.setSize(300, 300);
                        mesaj_ekrani.setLocationRelativeTo(null);
                        mesaj_ekrani.setVisible(true);
                    }
                });

                gelen_mesajlar.add(new JScrollPane(kisiListesi));
                gelen_mesajlar.setSize(600, 500);
                gelen_mesajlar.setLocationRelativeTo(null);
                gelen_mesajlar.setVisible(true);
            }
        });

        islemler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame islemler = new JFrame();
                JTable talepListesi = new JTable();
                JButton iptal = new JButton("TALEBİ GERİ ÇEK");

                DefaultTableModel model = new DefaultTableModel();
                talepListesi.setModel(model);
                islemler.add(iptal);
                iptal.setBounds(200, 400, 200, 30);

                model.addColumn("Talep Id");
                model.addColumn("Hoca Ad");
                model.addColumn("Hoca Soyad");
                model.addColumn("Proje Dersi Adı");
                model.addColumn("Anlasma Durumu");

                String sorgu = "SELECT a.anlasma_talep_id, h.hoca_ad, h.hoca_soyad, pd.pders_ad, a.anlasma_talep_durumu, a.talep_eden_kisi_id  " +
                        "FROM anlasma a " +
                        "INNER JOIN hoca h ON a.hoca_id = h.hoca_id " +
                        "INNER JOIN proje_dersler pd ON a.ders_id = pd.pders_id " +
                        "WHERE a.ogrenci_id = " + id;

                ResultSet resultSet = sorgulama(sorgu);

                try {
                    while (resultSet.next()) {
                        if (resultSet.getInt("talep_eden_kisi_id") == id) {
                            model.addRow(new Object[]{resultSet.getString("anlasma_talep_id"), resultSet.getString("hoca_ad"), resultSet.getString("hoca_soyad"), resultSet.getString("pders_ad"), resultSet.getString("anlasma_talep_durumu")});
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }

                iptal.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int secilenSatirIndeksi = talepListesi.getSelectedRow();
                        Object anlasma_id = model.getValueAt(secilenSatirIndeksi, 0);
                        Object anlasma_durumu = model.getValueAt(secilenSatirIndeksi, 4);

                        if (anlasma_durumu.toString().equals("beklemede")) {
                            String sor = "DELETE FROM anlasma WHERE anlasma_talep_id = " + Integer.parseInt((String) anlasma_id);
                            update(sor);
                            JOptionPane.showMessageDialog(null, "Talep Geri Çekildi.");
                        } else if (anlasma_durumu.toString().equals("kabul edildi")) {
                            JOptionPane.showMessageDialog(null, "Üzgünüm talep kabul edilmiş, geri çekilemez");
                        }
                    }
                });

                islemler.add(new JScrollPane(talepListesi));
                islemler.setSize(600, 500);
                islemler.setLocationRelativeTo(null);
                islemler.setVisible(true);
            }
        });

        alinan_dersler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame alinan_dersler_ekrani = new JFrame();
                JTable alinan_ders_listesi = new JTable();

                DefaultTableModel model = new DefaultTableModel();
                alinan_ders_listesi.setModel(model);

                model.addColumn("Talep Id");
                model.addColumn("Hoca Ad");
                model.addColumn("Hoca Soyad");
                model.addColumn("Proje Dersi Adı");
                model.addColumn("Anlasma Durumu");

                String sorgu = "SELECT a.anlasma_talep_id, h.hoca_ad, h.hoca_soyad, pd.pders_ad, a.anlasma_talep_durumu  " +
                        "FROM anlasma a " +
                        "INNER JOIN hoca h ON a.hoca_id = h.hoca_id " +
                        "INNER JOIN proje_dersler pd ON a.ders_id = pd.pders_id " +
                        "WHERE a.ogrenci_id = " + id;

                ResultSet resultSet = sorgulama(sorgu);

                try {
                    while (resultSet.next()) {
                        if (resultSet.getString("anlasma_talep_durumu").equals("kabul edildi")) {
                            model.addRow(new Object[]{resultSet.getString("anlasma_talep_id"), resultSet.getString("hoca_ad"), resultSet.getString("hoca_soyad"), resultSet.getString("pders_ad"), resultSet.getString("anlasma_talep_durumu")});
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }

                alinan_dersler_ekrani.add(new JScrollPane(alinan_ders_listesi));
                alinan_dersler_ekrani.setSize(600, 500);
                alinan_dersler_ekrani.setLocationRelativeTo(null);
                alinan_dersler_ekrani.setVisible(true);
            }
        });
        }else if(asama_atlama ==true){
            JOptionPane.showMessageDialog(null,"İKİNCİ AŞAMAYA GEÇİLDİ, İŞLEM YAPILAMAMAKTADIR!");
        }

        ogrenci_paneli.setLayout(null);
        ogrenci_paneli.pack();
        ogrenci_paneli.setSize(1200, 600);
        ogrenci_paneli.getContentPane().setBackground(Color.WHITE);
        ogrenci_paneli.setVisible(true);
        ogrenci_paneli.setResizable(true);

    }

    void dersKaydet(String text){
        Scanner scanner = new Scanner(new StringReader(text));
        String satir;
        while(scanner.hasNext()) {
            satir = scanner.nextLine();
            alinanDersler.add(satir.substring(0,satir.length()-3));
            harfNotlari.add(satir.substring(satir.length()-3,satir.length()));
        }
        for (int i = 0; i < alinanDersler.size(); i++) {
            System.out.println(alinanDersler.get(i) + "--" + harfNotlari.get(i));
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi","postgres","123456");
            for (String dersAdi : alinanDersler) {
                // Dersin daha önce eklenip eklenmediğini kontrol et
                if (!dersVarMi(connection, dersAdi)) {
                    // Ders daha önce eklenmemiş, yeni dersi ekleyin
                    dersEkle(connection, dersAdi);
                    System.out.println("Ders başarıyla eklendi.");
                } else {
                    System.out.println("Ders zaten mevcut.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    double harfNotAl(int ogrenci_id, String ders) {
        double harfNotCeviri = 0;
        String sor = "SELECT od.ogrenci_harfnot " +
                "FROM ogrenci_ders AS od " +
                "INNER JOIN dersler AS d ON od.ders_id = d.ders_id " +
                "WHERE d.ders_ad = '" + ders + "' AND od.ogrenci_id = " + ogrenci_id;

        ResultSet resultSet = sorgulama(sor);
        try {
            if (resultSet.next()) {
                String harfNot = resultSet.getString("ogrenci_harfnot");
                System.out.println(harfNot);
                if (harfNot.equals("AA") || harfNot.equals(" AA")) {
                    harfNotCeviri = 4.0;
                } else if (harfNot.equals("BA") || harfNot.equals(" BA")) {
                    harfNotCeviri = 3.5;
                } else if (harfNot.equals("BB") || harfNot.equals(" BB")) {
                    harfNotCeviri = 3.0;
                } else if (harfNot.equals("CB") || harfNot.equals(" CB")) {
                    harfNotCeviri = 2.5;
                } else if (harfNot.equals("CC") || harfNot.equals(" CC")) {
                    harfNotCeviri = 2.0;
                } else if (harfNot.equals("DC") || harfNot.equals(" DC")) {
                    harfNotCeviri = 1.5;
                } else if (harfNot.equals("FF") || harfNot.equals(" FF")) {
                    harfNotCeviri = 0.0;
                }
                System.out.println(harfNotCeviri);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return harfNotCeviri;
    }

    private static boolean dersVarMi(Connection connection, String dersAdi) throws SQLException {

        String sorgu = "SELECT COUNT(*) FROM dersler WHERE ders_ad = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {
            preparedStatement.setString(1, dersAdi);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int dersSayisi = resultSet.getInt(1);
                return dersSayisi > 0;
            }
        }
        return false;
    }

    private static void dersEkle(Connection connection, String dersAdi) throws SQLException {
        String sorgu = "INSERT INTO dersler (ders_ad) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {
            preparedStatement.setString(1, dersAdi);
            preparedStatement.executeUpdate();
        }
    }

    void ogrenciDersKaydet(int ogrenci_id){

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi","postgres","123456");
            String sorgu = "INSERT INTO ogrenci_ders (ogrenci_id,ders_id,ogrenci_harfnot) VALUES (?,(SELECT ders_id FROM dersler WHERE ders_ad = ?),?)";
            PreparedStatement prepared_Statement = connection.prepareStatement(sorgu);

            for (int i = 0; i < alinanDersler.size(); i++) {
                String dersAdi = alinanDersler.get(i);
                String harfNot = harfNotlari.get(i);

                prepared_Statement.setInt(1, ogrenci_id);
                prepared_Statement.setString(2, dersAdi);
                prepared_Statement.setString(3, harfNot);

                prepared_Statement.executeUpdate();
            }
            prepared_Statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void transkriptOkuma(String dosyaAdi){
        Tesseract tesseract = new Tesseract();
        String text=null;
        try {
            tesseract.setDatapath("C:\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
            tesseract.setLanguage("tur");
            text = tesseract.doOCR(new File("C:\\Users\\Eda Nur\\Desktop\\"+dosyaAdi+".pdf"));
            System.out.println(text);
            System.out.println("-------------------------------------------");
            dersKaydet(text);

        } catch (TesseractException ex) {
            ex.printStackTrace();
        }
    }
    void update(String sql_sorgu) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi","postgres","123456");
            Statement ifade = connection.createStatement();
            ifade.executeUpdate(sql_sorgu);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    ResultSet sorgulama(String sql_sorgu) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kayitSistemi","postgres","123456");
            Statement ifade = connection.createStatement();
            return ifade.executeQuery(sql_sorgu);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
