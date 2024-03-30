import api from "utils/api"
import auth from "utils/auth";

const userService = {
	login : async(email, password) => api.login(email, password),
	register : async(userData) => api.register(userData),
	logout: () => {auth.removeToken();auth.removeRole()},
	fetchUser : async() => api.fetchUserData(),
	updateUser : async(userData) => api.updateUser(userData),
	getEmail : () => auth.getEmail(),
}

export default userService
