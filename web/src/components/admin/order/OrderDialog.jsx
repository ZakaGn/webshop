import React, {useState, useEffect} from 'react'
import OrderService from 'services/OrderService'
import ProductService from 'services/ProductService'
import {toast} from 'react-toastify'
import './OrderDialog.css'
import {debounce} from 'lodash'

const OrderDialog = ({isOpen, onClose, order}) => {
	const [userId, setUserId] = useState('')
	const [status, setStatus] = useState('PENDING')
	const [orderDetails, setOrderDetails] = useState([])
	const [searchTerm, setSearchTerm] = useState('')
	const [searchResults, setSearchResults] = useState([])
	const [isLoading, setIsLoading] = useState(false)

	useEffect(() => {
		if(order){
			setUserId(order.userId || '')
			setOrderDetails(order.orderDetails || [])
		}else{
			resetForm()
		}
		return () => {
			setSearchResults([])
			setIsLoading(false)
		}
	}, [order])

	const resetForm = () => {
		setUserId('')
		setOrderDetails([])
	}

	const handleDetailChange = (index, field, value) => {
		const updatedDetails = orderDetails.map((detail, detailIndex) => {
			if(index === detailIndex){
				return {...detail, [field]: value}
			}
			return detail
		})
		setOrderDetails(updatedDetails)
	}

	const addDetail = () => {
		const newDetail = {
			...(order?.id && {orderId: order.id}),
			productId: '',
			quantity: '',
			price: ''
		}
		setOrderDetails([...orderDetails, newDetail])
	}

	const removeDetail = (index) => {
		setOrderDetails(orderDetails.filter((_, detailIndex) => index !== detailIndex))
	}

	const handleSearchChange = (e) => {
		setSearchTerm(e.target.value)
		if(e.target.value.trim() === ''){
			setSearchResults([])
			setIsLoading(false)
		}else{
			setIsLoading(true)
			debouncedSearch(e.target.value)
		}
	}

	const selectProduct = (product) => {
		const newDetail = {
			productId: product.id,
			quantity: 1,
			price: product.price
		}
		setOrderDetails([...orderDetails, newDetail])
		setSearchTerm('')
		setSearchResults([])
	}

	const debouncedSearch = debounce(async(searchTerm) => {
		if(!searchTerm.trim()){
			setSearchResults([])
			return
		}
		try{
			const results = await ProductService.searchProductByName(searchTerm)
			setSearchResults(results)
		}catch(error){
			toast.error(error.response?.data?.message || 'An error occurred during the search')
		}finally{
			setIsLoading(false)
		}
	}, 300)

	const validateOrderDetails = () => {
		if(!userId || userId <= 0){
			toast.error('Please enter a valid User ID')
			return false
		}
		for(const detail of orderDetails){
			if(!detail.productId || detail.productId <= 0){
				toast.error('Please enter a valid Product ID for all items.')
				return false
			}
			if(!detail.price || detail.price <= 0){
				toast.error('Price must be greater than zero for all items.')
				return false
			}
			if(!detail.quantity || detail.quantity <= 0){
				toast.error('Quantity must be greater than zero for all items.')
				return false
			}
		}
		return true
	}

	const handleSave = async() => {
		if(!validateOrderDetails()) return
		const orderData = {
			...(order?.id && {id: order.id, status}),
			userId: parseInt(userId, 10),
			orderDetails: orderDetails.map(detail => ({
				...(detail.id && {id: detail.id}),
				productId: parseInt(detail.productId, 10),
				quantity: parseInt(detail.quantity, 10),
				price: parseFloat(detail.price)
			}))
		}
		try{
			if(order?.id){
				await OrderService.updateOrder(orderData)
				toast.success('Order updated successfully')
			}else{
				await OrderService.createOrder(orderData)
				toast.success('Order created successfully')
			}
		}catch(error){
			toast.error(error.response?.data?.message || 'An error occurred')
		}finally{
			resetForm()
			onClose()
		}
	}

	if(!isOpen) return null

	return (
		<div id="order-dialog" className="order-dialog-overlay">
			<div className="order-dialog-content">
				<h2>{order?.id ? 'Edit Order' : 'Add New Order'}</h2>
				<div className="form-field">
					<label htmlFor="userId">User ID</label>
					<input
						id="userId"
						type="number"
						value={userId}
						onChange={(e) => setUserId(e.target.value)}
					/>
				</div>
				{order?.id && (
					<div className="form-field">
						<label htmlFor="status">Status</label>
						<select id="status" value={status} onChange={e => setStatus(e.target.value)}>
							<option value="PENDING">Pending</option>
							<option value="PROCESSING">Processing</option>
							<option value="SHIPPED">Shipped</option>
							<option value="DELIVERED">Delivered</option>
							<option value="CANCELLED">Cancelled</option>
						</select>
					</div>
				)}
				<div className="search-product">
					<input
						type="text"
						value={searchTerm}
						onChange={handleSearchChange}
						placeholder="Search product by name"
					/>
				</div>
				{isLoading && <div className="loading-icon">Searching products...</div> ||
					searchResults.length > 0 && (
						<div className="search-results">
							{searchResults.map((product) => (
								<div key={product.id} onClick={() => selectProduct(product)}>
									{product.name} - ${product.price}
								</div>
							))}
						</div>
					) || "type something to search"}
				<div className="order-details-section">
					<h3>Order Details</h3>
					<div className="order-details-table">
						<div className="order-details-header">
							<span>Product ID</span>
							<span>Price</span>
							<span>Quantity</span>
							<span>Action</span>
						</div>
						{orderDetails.map((detail, index) => (
							<div key={index} className="order-detail-row">
								<input
									type="number"
									value={detail.productId || ''}
									onChange={(e) =>
										handleDetailChange(index, 'productId', e.target.value)}
								/>
								<input
									type="number"
									step="0.01"
									value={detail.price || ''}
									onChange={(e) =>
										handleDetailChange(index, 'price', e.target.value)}
								/>
								<input
									type="number"
									value={detail.quantity || ''}
									onChange={(e) => handleDetailChange(index, 'quantity', e.target.value)}
								/>
								<button onClick={() => removeDetail(index)}>Remove</button>
							</div>
						))}
						<button className="add-detail-button" onClick={addDetail}>
							Add Detail
						</button>
					</div>
				</div>

				<div className="dialog-actions">
					<button onClick={handleSave}>Save</button>
					<button onClick={onClose}>Cancel</button>
				</div>
			</div>
		</div>

	)
}

export default OrderDialog
