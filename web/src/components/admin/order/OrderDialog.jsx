import React, {useState, useEffect} from 'react'
import {OrderService} from 'services/OrderService'
import {toast} from 'react-toastify'
import './OrderDialog.css'

const OrderDialog = ({isOpen, onClose, order}) => {
	const [userId, setUserId] = useState(order?.userId || '')
	const [orderDetails, setOrderDetails] = useState(order?.orderDetails || [])

	useEffect(() => {
		if(order){
			setUserId(order.userId)
			setOrderDetails(order.orderDetails)
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
		setOrderDetails([...orderDetails, {productId: '', quantity: 1, price: ''}])
	}

	const removeDetail = (index) => {
		setOrderDetails(orderDetails.filter((_, detailIndex) => index !== detailIndex))
	}

	const handleSave = async() => {
		const orderData = {
			userId: parseInt(userId, 10),
			orderDetails: orderDetails.map(detail => ({
				id: detail.id,
				orderId: detail.orderId,
				productId: parseInt(detail.productId, 10),
				quantity: parseInt(detail.quantity, 10),
				price: parseFloat(detail.price)
			}))
		}

		try{
			if(order?.id){
				await OrderService.updateOrder(order.id, orderData)
				toast.success('Order updated successfully')
			}else{
				await OrderService.createOrder(orderData)
				toast.success('Order created successfully')
			}
			resetForm()
			onClose()
		}catch(error){
			toast.error(error.response?.data?.message || 'An error occurred')
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
					{orderDetails.map((detail, index) => (
						<div key={index} className="order-detail">
							<div className="form-field">
								<label htmlFor={`product-${index}`}>Product ID</label>
								<input
									id={`product-${index}`}
									type="number"
									value={detail.productId}
									onChange={(e) => handleDetailChange(index, 'productId', e.target.value)}
								/>
							</div>
							<div className="form-field">
								<label htmlFor={`quantity-${index}`}>Quantity</label>
								<input
									id={`quantity-${index}`}
									type="number"
									value={detail.quantity}
									onChange={(e) => handleDetailChange(index, 'quantity', e.target.value)}
								/>
							</div>
							<div className="form-field">
								<label htmlFor={`price-${index}`}>Price</label>
								<input
									id={`price-${index}`}
									type="number"
									step="0.01"
									value={detail.price}
									onChange={(e) => handleDetailChange(index, 'price', e.target.value)}
								/>
							</div>
							<button className="remove-detail" onClick={() => removeDetail(index)}>Remove Detail</button>
						</div>
					))}
					<button className="add-detail" onClick={addDetail}>Add Detail</button>
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
