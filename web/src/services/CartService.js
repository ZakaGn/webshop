import api from 'utils/api'

const CartService = {
	getCart: async() => api.getCart(),
	getCartByUserId: async(userId) => api.getCartByUserId(userId),
	addToCart: async(cartItem) => api.addToCart(cartItem),
	removeFromCart: async(id) => api.removeFromCart(id),
	clearCart: async() => api.clearCart()
}

export default CartService
