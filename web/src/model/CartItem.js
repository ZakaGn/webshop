class CartItem{
	constructor(data){
		this.id = data.id
		this.cartId = data.cartId
		this.productId = data.productId
		this.productName = data.productName
		this.productPrice = data.productPrice
		this.quantity = data.quantity
	}

	get total(){
		return this.productPrice*this.quantity
	}
}

export default CartItem
