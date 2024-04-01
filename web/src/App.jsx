import React, {useEffect, useState} from 'react'
import 'App.css'
import {toast, ToastContainer} from "react-toastify"
import AppRouter from 'Router'
import User from "model/User"
import Cart from "model/Cart"
import Order from "model/Order"
import UserService from "services/UserService"
import CartService from "services/CartService"
import OrderService from "services/OrderService"

function App(){
	const [user, setUser] = useState(new User(null))
	const [cart, setCart] = useState(new Cart(null))
	const [orders, setOrders] = useState([])
	let isMounted = true

	useEffect(() => {
		if(!isMounted) return
		fetchData().then(() => {console.log('App.js: fetched data')})
		return () => {isMounted = false}
	}, [])

	useEffect(() => {
		console.log('App._____________useEffect: user:', user)
	}, [user])

	useEffect(() => {
		console.log('App._____________useEffect: cart:', cart)
	}, [cart])

	useEffect(() => {
		console.log('App._____________useEffect: orders:', orders)
	}, [orders])

	const fetchData = async() => {
		await getUser()
		await getCart()
		await getUserOrders()
	}

	const getUser = async() => {
		console.log('App.js: getUser() started')
		if(!UserService.isAuthenticated()) return
		console.log('App.js: getUser()')
		try{
			const userData = await UserService.getUser()
			console.log('App.js: getUser() userData:', userData)
			setUser(new User(userData))
		}catch(error){
			toast.error(error.data?.message || 'Failed to fetch user')
			UserService.logout()
		}
	}

	const getCart = async() => {
		if(!UserService.isAuthenticated()) return
		try{
			const cartData = await CartService.getCart()
			setCart(new Cart(cartData))
		}catch(error){
			toast.error(error.response?.data?.message || 'Failed to fetch cart')
		}
	}

	const getUserOrders = async() => {
		if(!UserService.isAuthenticated()) return
		try{
			const orderData = OrderService.getUserOrders()
			setOrders(orderData.map(order => new Order(order)))
		}catch(error){
			toast.error(error.response?.data?.message || 'Failed to fetch orders')
		}
	}

	return (
		<div className="App">
			<AppRouter user={user} cart={cart} orders={orders} getUser={getUser} setUser={setUser} setCart={setCart}/>
			<ToastContainer
				position="top-center"
				autoClose={5000}
				hideProgressBar={false}
				newestOnTop={false}
				closeOnClick
				rtl={false}
				pauseOnFocusLoss
				pauseOnHover
				theme="colored"
			/>
		</div>
	)

}

export default App
