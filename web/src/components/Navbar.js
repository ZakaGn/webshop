import 'components/Navbar.css'
import React from 'react'
import {Link, useNavigate} from 'react-router-dom'
import auth from "utils/auth"
import {userService} from "services/UserService";


const Navbar = () => {
	const navigate = useNavigate()
	const isAuthenticated = auth.isAuthenticated()
	const isEmployer = isAuthenticated && auth.getRole() === 'ROLE_EMPLOYER'

	const handleLogout = () => {
		userService.logout()
		navigate('/login', {replace: true})
	}

	return (
		<nav>
			<ul>
				<li><Link to="/">Home</Link></li>
				{isAuthenticated && (
					<>
						<li><Link to="/dashboard">Dashboard</Link></li>
						{isEmployer && (
							<>
								<li><Link to="/admin/categories">Manage Categories</Link></li>
							</>
						)}
						<li>
							<button onClick={handleLogout}>Logout</button>
						</li>
					</>
				)}
				{!isAuthenticated && (
					<>
						<li><Link to="/login">Login</Link></li>
						<li><Link to="/register">Register</Link></li>
					</>
				)}
			</ul>
		</nav>
	)
}

export default Navbar
