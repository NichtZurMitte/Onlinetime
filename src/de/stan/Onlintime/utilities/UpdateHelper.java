package de.stan.Onlintime.utilities;

import de.stan.Onlintime.main.Main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class UpdateHelper {
	
	String ip = "http://87.141.58.76/public";

	Main plugin;
	public UpdateHelper(String Name, double Version, Main plugin) {
		this.plugin = plugin;
		try {
			System.out.println(plugin.PREFIX + " Checking for newer versions...");
			String DL = sendPost(Name, Version);
			DownloadHelper(DL, Name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Download
	public void DownloadHelper(String TILEURL, String Name) {
		if(!TILEURL.startsWith("O")) {
			System.out.println(plugin.PREFIX + " Looks like i'm outdated...");
			try {
			
				System.out.println(plugin.PREFIX + " Removing current Version...");
				
				File file = new File("plugins/" + Name + ".jar"); 
				if(file.exists()) {
					download(this.ip + TILEURL, Name + "-newest.jar");
					file.delete();
				}else {
					File file2 = new File("plugins/" + Name + "-newest.jar"); 
					if(file2.exists()) {
						download(this.ip + TILEURL, Name + ".jar");
						file2.delete();
					}
				}
			
				System.out.println(plugin.PREFIX + " Now reload the server so the update can take effect...");
				
			}catch(Exception e) {
				e.printStackTrace();
				
			}
			
		}else {
			System.out.println(plugin.PREFIX + " i'm up-to-date!");
		}
		

	}
	
	private void download(String URL, String Name) throws Exception {
		System.out.println(plugin.PREFIX + " Downloading newest Version...");
		
		URL website = new URL(URL);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		System.out.println(plugin.PREFIX + " Downloading newest Version...");
		@SuppressWarnings("resource")
		FileOutputStream fos = new FileOutputStream("plugins/" + Name);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);			
		System.out.println(plugin.PREFIX + " Downloaded newest version...");
	}
	
	//HTTP POST request
    private String sendPost(String Name, double Version) throws Exception {
    	if(plugin.config.getBoolean("development")) {
    		this.ip = "http://127.0.0.1";
    	}
    	String url = this.ip + "/plugin-api/public";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

       //add reuqest header
        con.setRequestMethod("POST");

        String urlParameters = "lookup=true&name=" + Name + "&version=" + Version;

       //Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
       //return result
        return response.toString();
    }

			
}
