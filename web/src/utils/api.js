import axiosInstance from 'utils/axiosInstance'
import auth from 'utils/auth'

export const api = {
	login: async(email, password) => {
		const response = await axiosInstance.post('/user/login', {email, password})
		const {token, role} = response.data
		auth.saveToken(token)
		auth.saveRole(role)
		return response.data
	},

	register: async(userData) => {
		const response = await axiosInstance.post('/user/register', userData)
		return response.data
	},

	fetchUserData: async() => {
		const response = await axiosInstance.get('/user/data')
		return response.data
	},

	updateUserProfile: async(userData) => {
		const response = await axiosInstance.post('/user/update', userData)
		return response.data
	},

	fetchCategories: async() => {
		const response = await axiosInstance.get('/products/category')
		console.log("response:", response)
		return response.data
	},

	addCategory: async(category) => {
		const response = await axiosInstance.post('/products/category', category)
		return response.data
	},

	updateCategory: async(id, category) => {
		const response = await axiosInstance.put(`/products/category'/${id}`, category)
		return response.data
	},

	deleteCategory: async(id) => {
		const response = await axiosInstance.delete(`/products/category'/${id}`)
		return response.data
	}
}

export default api
