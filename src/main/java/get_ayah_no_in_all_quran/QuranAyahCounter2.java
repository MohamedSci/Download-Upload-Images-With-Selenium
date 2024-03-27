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

	public static void main(String[] args) throws FileNotFoundException, IOException, CsvException {
		List<String[]> rows1 = null;
		List<String[]> rows2 = null;

		// Provide the dataset of the ayahs count in each Surah
			CSVReader reader1 = new CSVReader(new FileReader("surah_ayah_count.csv"));
			rows1 = reader1.readAll();
			System.out.println("---- rows1: " + rows1);
			// Provide the dataset of the ayah in Surah
			CSVReader reader2 = new CSVReader(new FileReader("quranic_roqia_surah_ayah_dataset.csv"));
			rows2 = reader2.readAll();
			System.out.println("---- rows22: " + rows2);

				for (String[] row2 : rows2) {
					int surah = Integer.parseInt(row2[0]);
					System.out.println("---- rows22 surah: " + surah);

					int ayah = Integer.parseInt(row2[1]);
					System.out.println("---- rows22 ayah: " + ayah);

					int totalAyahs = getTotalAyahs(rows1, surah, ayah);
					System.out.println("---- rows22 totalAyahs: " + totalAyahs);

					String txtAppend = surah + "," + ayah + "," + totalAyahs;
					System.out.println("---- rows22 txtAppend: " + txtAppend);
					
					// Store the new Data in new file
					AppendTextToFile.AppendTextToFileFun(txtAppend,"C:\\Users\\moham\\OneDrive\\Desktop\\21-3\\quranic_roqia_global_ayah_dataset.csv");
					System.out.println("The Ayah number " + ayah + " in Surah " + surah + " is the " + totalAyahs
							+ "th Ayah in the whole Quran.");
				}
			}
		}

