import api from "utils/api";

export const productService = {
	fetchProducts: async () => api.fetchProducts(),
	addProduct: async (product) => api.addProduct(product),
	updateProduct: async (product) => api.updateProduct(product),
	deleteProduct: async (id) => api.deleteProduct(id)
}
