//package get_ayah_no_in_all_quran;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;
//
//
//public class QuranAyahCounter {
//
//    public static int getTotalAyahs(List<String[]> rows, int surah, int ayah) {
//        int totalAyahs = 0;
//        boolean foundSurah = false;
//        for (String[] row : rows) {
//            int currentSurah = Integer.parseInt(row[0]);
//            int ayahsCount = Integer.parseInt(row[1]);
//            if (currentSurah == surah) {
//                foundSurah = true;
//                if (ayah <= ayahsCount) {
//                    totalAyahs += ayah;
//                    break;
//                } else {
//                    totalAyahs += ayahsCount;
//                    ayah -= ayahsCount;
//                }
//            } else if (foundSurah) {
//                totalAyahs += ayah;
//                break;
//            } else {
//                totalAyahs += ayahsCount;
//            }
//        }
//        return totalAyahs;
//    }
//
//    public static void main(String[] args) {
//        try (CSVReader reader = new CSVReader(new FileReader("surah_ayah_count.csv"))) {
//            List<String[]> rows = null;
//			try {
//				rows = reader.readAll();
//			} catch (CsvException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Enter the Surah number: ");
//            int surah = scanner.nextInt();
//            System.out.print("Enter the Ayah number in this Surah: ");
//            int ayah = scanner.nextInt();
//            int totalAyahs = getTotalAyahs(rows, surah, ayah);
//            System.out.println("The Ayah number " + ayah + " in Surah " + surah + " is the " + totalAyahs + "th Ayah in the whole Quran.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
