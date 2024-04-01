import api from "utils/api";

const OrderService = {
	getAllOrders: async () => api.getAllOrders(),
	getUserOrders: async () => api.getUserOrders(),
	getOrderById: async (id) => api.getOrderById(id),
	createOrder: async (order) => api.createOrder(order),
	updateOrder: async (order) => api.updateOrder(order),
	deleteOrder: async (id) => api.deleteOrder(id),
	submitOrder: async (cart) => api.submitOrder(cart)
}

export default OrderService
