package com.iamchiwon.translater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class Main {

	public static void main(String[] args) {
		try {
			GoogleTranslater translater = new GoogleTranslater();

			translater.from(Locale.ENGLISH);
			translater.to(Locale.FRENCH);

			BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.print("<< ");
				String line = bin.readLine();
				if (line.equals("exit") || line.equals("quit"))
					break;

				String result = translater.translate(line);
				System.out.println(">> " + result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
