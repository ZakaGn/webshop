import api from "utils/api";

const ProductService = {
	fetchProducts: async () => api.fetchProducts(),
	addProduct: async (product) => api.addProduct(product),
	updateProduct: async (product) => api.updateProduct(product),
	deleteProduct: async (id) => api.deleteProduct(id),
	searchProductByName: async (name) => api.searchProductByName(name)
}

export default ProductService
