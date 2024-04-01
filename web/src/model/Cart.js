class Cart{
	constructor(data){
		if(!data) return
		this.id = data.id
		this.userId = data.userId
		this.createdAt = data.createdAt
		this.cartItems = data.cartItems || []
	}

	addItem(product, quantity){
		const item = this.cartItems.find(item => item.product.id === product.id)
		if(item){
			item.quantity += quantity
		}else{
			this.cartItems.push({product, quantity})
		}
	}

	removeItem(product){
		this.cartItems = this.cartItems.filter(item => item.product.id !== product.id)
	}

	getTotalPrice(){
		return this.cartItems.reduce((total, item) => total + item.product.price*item.quantity, 0)
	}
}

export default Cart
