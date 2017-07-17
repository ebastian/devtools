package br.com.devtools.apidevtools.resource.user.acess.artifact;

public class LoginToken {

	private String hash;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public LoginToken() {
	}

	public LoginToken(String hash) {
		this.hash = hash;
	}
	
}
