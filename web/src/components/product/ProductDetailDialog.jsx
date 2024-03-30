import React, {useState} from 'react'
import './ProductDetailDialog.css'

const ProductDetailDialog = ({product, onClose, onAddToCart}) => {
	const [quantity, setQuantity] = useState(1)

	if(!product) return null

	const handleAddToCart = () => {
		onAddToCart(product, quantity)
		onClose()
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
				<button className="add-to-cart-btn" onClick={handleAddToCart}>Add to Cart</button>
			</div>
		</div>
	)
}

export default ProductDetailDialog
