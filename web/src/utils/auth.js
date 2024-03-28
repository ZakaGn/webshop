const TOKEN_KEY = 'token'
const ROLE_KEY = 'role'

const auth = {
	saveToken: token => localStorage.setItem(TOKEN_KEY, token),
	getToken: () => localStorage.getItem(TOKEN_KEY),
	removeToken: () => localStorage.removeItem(TOKEN_KEY),
	saveRole: role => localStorage.setItem(ROLE_KEY, role),
	getRole: () => localStorage.getItem(ROLE_KEY),
	removeRole: () => localStorage.removeItem(ROLE_KEY),
	isAuthenticated: () => !!localStorage.getItem(TOKEN_KEY),
	getEmail: () => localStorage.getItem('email'),
	saveEmail: email => localStorage.setItem('email', email),
	removeEmail: () => localStorage.removeItem('email')
}

export default auth
