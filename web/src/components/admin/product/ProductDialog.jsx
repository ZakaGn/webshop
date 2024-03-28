import React, {useState, useEffect} from 'react'
import './ProductDialog.css'

const ProductDialog = ({isOpen, onClose, product, categories, onSave, onDelete}) => {
	const [name, setName] = useState('')
	const [description, setDescription] = useState('')
	const [price, setPrice] = useState('')
	const [categoryId, setCategoryId] = useState('')
	const [quantity, setQuantity] = useState('')

	useEffect(() => {
		if(product){
			setName(product.name || '')
			setDescription(product.description || '')
			setPrice(product.price || '')
			setCategoryId(product.categoryId || '')
			setQuantity(product.quantity || '')
		}else{
			resetForm()
		}
	}, [product])

	const resetForm = () => {
		setName('')
		setDescription('')
		setPrice('')
		setCategoryId('')
		setQuantity('')
	}

	const handleSave = () => {
		onSave({
			...product,
			name,
			description,
			price: parseFloat(price),
			categoryId: parseInt(categoryId, 10),
			quantity: parseInt(quantity, 10),
		})
		resetForm()
		onClose()
	}

	const handleDelete = async(id) => {
		if(product?.id){
			await onDelete(id)
			onClose()
		}
	}

	if(!isOpen) return null

	return (
		<div id="product-dialog">
			<div className="dialog-content">
				<h2>{product?.id ? 'Edit Product' : 'Add New Product'}</h2>
				<label htmlFor="productName">Name</label>
				<input id="productName" type="text" value={name} onChange={e => setName(e.target.value)}/>

				<label htmlFor="productDescription">Description</label>
				<input id="productDescription" type="text" value={description} onChange={e => setDescription(e.target.value)}/>

				<label htmlFor="productPrice">Price</label>
				<input id="productPrice" type="number" value={price} onChange={e => setPrice(e.target.value)}/>

				<label htmlFor="productQuantity">Quantity</label>
				<input id="productQuantity" type="number" value={quantity} onChange={e => setQuantity(e.target.value)}/>

				<label htmlFor="productCategory">Category</label>
				<select id="productCategory" value={categoryId} onChange={e => setCategoryId(e.target.value)}>
					<option value="">Select a category</option>
					{categories.map(category => (
						<option key={category.id} value={category.id}>
							{category.name}
						</option>
					))}
				</select>

				<div className="dialog-actions">
					<button onClick={handleSave}>Save</button>
					{product && product.id && (
						<button onClick={() => handleDelete(product.id)}>Delete</button>
					)}
					<button onClick={onClose}>Cancel</button>
				</div>
			</div>
		</div>
	)
}

export default ProductDialog
