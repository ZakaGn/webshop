import './CartComponent.css'
import React from 'react'
import OrderService from "../../services/OrderService";
import {toast} from "react-toastify";
import Cart from "../../model/Cart";

const CartComponent = ({onClose, isOpen, cart, setCart}) => {
	const totalPrice = cart.cartItems.reduce((total, item) => total + item.productPrice*item.quantity, 0)

	const handleSubmitOrder = () => {
		OrderService.submitOrder(cart).then(data => {
			toast.success('Order submitted successfully')
			const newCart = new Cart(data)
			setCart(newCart)
			onClose()
		}).catch(error => {
			toast.error(error.response?.data?.message || 'Failed to submit order');
		})
	}

	return (
		<div className={`cart-overlay ${isOpen ? 'show' : ''}`} onClick={onClose}>
			<div className="cart-dialog" onClick={(e) => e.stopPropagation()}>
				<h2 className="cart-title">Your Cart</h2>
				<ul className="cart-list">
					{cart.cartItems.length > 0 ? (
						cart.cartItems.map((item, index) => (
							<li className="cart-item" key={index}>
								<span>{item.productName}</span> <span>{item.quantity} x ${item.productPrice}</span>
							</li>
						))
					) : (
						<p>Your cart is empty.</p>
					)}
				</ul>
				{cart.cartItems.length > 0 && (
					<>
						<div className="cart-total">Total: ${totalPrice.toFixed(2)}</div>
						<button className="cart-submit" onClick={handleSubmitOrder}>Submit Order</button>
					</>
				)}
				<button className="cart-close" onClick={onClose}>Close Cart</button>
			</div>
		</div>
	)
}

export default CartComponent
