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

	register: async userData => {
		console.log("register")
		const response = await axiosInstance.post('/user/register', userData)
		return response.data
	},

	fetchUserData: async() => {
		console.log("fetchUserData")
		const response = await axiosInstance.get('/user/data')
		return response.data
	},

	updateUser: async userData => {
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

	addCategory: async category => {
		console.log("addCategory")
		const response = await axiosInstance.post('/products/category', category)
		return response.data
	},

	updateCategory: async category => {
		console.log("updateCategory", category)
		const response = await axiosInstance.put(`/products/category`, category)
		return response.data
	},

	deleteCategory: async id => {
		console.log("deleteCategory")
		const response = await axiosInstance.delete(`/products/category/${id}`)
		return response.data
	},

	searchProductByName: async name => {
		console.log("searchProductByName")
		const response = await axiosInstance.get(`/products/search/${name}`)
		console.log("response:", response)
		return response.data
	},

	fetchProducts: async() => {
		console.log("fetchProducts")
		const response = await axiosInstance.get('/products')
		console.log("response:", response)
		return response.data
	},

	addProduct: async product => {
		console.log("addProduct")
		const response = await axiosInstance.post('/products', product)
		return response.data
	},

	updateProduct: async product => {
		console.log("updateProduct", product)
		const response = await axiosInstance.put(`/products`, product)
		return response.data
	},

	deleteProduct: async id => {
		console.log("deleteProduct")
		const response = await axiosInstance.delete(`/products/${id}`)
		return response.data
	},

	fetchOrders: async() => {
		console.log("fetchOrders")
		const response = await axiosInstance.get('/orders')
		console.log("response:", response)
		return response.data
	},

	fetchOrderById: async id => {
		console.log(`fetchOrder ${id}`)
		const response = await axiosInstance.get(`/orders/${id}`)
		console.log("response:", response)
		return response.data
	},

	createOrder: async order => {
		console.log("createOrder", order)
		const response = await axiosInstance.post('/orders', order)
		console.log("response:", response)
		return response.data
	},

	updateOrder: async order => {
		console.log("updateOrder", order)
		const response = await axiosInstance.put(`/orders`, order)
		console.log("response:", response)
		return response.data
	},

	deleteOrder: async id => {
		console.log("deleteOrder")
		const response = await axiosInstance.delete(`/orders/${id}`)
		console.log("response:", response)
		return response.data
	}

}

export default api
