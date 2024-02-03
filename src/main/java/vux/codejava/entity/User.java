package vux.codejava.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;



@Entity(name = "users")
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true, length = 45)
	@NotBlank(message = "Tên đăng nhập không được trống")
	@Length(min = 5, max = 45, message = "Tên đăng nhập phải có từ 5 - 45 ký tự")
	private String username;
	
	@Column(name = "password", nullable = false, length = 64)
	@NotBlank(message = "Mật khẩu không được trống")
	@Length(min = 5, max = 64, message = "Mật khẩu phải có từ 6 - 64 ký tự")
	private String password;
	
	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Email không hợp lệ")
	@NotBlank(message = "Email không được trống")
	private String email;
	
	@Column(name = "role")
	private Integer role = 3;//default role user
	
	@Column(name = "enabled")	
	private boolean enabled = true;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "status_shift")
	private boolean statusShift = false;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "name")
	private String name;
	
	//@OneToMany(cascade = CascadeType.ALL,
	//		fetch = FetchType.LAZY,
	//		mappedBy = "user")
	//@JsonManagedReference
	//private Set<OperationalStatistics> operationalStatistics =new HashSet<OperationalStatistics>();
	/*
	@ManyToMany(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)	
	private Set<Role> roles = new HashSet<>();
	*/
	public User() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/*
	public Set<OperationalStatistics> getOperationalStatistics() {
		return operationalStatistics;
	}

	public void setOperationalStatistics(Set<OperationalStatistics> operationalStatistics) {
		this.operationalStatistics = operationalStatistics;
	}
	*/
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public boolean isStatusShift() {
		return statusShift;
	}

	public void setStatusShift(boolean statusShift) {
		this.statusShift = statusShift;
	}
	
	
	/*
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	*/
	
	
}
