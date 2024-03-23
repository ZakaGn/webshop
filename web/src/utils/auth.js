const TOKEN_KEY = 'token'
const ROLE_KEY = 'role'

const auth = {
	saveToken: (token) => {
		localStorage.setItem(TOKEN_KEY, token)
	},

	getToken: () => {
		return localStorage.getItem(TOKEN_KEY)
	},

	removeToken: () => {
		localStorage.removeItem(TOKEN_KEY)
		localStorage.removeItem(ROLE_KEY)
	},

	saveRole: (role) => {
		localStorage.setItem(ROLE_KEY, role)
	},

	getRole: () => {
		return localStorage.getItem(ROLE_KEY)
	},

	removeRole: () => {
		localStorage.removeItem(ROLE_KEY)
	},

	isAuthenticated: () => {
		return !!localStorage.getItem(TOKEN_KEY)
	}

}

export default auth
