package project.mogakco.domain.member.entity.member;

public enum AuthProvider {
	GOOGLE,GITHUB;

	public static AuthProvider changeStringAuthProvider(String providerType){
		if (providerType.equals("GITHUB")) return AuthProvider.GITHUB;
		else return AuthProvider.GOOGLE;
	}
}
