import './CartComponent.css'
import React from 'react'

const CartComponent = ({onClose, isOpen, cartItems, onSubmitOrder}) => {

	const totalPrice = cartItems.reduce((total, item) => total + item.price*item.quantity, 0)

	return (
		<div className={`cart-overlay ${isOpen ? 'show' : ''}`} onClick={onClose}>
			<div className="cart-dialog" onClick={(e) => e.stopPropagation()}>
				<h2>Your Cart</h2>
				<ul>
					{cartItems.length > 0 ? (
						cartItems.map((item, index) => (
							<li key={index}>
								<span>{item.name}</span> - <span>{item.quantity} x ${item.price}</span>
							</li>
						))
					) : (
						<p>Your cart is empty.</p>
					)}
				</ul>
				{cartItems.length > 0 && (
					<>
						<div>Total: ${totalPrice.toFixed(2)}</div>
						<button onClick={onSubmitOrder}>Submit Order</button>
					</>
				)}
				<button onClick={onClose}>Close Cart</button>
			</div>
		</div>
	)
}

export default CartComponent
