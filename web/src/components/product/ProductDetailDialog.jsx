import React, {useState} from 'react'
import './ProductDetailDialog.css'
import CartService from "../../services/CartService";
import {toast} from "react-toastify";
import Cart from "../../model/Cart";

const ProductDetailDialog = ({cart, product, onClose, setCart}) => {
	const [quantity, setQuantity] = useState(1)

	if(!product) return null

	const addToCart = () => {
		let newCartItem = {
			cartId: cart.id,
			productId: product.id,
			quantity: quantity
		}
		CartService.addToCart(newCartItem).then(
			data => {
				const newCart = new Cart(data)
				setCart(newCart)
				onClose()
			}
		).catch(error => {toast.error(error.response?.data?.message || 'Failed to add to cart')})
	}

	return (
		<div className="product-detail-overlay">
			<div className="product-detail-dialog">
				<button className="close-btn" onClick={onClose}>X</button>
				<img src={product.image} alt={product.name} className="product-image"/>
				<h3>{product.name}</h3>
				<p>{product.description}</p>
				<p className="product-price">${product.price}</p>
				<div className="quantity-selector">
					<label>Quantity:</label>
					<input
						type="number"
						value={quantity}
						onChange={(e) => setQuantity(e.target.value)}
						min="1"
					/>
				</div>
				<button className="add-to-cart-btn" onClick={addToCart}>Add to Cart</button>
			</div>
		</div>
	)
}

export default ProductDetailDialog
