package get_ayah_no_in_all_quran;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;


public class QuranAyahCounter2 {

    public static int getTotalAyahs(List<String[]> rows, int surah, int ayah) {
        int totalAyahs = 0;
        boolean foundSurah = false;
        for (String[] row : rows) {
            int currentSurah = Integer.parseInt(row[0]);
            int ayahsCount = Integer.parseInt(row[1]);
            if (currentSurah == surah) {
                foundSurah = true;
                if (ayah <= ayahsCount) {
                    totalAyahs += ayah;
                    break;
                } else {
                    totalAyahs += ayahsCount;
                    ayah -= ayahsCount;
                }
            } else if (foundSurah) {
                totalAyahs += ayah;
                break;
            } else {
                totalAyahs += ayahsCount;
            }
        }
        return totalAyahs;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<String[]> rows1 = null;
        try (CSVReader reader1 = new CSVReader(new FileReader("surah_ayah_count.csv"))) {
				rows1 = reader1.readAll();
			} catch (CsvException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try (CSVReader reader2 = new CSVReader(new FileReader("ayah_doaa.csv"))) {
	            List<String[]> rows2 = null;
				try {
					rows2 = reader2.readAll();
				} catch (CsvException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (String[] row2 : rows2) {
	            int surah = Integer.parseInt(row2[0]);
	            int ayah = Integer.parseInt(row2[1]);
	            int totalAyahs = getTotalAyahs(rows1, surah, ayah);
	            String txtAppend= surah+","+ ayah +","+ totalAyahs;
	            AppendTextToFile.AppendTextToFileFun(txtAppend);
            System.out.println("The Ayah number " + ayah + " in Surah " + surah + " is the " + totalAyahs + "th Ayah in the whole Quran.");
				}
				} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
