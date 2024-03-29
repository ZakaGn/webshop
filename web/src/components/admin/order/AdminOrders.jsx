import './AdminOrders.css'
import React, {useEffect, useState} from 'react'
import {OrderService} from 'services/OrderService'
import {toast} from 'react-toastify'
import OrderDialog from './OrderDialog'

const AdminOrders = () => {
	const [orders, setOrders] = useState([])
	const [searchQuery, setSearchQuery] = useState('')
	const [isDialogOpen, setIsDialogOpen] = useState(false)
	const [selectedOrder, setSelectedOrder] = useState(null)
	let isMounted = true

	useEffect(() => {
		if(!isMounted) return
		fetchOrders()
		return () => {
			isMounted = false
		}
	}, [])

	const fetchOrders = () => {
		OrderService.fetchOrders().then(
			data => setOrders(data)
		).catch(
			error => toast.error(error.response?.data?.message || 'Failed to fetch orders')
		)
	}

	const handleAddNewOrder = () => {
		setSelectedOrder(null)
		setIsDialogOpen(true)
	}

	const handleEditOrder = (order) => {
		setSelectedOrder(order)
		setIsDialogOpen(true)
	}

	const handleSearchChange = (event) => {
		setSearchQuery(event.target.value.toLowerCase())
	}

	const filteredOrders = orders.filter(order =>
		order.id.toString().includes(searchQuery) || order.status.toLowerCase().includes(searchQuery)
	)

	const handleCloseDialog = () => {
		setIsDialogOpen(false)
		fetchOrders()
	}

	return (
		<div id="admin-orders" className="admin-orders-container">
			<h1>Admin Orders</h1>
			<div className="admin-orders-header">
				<button onClick={handleAddNewOrder} className="add-order-button">Add New Order</button>
				<input
					type="text"
					placeholder="Search orders..."
					value={searchQuery}
					onChange={handleSearchChange}
					className="order-search-input"
				/>
			</div>
			<div className="orders-list">
				{filteredOrders.length > 0 ? (
					filteredOrders.map((order, index) => (
						<div key={index} className="order-item" onClick={() => handleEditOrder(order)}>
							<span className="order-id">Order ID: {order.id}</span>
							<span className="order-status">Status: {order.status}</span>
						</div>
					))
				) : (
					<p className="no-orders-message">No orders found.</p>
				)}
			</div>
			<OrderDialog
				isOpen={isDialogOpen}
				onClose={handleCloseDialog}
				order={selectedOrder}
			/>
		</div>

	)
}

export default AdminOrders
