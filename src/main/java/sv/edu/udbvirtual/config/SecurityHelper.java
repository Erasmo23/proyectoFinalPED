package sv.edu.udbvirtual.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class SecurityHelper {

	private SecurityHelper(){
        throw new IllegalStateException("Utility Class");
    }

    public static final SecurityUserDetails getLoggedInUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object o = auth.getPrincipal();
            if (o instanceof SecurityUserDetails) {
                return ((SecurityUserDetails) o);
            } else if (auth instanceof PreAuthenticatedAuthenticationToken) {
                PreAuthenticatedAuthenticationToken ptoken = (PreAuthenticatedAuthenticationToken) auth;
                return (SecurityUserDetails) ptoken.getDetails();
            } else {
                return null;
            }
        }
        return null;
    }
	
	
}