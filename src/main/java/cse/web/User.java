package cse.web;


public class User {
		private int userId ;
		private String username;
		private String email;
		private int userType;
		
		public User(int userId, String username, String email,int userType) {
			super();
			this.userId = userId;
			this.username = username;
			this.email = email;
			this.userType = userType;
			
		}

		public int getUserId() {
			return userId;
		}

		public String getUsername() {
			return username;
		}

		public String getEmail() {
			return email;
		}


		public int getUserType() {
			return userType;
		}
		
		
		
		
}
	

