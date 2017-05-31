package br.com.devtools.apidevtools.resource.person.rules;

import java.util.Base64;

import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.rest.RestSessao;

public class UserToken {

	public UserToken() {
	}
	
	public void split(String userToken, RestSessao sessao) {

		if (userToken==null || userToken.equals("")) {
			return;
		}
		
		userToken = new String(Base64.getDecoder().decode(userToken));
		
		if (userToken==null || userToken.equals("")) {
			return;
		}
		
		String[] arr = userToken.split(";");
		if (arr.length!=2) {
			return;
		}
		
		sessao.setNameAcess(arr[0]);
		sessao.setPassword(arr[1]);
		
	}
	
	public String userCrypto(String nomeAcess, String password) throws Exception {
		
		Crypto crypto = new Crypto();
		return (crypto.criptografar(nomeAcess) + crypto.criptografar(password));
		
	}
	
	public String userCrypto(RestSessao sessao) throws Exception {
		return this.userCrypto(sessao.getNameAcess(), sessao.getPassword());
	}
	
}
