import api from "utils/api";

export const OrderService = {
	fetchOrders: async () => api.fetchOrders(),
	fetchOrderById: async (orderId) => api.fetchOrderById(orderId),
	createOrder: async (orderDTO) => api.createOrder(orderDTO),
	updateOrder: async (orderId, orderDTO) => api.updateOrder(orderId, orderDTO),
	deleteOrder: async (orderId) => api.deleteOrder(orderId)
}
