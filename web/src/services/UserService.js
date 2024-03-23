import {api} from "utils/api"
import auth from "utils/auth";

export const userService = {
	login : async(email, password) => {return api.login(email, password)},
	register : async(userData) => {return api.register(userData)},
	logout: () => {auth.removeToken();auth.removeRole()}
}
