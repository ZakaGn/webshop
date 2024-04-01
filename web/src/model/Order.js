import {OrderStatus} from "./OrderStatus";

class Order {
	constructor(data){
		if(!data) return
		this.id = data.id
		this.userId = data.userId
		this.orderDate = data.orderDate
		this.status = data.status || OrderStatus.PENDING
		this.orderDetails = data.orderDetails || []
	}

	addItem(product, quantity){
		const item = this.orderDetails.find(item => item.productId === product.id)
		if(item){
			item.quantity += quantity
		}else{
			this.orderDetails.push({productId: product.id, quantity})
		}
	}

	removeItem(product){
		this.orderDetails = this.orderDetails.filter(item => item.productId !== product.id)
	}

	updateItem(product, quantity){
		const item = this.orderDetails.find(item => item.productId === product.id)
		if(item){
			item.quantity = quantity
		}
	}

	getTotalPrice(){
		return this.orderDetails.reduce((total, item) => total + item.price*item.quantity, 0)
	}

	getTotalQuantity(){
		return this.orderDetails.reduce((total, item) => total + item.quantity, 0)
	}

	getOrderDetail(productId){
		return this.orderDetails.find(item => item.productId === productId)
	}

}

export default Order
