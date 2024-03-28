import axiosInstance from 'utils/axiosInstance'
import auth from 'utils/auth'

export const api = {
	login: async(email, password) => {
		console.log("login")
		const response = await axiosInstance.post('/user/login', {email, password})
		const {token, role} = response.data
		auth.saveToken(token)
		auth.saveRole(role)
		auth.saveEmail(email)
		return response.data
	},

	register: async(userData) => {
		console.log("register")
		const response = await axiosInstance.post('/user/register', userData)
		return response.data
	},

	fetchUserData: async() => {
		console.log("fetchUserData")
		const response = await axiosInstance.get('/user/data')
		return response.data
	},

	updateUser: async(userData) => {
		console.log("updateUser")
		const response = await axiosInstance.post('/user/update', userData)
		return response.data
	},

	fetchCategories: async() => {
		console.log("fetchCategories")
		const response = await axiosInstance.get('/products/category')
		console.log("response:", response)
		return response.data
	},

	addCategory: async(category) => {
		console.log("addCategory")
		const response = await axiosInstance.post('/products/category', category)
		return response.data
	},

	updateCategory: async(id, category) => {
		console.log("updateCategory")
		const response = await axiosInstance.put(`/products/category'/${id}`, category)
		return response.data
	},

	deleteCategory: async(id) => {
		console.log("deleteCategory")
		const response = await axiosInstance.delete(`/products/category'/${id}`)
		return response.data
	}
}

export default api
