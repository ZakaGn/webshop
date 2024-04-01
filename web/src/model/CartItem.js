class CartItem{
	constructor(data){
		this.id = data.id
		this.cartId = data.cartId
		this.product = data.product
		this.quantity = data.quantity
	}

	get total(){
		if(!this.product) return 0
		return this.product.price*this.quantity
	}
}

export default CartItem
