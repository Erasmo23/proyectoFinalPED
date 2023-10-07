package sv.edu.udbvirtual.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecurityUserDetails implements UserDetails {

	@Getter
	private static final long serialVersionUID = 1064178928280083346L;

	private Integer idUsuario;

	private String correoUsuario;

	private String nombreCompleto;

	private List<Auth> authorities = new ArrayList<>();
	private List<String> roles = new ArrayList<>();

	public void addGrantedAuthority(String name) {
		Auth auth = new Auth();
		auth.setName(name);
		if (this.authorities.stream().noneMatch(o -> o.getName().equals(name))) {
			this.authorities.add(auth);
		}

	}

	public void addRole(String name) {
		roles.add(name);
	}

	public List<String> getRoles() {
		return this.roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.correoUsuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Setter
	@Getter
	public class Auth implements GrantedAuthority {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private String name;

		@Override
		public String getAuthority() {
			return this.name;
		}

	}

}