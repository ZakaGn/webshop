import './Navbar.css'
import React, {useState} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import {FaShoppingCart} from 'react-icons/fa'
import {toast} from "react-toastify";
import CartComponent from "../CartComponent"
import userService from "services/UserService"
import OrderService from "services/OrderService"
import CartService from "services/CartService";
import User from "../../model/User";

const Navbar = ({user, cart, setUser}) => {
	const navigate = useNavigate()
	const [cartCount, setCartCount] = useState(0)
	const [isCartOpen, setIsCartOpen] = useState(false)

	const toggleCart = () => setIsCartOpen(!isCartOpen)

	const handleLogout = () => {
		userService.logout()
		navigate('/login', {replace: true})
		setUser(new User(null))
	}

	const handleSubmitOrder = () => {
		OrderService.submitOrder(cart).then(response => {
			console.log('Order submitted: ', response)
			setIsCartOpen(false)
			setCartCount(0)
		}).catch(error => {
			console.error('Order submission failed: ', error);
		});
	}

	return (
		<nav id={"navbar"}>
			<ul>
				<li><Link to="/">Home</Link></li>
				{user?.email && (
					<>
						<li><Link to="/dashboard">Dashboard</Link></li>
						{user?.role === "ROLE_EMPLOYER" &&
							<>
								<li><Link to="/admin/categories">Manage Categories</Link></li>
								<li><Link to="/admin/products">Manage Products</Link></li>
								<li><Link to="/admin/orders">Manage Orders</Link></li>
							</>
						}
					</>
				)}
				<li>
					{user?.email &&
						<button onClick={toggleCart}>
							<FaShoppingCart/>
							{cartCount > 0 && <span className="cart-count">{cartCount}</span>}
						</button>
					}
				</li>
				<li style={{marginLeft: 'auto'}}>
					{user?.email ? (
						<>
							<span>{user.email || 'Guest'}</span>
							<button onClick={handleLogout}>Logout</button>
						</>
					) : (
						<>
							<Link to="/login">Login</Link>
							<Link to="/register">Register</Link>
						</>
					)}
				</li>
			</ul>
			{isCartOpen &&
				<CartComponent
					onClose={toggleCart}
					isOpen={isCartOpen}
					cartItems={cart.cartItems}
					onSubmitOrder={handleSubmitOrder}
				/>
			}
		</nav>
	)
}

export default Navbar
