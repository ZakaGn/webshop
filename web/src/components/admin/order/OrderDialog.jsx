import React, {useState, useEffect} from 'react'
import {OrderService} from 'services/OrderService'
import {toast} from 'react-toastify'
import './OrderDialog.css'

const OrderDialog = ({isOpen, onClose, order}) => {
	const [userId, setUserId] = useState('')
	const [orderDetails, setOrderDetails] = useState([])

	useEffect(() => {
		if(order){
			setUserId(order.userId || '')
			setOrderDetails(order.orderDetails || [])
		}else{
			resetForm()
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
		setOrderDetails([...orderDetails, {productId: '', quantity: '', price: ''}])
	}

	const removeDetail = (index) => {
		setOrderDetails(orderDetails.filter((_, detailIndex) => index !== detailIndex))
	}

	const handleSave = async() => {
		const orderData = {
			userId: parseInt(userId, 10),
			orderDetails: orderDetails.map(detail => ({
				productId: parseInt(detail.productId, 10),
				quantity: parseInt(detail.quantity, 10),
				price: parseFloat(detail.price),
			})),
		}

		try{
			if(order?.id){
				await OrderService.updateOrder(order.id, orderData)
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

				<div className="order-details-section">
					<h3>Order Details</h3>
					<div className="order-details-table">
						<div className="order-details-header">
							<span>Product ID</span>
							<span>Quantity</span>
							<span>Price</span>
							<span>Action</span>
						</div>
						{orderDetails.map((detail, index) => (
							<div key={index} className="order-detail-row">
								<input
									type="number"
									value={detail.productId}
									onChange={(e) =>
										handleDetailChange(index, 'productId', e.target.value)}
								/>
								<input
									type="number"
									value={detail.quantity}
									onChange={(e) =>
										handleDetailChange(index, 'quantity', e.target.value)}
								/>
								<input
									type="number"
									step="0.01"
									value={detail.price}
									onChange={(e) =>
										handleDetailChange(index, 'price', e.target.value)}
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
