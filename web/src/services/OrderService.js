import api from "utils/api";

export const OrderService = {
	fetchOrders: async () => api.fetchOrders(),
	fetchOrderById: async (id) => api.fetchOrderById(id),
	createOrder: async (order) => api.createOrder(order),
	updateOrder: async (order) => api.updateOrder(order),
	deleteOrder: async (id) => api.deleteOrder(id)
}
