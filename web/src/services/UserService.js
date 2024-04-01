import api from "utils/api"
import auth from "utils/auth";

const userService = {
	login : async(email, password) => api.login(email, password),
	register : async(userData) => api.register(userData),
	logout: () => {auth.removeToken();auth.removeRole()},

	getUser : async() => api.getUser(),
	updateUser : async(userData) => api.updateUser(userData),

	getEmail : () => auth.getEmail(),
	getRole : () => auth.getRole(),
	isAuthenticated : () => auth.isAuthenticated()
}

export default userService
