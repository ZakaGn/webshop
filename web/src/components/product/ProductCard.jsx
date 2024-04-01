import React from 'react'
import './ProductCard.css'

const ProductCard = ({product, onClick}) => {
	return (
		<div className="product-card" onClick={() => onClick(product)}>
			<div className="product-info">
				{/*<img src={product.image} alt={product.name} className="product-image"/>*/}
				<h5 className="product-name">{product.name}</h5>
				<p className="product-price">${product.price}</p>
				<button className="view-product-btn">View</button>
			</div>
		</div>
	)
}

export default ProductCard
