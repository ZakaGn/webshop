import './Navbar.css'
import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import auth from "../utils/auth";

const Navbar = () => {
	const navigate = useNavigate()
	const isAuthenticated = localStorage.getItem('token')

	const handleLogout = () => {
		auth.logout()
		navigate('/login', {replace: true})
	}

	return (
		<nav>
			<ul>
				<li>
					<Link to="/">Home</Link>
				</li>
				{auth.isAuthenticated() ? (
					<>
						<li>
							<Link to="/dashboard">Dashboard</Link>
						</li>
						<li>
							<button onClick={handleLogout}>Logout</button>
						</li>
					</>
				) : (
					<>
						<li>
							<Link to="/login">Login</Link>
						</li>
						<li>
							<Link to="/register">Register</Link>
						</li>
					</>
				)}
			</ul>
		</nav>
	)
}

export default Navbar
