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
		<div id="admin-orders">
			<h1>Admin Orders</h1>
			<button onClick={handleAddNewOrder}>Add New Order</button>
			<input
				type="text"
				placeholder="Search orders..."
				value={searchQuery}
				onChange={handleSearchChange}
			/>
			<div>
				{filteredOrders.length > 0 ? (
					filteredOrders.map((order, index) => (
						<div key={index} onClick={() => handleEditOrder(order)}>
							Order ID: {order.id}, Status: {order.status}
						</div>
					))
				) : (
					<p>No orders found.</p>
				)}
			</div>
			<OrderDialog
				isOpen={isDialogOpen}
				onClose={handleCloseDialog}
				order={selectedOrder}
				onSave={fetchOrders}
			/>
		</div>
	)
}

export default AdminOrders
