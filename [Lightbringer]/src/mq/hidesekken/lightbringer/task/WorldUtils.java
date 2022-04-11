package mq.hidesekken.lightbringer.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WorldUtils {
	
	public void deleteWorld(File fichier) {
		if(fichier.exists()) {
			
			File files[] = fichier.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					deleteWorld(files[i]);
				}else {
					files[i].delete();
					
				}
			}
		}
		
	}
	
	public void copyFolder(File src, File dest) throws IOException {
		
		if(src.isDirectory()) {
			if(!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for(String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		}else {
			
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			
			byte[] b = new byte[1024];
			int length;
			
			while((length = in.read()) > 0) {
				out.write(b, 0, length);
			}
			
			in.close();
			out.close();
			
			
		}
		
		
	}

}
