package br.com.devtools.apidevtools.buscauserpass;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import org.junit.Test;

public class TestBuscaUserPass {

	
	@Test
	public void teste() {
		
		String path = System.getProperty("user.home")+"/userpass.db";
		
		try {
			
			Properties prop = new Properties();
			
			File f = new File(path);
			if (f.exists()) {
				prop.load(new FileReader(path));
			} else {
				prop.setProperty("username", "");
				prop.setProperty("password", "");
				prop.store(new FileWriter(f), "First save");
			}
			
			if (prop.getProperty("username")==null || prop.getProperty("username").equals("")
				|| prop.getProperty("password")==null || prop.getProperty("password").equals("")) {
				System.out.println("Informe um usuário e senha para o banco no arquivo: " + path);
			}
			
		} catch (Exception e) {
			System.out.println("Arquivo "+path+" não existe");
		}
		
		assertTrue(true);
		
	}
	
}
