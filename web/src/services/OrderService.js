import api from "utils/api";

const OrderService = {
	getAllOrders: async () => api.getAllOrders(),
	getUserOrders: async () => api.getUserOrders(),
	getOrderById: async (id) => api.getOrderById(id),
	createOrder: async (order) => api.createOrder(order),
	updateOrder: async (order) => api.updateOrder(order),
	deleteOrder: async (id) => api.deleteOrder(id),
	submitOrder: async (order) => api.submitOrder(order)
}

export default OrderService
