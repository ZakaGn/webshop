const TOKEN_KEY = 'token';

const auth = {
	saveToken: (token) => {localStorage.setItem(TOKEN_KEY, token)},
	getToken: () => {return localStorage.getItem(TOKEN_KEY)},
	removeToken: () => {localStorage.removeItem(TOKEN_KEY)},
	isAuthenticated: () => {return !!localStorage.getItem(TOKEN_KEY)},
	logout: () => {
		localStorage.removeItem(TOKEN_KEY)
		window.location.href = '/login'
	}
}

export default auth
