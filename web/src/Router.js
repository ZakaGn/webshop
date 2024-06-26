import React from 'react'
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from './components/Home'
import Login from './components/user/Login'
import Register from './components/user/Register'
import Dashboard from './components/user/Dashboard'
import ProfileEdit from 'components/user/ProfileEdit'
import Navbar from "./components/layout/Navbar"
import AdminCategories from "components/admin/category/AdminCategories"
import AdminProducts from "components/admin/product/AdminProducts"
import AdminOrders from "components/admin/order/AdminOrders"

const AppRouter = ({user, cart, orders, getUser, setUser, setCart}) => {
	return (
		<Router>
			<Navbar user={user} cart={cart} setUser={setUser} setCart={setCart}/>
			<Routes>
				<Route path="/" element={<Home cart={cart} setCart={setCart}/>}/>
				<Route path="/login" element={<Login getUser={getUser}/>}/>
				<Route path="/register" element={<Register/>}/>
				<Route path="/dashboard" element={<Dashboard user={user}/>}/>
				<Route path="/edit-profile" element={<ProfileEdit user={user} setUser={setUser}/>} />
				<Route path="/admin/categories" element={<AdminCategories />} />
				<Route path="/admin/products" element={<AdminProducts />} />
				<Route path="/admin/orders" element={<AdminOrders />} />
			</Routes>
		</Router>
	)
}

export default AppRouter
