package com.iamchiwon.translater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;

public class GoogleTranslater {

	public static void main(String[] args) {
		try {
			GoogleTranslater translater = new GoogleTranslater();
			translater.from(Locale.KOREAN);
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

	private Locale fromLang, toLang;

	public void from(final Locale fromLang) {
		this.fromLang = fromLang;
	}

	public void to(final Locale toLang) {
		this.toLang = toLang;
	}

	public String translate(final String fromText) throws IOException {
		final String urlTemp = "https://translate.google.co.kr/translate_a/single?client=t&sl=auto&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&otf=2&ssel=0&tsel=0&kc=2&tk=365832|232076";
		final String encoded = URLEncoder.encode(fromText, "UTF-8");
		final String fromLang = "&hl=" + this.fromLang.getLanguage();
		final String toLang = "&tl=" + this.toLang.getLanguage();
		final String query = "&q=" + encoded;

		String response = httpRequest(urlTemp + fromLang + toLang + query);

		// parse response
		String toText = parse(response);

		return toText;
	}

	private String httpRequest(String url) throws IOException {
		URL server = new URL(url);
		URLConnection comm = server.openConnection();
		comm.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

		BufferedReader bin = new BufferedReader(new InputStreamReader(comm.getInputStream()));
		StringBuffer sf = new StringBuffer();
		String line;
		while ((line = bin.readLine()) != null) {
			sf.append(line);
		}
		bin.close();

		return sf.toString();
	}

	private String parse(String source) {
		int index = source.indexOf('\"');
		int index2 = source.indexOf('\"', index + 1);
		String parsed = source.substring(index + 1, index2);
		return parsed;
	}
}
