import axiosInstance from 'utils/axiosInstance'
import auth from 'utils/auth'

const api = {
	login: async(email, password) => {
		console.log("api.login")
		const response = await axiosInstance.post('/user/login', {email, password})
		const {token, role} = response.data
		auth.saveToken(token)
		auth.saveRole(role)
		auth.saveEmail(email)
		return response.data
	},

	register: async userData => {
		console.log("api.register")
		const response = await axiosInstance.post('/user/register', userData)
		console.log("api.register.response:", response)
		return response.data
	},

	fetchUserData: async() => {
		console.log("api.fetchUserData")
		const response = await axiosInstance.get('/user/data')
		console.log("api.fetchUserData.response:", response)
		return response.data
	},

	updateUser: async userData => {
		console.log("api.updateUser")
		const response = await axiosInstance.post('/user/update', userData)
		console.log("api.updateUser.response:", response)
		return response.data
	},

	fetchCategories: async() => {
		console.log("api.fetchCategories")
		const response = await axiosInstance.get('/products/category')
		console.log("api.fetchCategories.response:", response)
		return response.data
	},

	addCategory: async category => {
		console.log("api.addCategory")
		const response = await axiosInstance.post('/products/category', category)
		console.log("api.addCategory.response:", response)
		return response.data
	},

	updateCategory: async category => {
		console.log("api.updateCategory", category)
		const response = await axiosInstance.put(`/products/category`, category)
		console.log("api.updateCategory.response:", response)
		return response.data
	},

	deleteCategory: async id => {
		console.log("api.deleteCategory")
		const response = await axiosInstance.delete(`/products/category/${id}`)
		console.log("api.deleteCategory.response:", response)
		return response.data
	},

	searchProductByName: async name => {
		console.log("api.searchProductByName")
		const response = await axiosInstance.get(`/products/search/${name}`)
		console.log("api.searchProductByName.response:", response)
		return response.data
	},

	fetchProducts: async() => {
		console.log("api.fetchProducts")
		const response = await axiosInstance.get('/products')
		console.log("api.fetchProducts.response:", response)
		return response.data
	},

	addProduct: async product => {
		console.log("api.addProduct")
		const response = await axiosInstance.post('/products', product)
		console.log("api.addProduct.response:", response)
		return response.data
	},

	updateProduct: async product => {
		console.log("api.updateProduct", product)
		const response = await axiosInstance.put(`/products`, product)
		console.log("api.updateProduct.response:", response)
		return response.data
	},

	deleteProduct: async id => {
		console.log("api.deleteProduct")
		const response = await axiosInstance.delete(`/products/${id}`)
		console.log("api.deleteProduct.response:", response)
		return response.data
	},

	fetchOrders: async() => {
		console.log("api.fetchOrders")
		const response = await axiosInstance.get('/orders')
		console.log("api.fetchOrders.response:", response)
		return response.data
	},

	fetchOrderById: async id => {
		console.log(`api.fetchOrderById ${id}`)
		const response = await axiosInstance.get(`/orders/${id}`)
		console.log("api.fetchOrderById.response:", response)
		return response.data
	},

	createOrder: async order => {
		console.log("api.createOrder", order)
		const response = await axiosInstance.post('/orders', order)
		console.log("api.createOrder.response:", response)
		return response.data
	},

	updateOrder: async order => {
		console.log("api.updateOrder", order)
		const response = await axiosInstance.put(`/orders`, order)
		console.log("api.updateOrder.response:", response)
		return response.data
	},

	deleteOrder: async id => {
		console.log("api.deleteOrder")
		const response = await axiosInstance.delete(`/orders/${id}`)
		console.log("api.deleteOrder.response:", response)
		return response.data
	}

}

export default api
